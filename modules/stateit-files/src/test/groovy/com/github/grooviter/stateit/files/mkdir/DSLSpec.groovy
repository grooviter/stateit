package com.github.grooviter.stateit.files.mkdir

import com.github.grooviter.stateit.DSL
import com.github.grooviter.stateit.core.Plan
import com.github.grooviter.stateit.core.PlanExecutor
import com.github.grooviter.stateit.core.Result
import com.github.grooviter.stateit.files.mkdir.MkdirResource
import groovy.json.JsonOutput
import spock.lang.Specification

class DSLSpec extends Specification {
    void 'declaring one resource successfully'() {
        when:
        Plan plan = DSL.stateit {
            mkdir("backup-directory") {
                path = "/tmp/backup"
            }
        }

        then:
        plan.resourcesDeclared.size() == 1
    }

    void 'declaring more than one resources successfully'() {
        when:
        Plan plan = DSL.stateit {
            mkdir("one-directory") {
                path = "/tmp/one"
            }

            mkdir("two-directory") {
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
                type: MkdirResource.name,
                directory: [
                    path: "/tmp/my-dir"
                ]
            ],
            [
                id: "resource-to-remove",
                type: MkdirResource.name,
                directory: [
                    path: "/tmp/to-remove"
                ]
            ],
        ])

        when:
        Plan plan = DSL.stateit {
            mkdir("applied-id") {
                path = myDir.absolutePath
            }

            mkdir("to-apply-id") {
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
