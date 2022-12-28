package com.github.grooviter.stateit

import com.github.grooviter.stateit.core.Plan
import com.github.grooviter.stateit.core.PlanExecutor
import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.core.Result
import com.github.grooviter.stateit.core.StateProps
import com.github.grooviter.stateit.core.StateProvider

import static com.github.grooviter.stateit.core.ClosureUtils.applyPropsToClosure

class DSL {

    private List<Resource> declaredResources = []
    private List<Resource> stateResources = []
    private String statePath = ""
    private StateProvider stateProvider

    static Result<Plan> validate(Plan plan) {
        return new PlanExecutor(plan).validate()
    }

    static Result<Plan> execute(Plan plan) {
        return new PlanExecutor(plan).execute()
    }

    static Plan stateit(@DelegatesTo(DSL) Closure closure) {
        DSL dsl = applyPropsToClosure(new DSL(), closure)
        return Plan.builder()
            .statePath(dsl.statePath)
            .stateProvider(dsl.stateProvider)
            .resourcesDeclared(dsl.declaredResources)
            .resourcesInState(dsl.stateResources)
            .build()
    }

    DSL state(@DelegatesTo(StateProps) Closure closure) {
        StateProps stateProps = applyPropsToClosure(new StateProps(), closure)
        this.statePath = stateProps.path
        this.stateProvider = stateProps.provider

        stateProps.provider
            .load()
            .sideEffect {plan ->
                this.stateResources = plan.resourcesInState
            }

        return this
    }

    void addDeclaredResource(Resource resource) {
        this.declaredResources.add(resource)
    }
}