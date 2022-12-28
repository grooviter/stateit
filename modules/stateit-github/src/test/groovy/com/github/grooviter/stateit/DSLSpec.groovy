package com.github.grooviter.stateit

import com.github.grooviter.stateit.core.Plan
import com.github.grooviter.stateit.core.PlanExecutor
import com.github.grooviter.stateit.core.Result
import spock.lang.Specification

class DSLSpec extends Specification {
    void 'creating a repository fails when missing its name'() {
        given:
        Plan plan = DSL.stateit {
            github_repository("shine/hope") {
                owner = "shine"
            }
        }

        when:
        PlanExecutor planExecutor = new PlanExecutor(plan)
        Result<Plan> executionResult = planExecutor.validate()

        then:
        executionResult.isFailure()
    }
}
