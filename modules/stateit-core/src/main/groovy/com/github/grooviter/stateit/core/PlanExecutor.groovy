package com.github.grooviter.stateit.core

import groovy.json.JsonOutput
import groovy.transform.TupleConstructor
import groovy.util.logging.Slf4j

@Slf4j
@TupleConstructor
class PlanExecutor {
    Plan plan

    Result<Plan> execute() {
        return Result.of(plan)
            .flatMap(PlanExecutor::toApply)
            .flatMap(PlanExecutor::toRemove)
            .flatMap(PlanExecutor::serializeState)
    }

    private static Result<Plan> toApply(Plan stage) {
        List<Resource> applied = []
        for (Resource resource : stage.resourcesDeclared) {
            log.info "create ${resource.id}"
            Result<Resource> result = resource.create()
            if (result.isFailure()) {
                return Result.error(resolvePlan(stage, applied, []), result.error)
            } else {
                applied.add(resource)
            }
        }
        return Result.of(resolvePlan(stage, applied, []))
    }

    private static Result<Plan> toRemove(Plan stage) {
        List<Resource> removed = []
        for (Resource resource : stage.resourcesToRemove) {
            log.info "destroy ${resource.id}"
            Result<Resource> result = resource.destroy()
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

        log.info "save state"
        File stateFile = new File(stage.statePath)
        stateFile.text = JsonOutput.toJson(stage)
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
