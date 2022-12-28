package com.github.grooviter.stateit.files.targz

import com.github.grooviter.stateit.DSL
import com.github.grooviter.stateit.core.Plan
import com.github.grooviter.stateit.core.Result
import com.github.grooviter.stateit.test.BaseSpecification

class TargzDSLSpec extends BaseSpecification {
    void 'create a tar.gz from a directory successfully'() {
        given:
        File uncompressed = mkdir("/tmp/test-dir")
        File compressed = file("/tmp/test-dir.tar.gz")

        and:
        createTextFilesInDir(uncompressed, [hello: "hi!", bye: "bye"])

        and:
        Plan plan = DSL.stateit {
            targz("test-dir-gzip") {
                input  = uncompressed.absolutePath
                output = compressed.absolutePath
                action = compress()
            }
        }

        when:
        Result<Plan> planExecutionResult = executePlan(plan)

        then:
        planExecutionResult.isSuccess()

        cleanup:
        deleteDirs(uncompressed)
        deleteFiles(compressed)
    }

    void 'decompress a tar.gz successfully'() {
        given:
        File compressed = createTarGzFile("/tmp/compressed.tar.gz")
        File uncompressed = file("/tmp/uncompressed")

        and:
        Plan plan = DSL.stateit {
            targz("test-dir-gzip") {
                input  = compressed.absolutePath
                output = uncompressed.absolutePath
                action = extract()
            }
        }

        when:
        Result<Plan> planExecutionResult = executePlan(plan)

        then:
        planExecutionResult.isSuccess()

        cleanup:
        deleteDirs(uncompressed)
        deleteFiles(compressed)
    }

    void 'using dependencies'() {
        given:
        File compressed = file("/tmp/test-dir.tar.gz")
        File uncompressedFile = file("/tmp/ooo")
        File stateFile = file("/tmp/superstate.json")

        and:
        Plan plan = DSL.stateit {
            def uncompressed = directory("id-data") {
                path = uncompressedFile.absolutePath
            }

            targz("test-dir-gzip") {
                input  = uncompressed.path
                output = compressed.absolutePath
                action = compress()
            }

            state {
                provider = fileState(stateFile.absolutePath)
            }
        }

        when:
        Result<Plan> executedPlan = executePlan(plan)

        then:
        executedPlan.isSuccess()

        cleanup:
        deleteDirs(uncompressedFile)
        deleteFiles(stateFile)
    }
}
