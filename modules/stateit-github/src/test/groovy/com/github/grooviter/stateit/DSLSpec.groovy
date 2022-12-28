package com.github.grooviter.stateit

import com.github.grooviter.stateit.core.Plan
import com.github.grooviter.stateit.core.Result
import spock.lang.Specification

import static com.github.grooviter.stateit.DSL.stateit
import static com.github.grooviter.stateit.DSL.validate

class DSLSpec extends Specification {
    void 'creating a repository fails when missing its name'() {
        when:
        Result<Plan> result = validate stateit {
            github_repository("shine/hope") {
                owner = "shine"
            }
        }

        then:
        result.isFailure()
    }
}
