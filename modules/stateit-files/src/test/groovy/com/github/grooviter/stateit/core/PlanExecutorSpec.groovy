package com.github.grooviter.stateit.core

import com.github.grooviter.stateit.DSL
import com.github.grooviter.stateit.testing.FileUtilsAware
import spock.lang.Specification

class PlanExecutorSpec extends Specification implements FileUtilsAware {
    void 'executing a plan successfully'() {
        given:
        File dest = new File("/tmp/kk1")
        Plan plan = DSL.stateit {
            directory("garbage") {
                path = dest.absolutePath
            }
        }

        when:
        PlanExecutor planExecutor = new PlanExecutor(plan)
        Result<Plan> result = planExecutor.execute()

        then:
        result.isSuccess()

        and:
        dest.exists()

        cleanup:
        deleteDirs(dest)
    }

    void 'executing a wrong plan stops execution'() {
        given:
        File dest = new File("/notallowed")
        Plan plan = DSL.stateit {
            directory("garbage") {
                path = dest.absolutePath
            }
        }

        when:
        PlanExecutor planExecutor = new PlanExecutor(plan)
        Result<Plan> result = planExecutor.execute()

        then:
        result.isFailure()

        and:
        !dest.exists()
    }

    void 'executing a wrong plan returns error info'() {
        given:
        File dest = new File("/notallowed")
        Plan plan = DSL.stateit {
            directory("garbage") {
                path = dest.absolutePath
            }
        }

        when:
        PlanExecutor planExecutor = new PlanExecutor(plan)
        Result<Plan> result = planExecutor.execute()

        then:
        result.error.code === "directory.exception"
    }

    void 'executing 1/2 wrong plan returns one resource applied and another not applied'() {
        given:
        File notAllowed = new File("/notallowed")
        File yesAllowed = new File("/tmp/kk3")

        Plan plan = DSL.stateit {
            directory("treasure") {
                path = yesAllowed.absolutePath
            }

            directory("garbage") {
                path = notAllowed.absolutePath
            }
        }

        when:
        PlanExecutor planExecutor = new PlanExecutor(plan)
        Result<Plan> result = planExecutor.execute()

        then:
        result.error.code === "directory.exception"

        and:
        with(result.context) {
            resourcesDeclared.size() == 2
            resourcesApplied.size() == 1
            resourcesInState.size() == 1
        }

        cleanup:
        deleteDirs(yesAllowed)
    }

    void 'saving the plan execution state successfully'() {
        given:
        File yesAllowed = new File("/tmp/kk3")
        File stateFile = new File("/tmp/state.json")

        Plan plan = DSL.stateit {
            directory("treasure") {
                path = yesAllowed.absolutePath
            }

            state {
                provider = fileState(stateFile.absolutePath)
            }
        }

        when:
        PlanExecutor planExecutor = new PlanExecutor(plan)
        Result<Plan> result = planExecutor.execute()

        then:
        result.isSuccess()

        and:
        stateFile.exists()

        cleanup:
        deleteDirs(yesAllowed)
        deleteFiles(stateFile)
    }
}
