package com.github.grooviter.stateit

import com.github.grooviter.stateit.core.Error
import com.github.grooviter.stateit.core.Plan
import com.github.grooviter.stateit.core.PlanExecutor
import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.core.Result
import com.github.grooviter.stateit.core.StateProps
import com.github.grooviter.stateit.core.StateProvider
import com.github.grooviter.stateit.core.variables.FileResolver
import com.github.grooviter.stateit.core.variables.Variables
import com.github.grooviter.stateit.core.variables.VariablesLoader
import com.github.grooviter.stateit.core.variables.VariablesLoaderResolver

import static com.github.grooviter.stateit.core.ClosureUtils.applyPropsToClosure

class DSL {
    private List<Resource> declaredResources = []
    private StateProvider stateProvider
    private Variables variables

    private DSL(Variables variables) {
        this.variables = variables
    }

    static Result<Plan> validate(Plan plan) {
        return new PlanExecutor(plan).validate()
    }

    static Result<Plan> execute(Plan plan) {
        return new PlanExecutor(plan).execute()
    }

    static Result<Plan> destroy(Plan plan){
        return new PlanExecutor(plan).destroy()
    }

    static Result<Plan> catalog(File catalogDir, File variablesFile) {
        Result<DSL> dslResult = catalogDir
            .listFiles()
            .collect { dsl(it, variablesFile) }
            .inject { DSL agg, DSL next -> agg.merge(next) } as Result<DSL>

        return dslResult
            .mapTry((DSL dsl) -> dsl.stateProvider.load()
            .flatMap(plan -> Plan.builder()
                .stateProvider(dsl.stateProvider)
                .resourcesDeclared(dsl.declaredResources)
                .resourcesInState(plan.resourcesInState)
                .build())
        , null) as Result<Plan>
    }

    static DSL dsl(File planFile, File variableFile) {
        List<VariablesLoaderResolver> resolvers = (variableFile ? [new FileResolver(variableFile)] : []) as List<VariablesLoaderResolver>
        VariablesLoader loader = new VariablesLoader(resolvers)
        GroovyShell shell = new GroovyShell(new Binding([DSL: DSL, loader: loader]))
        String scriptText = "DSL.dsl(loader) { ${planFile.text } }"
        return shell.evaluate(scriptText) as DSL
    }

    static DSL dsl(VariablesLoader variablesLoader, @DelegatesTo(DSL) Closure closure) {
        return applyPropsToClosure(new DSL(variablesLoader.load()), closure)
    }

    static Plan stateit(File planFile, File variableFile) {
        DSL dsl = dsl(planFile, variableFile)
        Result<Plan> statePlan = dsl.stateProvider?.load() ?: Result.of(Plan.empty())

        if (statePlan.failure) {
            return statePlan.context
        }

        return Plan.builder()
                .stateProvider(dsl.stateProvider)
                .resourcesDeclared(dsl.declaredResources)
                .resourcesInState(statePlan.context.resourcesInState)
                .build()
    }

    static Plan stateit(VariablesLoader variablesLoader, @DelegatesTo(DSL) Closure closure) {
        DSL dsl = applyPropsToClosure(new DSL(variablesLoader.load()), closure)
        Result<Plan> statePlan = dsl.stateProvider?.load() ?: Result.of(Plan.empty())

        if (statePlan.failure) {
            return statePlan.context
        }

        return Plan.builder()
                .stateProvider(dsl.stateProvider)
                .resourcesDeclared(dsl.declaredResources)
                .resourcesInState(statePlan.context.resourcesInState)
                .build()
    }

    static Plan stateit(@DelegatesTo(DSL) Closure closure) {
        return stateit(new VariablesLoader(), closure)
    }

    Result<DSL> merge(DSL another) {
        return Result
            .of(new DSL(this.variables))
            .filter(newDSL -> false, new Error("", "", ""))
            .flatMap(newDSL -> newDSL.addStateProvider(another.stateProvider))
            .sideEffect(newDSL -> newDSL.declaredResources = another.declaredResources + this.declaredResources)
    }

    DSL state(@DelegatesTo(StateProps) Closure closure) {
        StateProps stateProps = applyPropsToClosure(new StateProps(), closure)
        this.stateProvider = stateProps.provider
        return this
    }

    Map<String,?> getVar_() {
        return this.variables.vars
    }

    Map<String,?> getSec_() {
        return this.variables.secrets
    }

    Result<DSL> addStateProvider(StateProvider stateProvider) {
        if (this.stateProvider) {
            return Result.error(this, null)
        }
        return Result.of(this)
    }

    void addDeclaredResource(Resource resource) {
        this.declaredResources.add(resource)
    }
}