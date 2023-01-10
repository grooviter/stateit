package com.github.grooviter.stateit.core

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
            .flatMap(PlanExecutor::destroyOrphans)
            .flatMap(PlanExecutor::serializeState)
    }

    Result<Plan> validate() {
        log.info "validating resources to apply"
        return Result.of(plan)
            .sideEffect(PlanExecutor::showSummary)
            .flatMap(PlanExecutor::validateResourcesToApply)
    }

    Result<Plan> destroy() {
        log.info "destroying all resources apply"
        return Result.of(plan)
            .sideEffect { log.info "deleting ALL (${it.resourcesInState.size()}) resources" }
            .flatMap(PlanExecutor::deleteAllResources)
            .flatMap(PlanExecutor::serializeState)
    }

    private static Result<Plan> deleteAllResources(Plan stage) {
        List<Resource> removed = []
        for (Resource resource : stage.resourcesInState) {
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

    private static Result<Plan> validateResourcesToApply(Plan plan) {
        Result<Resource> failed = plan.resourcesToApply
            .collect { it.validate() }
            .find { it.isFailure() }

        if (failed) {
            log.info "VALIDATION FAILED!"
            log.info "resource: ${failed.context.id} - error: ${failed.error.code}"
            return Result.error(plan, failed.error)
        }

        log.info "VALIDATION SUCCEEDED!"
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

    private static Result<Plan> destroyOrphans(Plan stage) {
        List<Resource> removed = []
        for (Resource resource : stage.resourcesToRemove) {
            log.info "destroy ${resource.id}"
            Result<Resource> result = resource.applyWhenDestroying()
            if (result.isFailure()) {
                return Result.error(resolvePlan(stage, stage.resourcesApplied, removed), result.error)
            } else {
                removed.add(resource)
            }
        }
        return Result.of(resolvePlan(stage, [], removed))
    }

    private static Result<Plan> serializeState(Plan stage) {
        if (!stage.stateProvider) {
            log.info "no state provider found skipping state storage"
            return Result.of(stage)
        }

        log.info "saving state"
        return stage.stateProvider.store(stage)
    }

    private static Plan resolvePlan(Plan previous, List<Resource> applied, List<Resource> removed) {
        return Plan.builder()
            .stateProvider(previous.stateProvider)
            .resourcesDeclared(previous.resourcesDeclared)
            .resourcesInState(new HashSet<>(previous.resourcesInState + applied - removed).toList())
            .build()
    }
}
