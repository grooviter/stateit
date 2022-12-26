package com.github.grooviter.stateit.files.targz

import com.github.grooviter.stateit.DSL
import com.github.grooviter.stateit.core.Plan
import com.github.grooviter.stateit.core.Result
import com.github.grooviter.stateit.test.BaseSpecification

class DSLSpec extends BaseSpecification {
    void 'create a tar.gz from a directory successfully'() {
        given:
        File testDir = mkdir("/tmp/test-dir")
        File gzipFile = file("/tmp/test-dir.tar.gz")

        Plan plan = DSL.stateit {
            targz("test-dir-gzip") {
                input  = testDir.absolutePath
                output = gzipFile.absolutePath
                action = compress()
            }
        }

        when:
        createTextFiles(testDir, [hello: "hi!", bye: "bye"])
        Result<Plan> planExecutionResult = executePlan(plan)

        then:
        planExecutionResult.isSuccess()

        cleanup:
        deleteDirs(testDir)
        deleteFiles(gzipFile)
    }
}
