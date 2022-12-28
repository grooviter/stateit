package com.github.grooviter.stateit.github.repository

import com.github.grooviter.stateit.core.Plan
import com.github.grooviter.stateit.core.Result
import spock.lang.IgnoreIf
import spock.lang.Specification

import static com.github.grooviter.stateit.DSL.stateit
import static com.github.grooviter.stateit.DSL.validate
import static com.github.grooviter.stateit.github.common.CredentialsResolver.resolveCredentials

class RepositoryDSLSpec extends Specification {
    void 'validating a repository fails when missing its name'() {
        when:
        Result<Plan> result = validate stateit {
            github_repository("shine/hope") {
                owner = "shine"
            }
        }

        then:
        result.isFailure()

        and:
        result.error.code == 'stateit.github.name.missing'
    }

    void 'validating a repository fails when missing its owner'() {
        when:
        Result<Plan> result = validate stateit {
            github_repository("shine/hope") {
                name  = "hope"
            }
        }

        then:
        result.isFailure()

        and:
        result.error.code == 'stateit.github.owner.missing'
    }

    @IgnoreIf({ resolveCredentials().success })
    void 'validating a repository fails when missing credentials'() {
        when:
        Result<Plan> result = validate stateit {
            github_repository("shine/hope") {
                owner = "shine"
                name  = "hope"
            }
        }

        then:
        result.isFailure()

        and:
        result.error.code == 'stateit.github.credentials.missing'
    }

    @IgnoreIf({ resolveCredentials().failure })
    void 'validating a repository succeeds'() {
        when:
        Result<Plan> result = validate stateit {
            github_repository("shine/hope") {
                owner = "shine"
                name  = "hope"
            }
        }

        then:
        result.isSuccess()
    }
}
