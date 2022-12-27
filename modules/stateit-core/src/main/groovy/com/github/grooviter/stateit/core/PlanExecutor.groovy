package com.github.grooviter.stateit.core

import groovy.json.JsonGenerator
import groovy.transform.TupleConstructor
import groovy.util.logging.Slf4j

@Slf4j
@TupleConstructor
class PlanExecutor {
    Plan plan

    Result<Plan> execute() {
        return Result.of(plan)
            .sideEffect(PlanExecutor::showSummary)
            .flatMap(PlanExecutor::create)
            .flatMap(PlanExecutor::destroy)
            .flatMap(PlanExecutor::serializeState)
    }

    Result<Plan> validate() {
        return Result.of(plan)
    }

    private static void showSummary(Plan plan) {
        log.info "TO APPLY:\t ${plan.resourcesToApply.size()}"
        log.info "TO REMOVE:\t ${plan.resourcesToRemove.size()}"
    }

    private static Result<Plan> create(Plan stage) {
        List<Resource> applied = []
        for (Resource resource : stage.resourcesToApply) {
            log.info "create ${resource.id}"
            Result<Resource> result = resource.create()
            if (result.isFailure()) {
                log.error "error while applying [${resource.id}] -- ${result.error.code}"
                return Result.error(resolvePlan(stage, applied, []), result.error)
            } else {
                applied.add(resource)
            }
        }
        return Result.of(resolvePlan(stage, applied, []))
    }

    private static Result<Plan> destroy(Plan stage) {
        List<Resource> removed = []
        for (Resource resource : stage.resourcesToRemove) {
            log.info "destroy ${resource.id}"
            Result<Resource> result = resource.applyWhenDestroying()
            if (result.isFailure()) {
                return Result.error(resolvePlan(stage, [], removed), result.error)
            } else {
                removed.add(resource)
            }
        }
        return Result.of(resolvePlan(stage, [], removed))
    }

    private static Result<Plan> serializeState(Plan stage) {
        if (!stage.statePath) {
            return Result.of(stage)
        }

        JsonGenerator generator = new JsonGenerator.Options()
            .addConverter(Plan) { Plan plan, String key ->
                return plan.resourcesApplied
            }
            .addConverter(Resource) { Resource resource, String key ->
                return [id: resource.id, props: resource.props, type: resource.getClass().name]
            }
            .addConverter(Dependency) { Dependency dependency, String key ->
                return [parent: dependency.parent.id, target: dependency.targetField, source: dependency.sourceField]
            }.build()

        log.info "saving state"
        File stateFile = new File(stage.statePath)
        stateFile.text = generator.toJson(stage)
        return Result.of(stage)
    }

    private static Plan resolvePlan(Plan previous, List<Resource> applied, List<Resource> removed) {
        return Plan.builder()
            .statePath(previous.statePath)
            .resourcesDeclared(previous.resourcesDeclared)
            .resourcesInState(new HashSet<>(previous.resourcesInState + applied - removed).toList())
            .build()
    }
}
