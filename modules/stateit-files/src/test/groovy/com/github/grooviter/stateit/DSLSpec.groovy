package com.github.grooviter.stateit

import com.github.grooviter.stateit.core.Plan
import com.github.grooviter.stateit.files.DirectoryResource
import groovy.json.JsonOutput
import spock.lang.Specification

class DSLSpec extends Specification {
    void 'declaring one resource successfully'() {
        when:
        Plan plan = DSL.stateit {
            directory("backup-directory") {
                path = "/tmp/backup"
            }
        }

        then:
        plan.resourcesDeclared.size() == 1
    }

    void 'declaring more than one resources successfully'() {
        when:
        Plan plan = DSL.stateit {
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

    void 'declaring plan with previous state'() {
        given:
        File myDir = new File("/tmp/my-dir")
        File stateFile = new File("/tmp/state.json")

        stateFile.text = JsonOutput.toJson([
            [
                id: "applied-id",
                type: DirectoryResource.TYPE,
                directory: [
                    path: "/tmp/my-dir"
                ]
            ],
            [
                id: "resource-to-remove",
                type: DirectoryResource.TYPE,
                directory: [
                    path: "/tmp/to-remove"
                ]
            ],
        ])

        when:
        Plan plan = DSL.stateit {
            directory("applied-id") {
                path = myDir.absolutePath
            }

            directory("to-apply-id") {
                path = "another"
            }

            state {
                path = "/tmp/state.json"
            }
        }

        then:
        plan.resourcesApplied.size() == 1
        plan.resourcesToApply.size() == 1
        plan.resourcesToRemove.size() == 1
        plan.resourcesInState.size() == 2

        cleanup:
        myDir.deleteDir()
    }
}
