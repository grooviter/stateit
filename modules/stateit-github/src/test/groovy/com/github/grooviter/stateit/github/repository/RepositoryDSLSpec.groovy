package com.github.grooviter.stateit.github.repository

import com.github.grooviter.stateit.core.Plan
import com.github.grooviter.stateit.core.Result
import spock.lang.IgnoreIf
import spock.lang.Specification

import static com.github.grooviter.stateit.DSL.plan
import static com.github.grooviter.stateit.DSL.validate
import static com.github.grooviter.stateit.DSL.apply
import static com.github.grooviter.stateit.DSL.destroy
import static com.github.grooviter.stateit.github.common.CredentialsResolver.resolveCredentials

class RepositoryDSLSpec extends Specification {
    void 'validating a repository fails when missing its name'() {
        when:
        Result<Plan> result = validate plan {
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
        Result<Plan> result = validate plan {
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
        Result<Plan> result = validate plan {
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
        Result<Plan> result = validate plan {
            github_repository("shine/hope") {
                owner = "shine"
                name  = "hope"
            }
        }

        then:
        result.isSuccess()
    }

    @IgnoreIf({ resolveCredentials().failure })
    void 'create a repository succeeds'() {
        given:
        Plan plan = plan {
            def org = github_organization("@my-devops-playground") {
                name = "my-devops-playground";
                type = "open-source"
            }

            def repo = github_repository("@my-devops-playground/hope") {
                owner          = org.name
                name           = "hope"
                is_private     = true
                default_branch = "main"
                from_template  = "my-devops-playground/stateit-template"
            }
        }

        when:
        Result<Plan> result = apply plan

        then:
        result.success

        and:
        result.context.resourcesApplied.size() == 2

        cleanup:
        result.sideEffect { destroy(it) }
    }
}
