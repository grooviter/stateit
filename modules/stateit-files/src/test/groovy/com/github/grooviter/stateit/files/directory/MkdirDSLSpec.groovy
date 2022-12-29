package com.github.grooviter.stateit.files.directory

import com.github.grooviter.stateit.core.Plan
import com.github.grooviter.stateit.core.Result
import com.github.grooviter.stateit.test.BaseSpecification
import groovy.json.JsonOutput

import static com.github.grooviter.stateit.DSL.stateit
import static com.github.grooviter.stateit.DSL.validate
import static com.github.grooviter.stateit.DSL.destroy

class MkdirDSLSpec extends BaseSpecification {
    void 'declaring one resource successfully'() {
        when:
        Plan plan = stateit {
            directory("backup-directory") {
                path = "/tmp/backup"
            }
        }

        then:
        plan.resourcesDeclared.size() == 1
    }

    void 'declaring more than one resources successfully'() {
        when:
        Plan plan = stateit {
            directory("one-directory") {
                path = "/tmp/one"
            }

            directory("two-directory") {
                path = "/tmp/two"
            }
        }

        then:
        plan.resourcesDeclared.size() == 2
    }

    void 'declaring a directory without name should fail'() {
        when:
        Result<Plan> validationResult = validate stateit {
            directory("one-directory") {}
        }

        then:
        validationResult.isFailure()

        and:
        validationResult.error.code == "directory.path.missing"
    }

    void 'declaring plan with previous state'() {
        given:
        File myDir = new File("/tmp/my-dir")
        File stateFile = new File("/tmp/state.json")

        stateFile.text = JsonOutput.toJson([
            [
                id: "applied-id",
                type: DirectoryResource.name,
                directory: [
                    path: "/tmp/my-dir"
                ]
            ],
            [
                id: "resource-to-remove",
                type: DirectoryResource.name,
                directory: [
                    path: "/tmp/to-remove"
                ]
            ],
        ])

        when:
        Plan plan = stateit {
            directory("applied-id") {
                path = myDir.absolutePath
            }

            directory("to-apply-id") {
                path = "another"
            }

            state {
                provider = fileState("/tmp/state.json")
            }
        }

        then:
        plan.resourcesApplied.size() == 1
        plan.resourcesToApply.size() == 1
        plan.resourcesToRemove.size() == 1
        plan.resourcesInState.size() == 2

        cleanup:
        deleteDirs(myDir)
        deleteFiles(stateFile)
    }

    void 'destroying all resources'() {
        given:
        File myDir = new File("/tmp/my-dir")
        File stateFile = new File("/tmp/state.json")

        stateFile.text = JsonOutput.toJson([
            [id: "applied-id", type: DirectoryResource.name, props:[path: "/tmp/my-dir"]],
            [id: "resource-to-remove", type: DirectoryResource.name, props: [path: "/tmp/to-remove"]],
        ])

        and:
        Plan plan = stateit {
            directory("applied-id") {
                path = myDir.absolutePath
            }

            directory("to-apply-id") {
                path = "another"
            }

            state {
                provider = fileState("/tmp/state.json")
            }
        }

        when:
        Result<Plan> result = destroy plan

        then:
        with(result.context) {
            resourcesApplied.size()  == 0
            resourcesToApply.size()  == 2
            resourcesToRemove.size() == 0
            resourcesInState.size()  == 0
        }

        cleanup:
        deleteDirs(myDir)
        deleteFiles(stateFile)
    }
}
