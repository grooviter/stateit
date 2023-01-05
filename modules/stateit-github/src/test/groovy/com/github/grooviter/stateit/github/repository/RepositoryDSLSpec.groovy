package com.github.grooviter.stateit.github.repository

import com.github.grooviter.stateit.core.Plan
import com.github.grooviter.stateit.core.Result
import spock.lang.IgnoreIf
import spock.lang.Specification

import static com.github.grooviter.stateit.DSL.plan
import static com.github.grooviter.stateit.DSL.validate
import static com.github.grooviter.stateit.DSL.execute
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
            def org = github_organization("@shine"){
                name = "shine";
                type = "open-source"
            }

            def admins = github_team("@shine/admins") {
                organization = org.name
                name         = "admins"
                role         = "admins"
            }

            def maintainers = github_team("@shine/maintainers") {
                organization = org.name
                name         = "maintainers"
                role         = "maintainers"
            }

            def repo = github_repository("@shine/hope") {
                owner          = org.name
                name           = "hope"
                is_private     = true
                default_branch = "main"
                from_template  = "olvido/gara"
                collaborators  = [
                    ADMIN: ["owner"],
                    MAINTAINER: ["owner", "collaborator"]
                ]
            }

            github_branch_protection("@shine/hope/main") {
                branch             = "main"
                repository         = repo.full_name
                codeowners_enable  = true
                codeowners_content = """
                .github/workflows @shine/admins
                package.json @shine/leaders
                """
            }
        }

        expect:
        execute plan isSuccess()

        cleanup:
        destroy plan
    }
}
