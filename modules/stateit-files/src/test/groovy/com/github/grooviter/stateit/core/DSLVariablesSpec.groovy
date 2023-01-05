package com.github.grooviter.stateit.core

import com.github.grooviter.stateit.core.variables.FileResolver
import com.github.grooviter.stateit.core.variables.VariablesLoader
import com.github.grooviter.stateit.testing.FileUtilsAware
import spock.lang.Specification

import static com.github.grooviter.stateit.DSL.execute
import static com.github.grooviter.stateit.DSL.plan

class DSLVariablesSpec extends Specification implements FileUtilsAware {
    void 'resolve variables in DSL'() {
        given:
        File directoryRef = randomFileRef()
        File variablesFile = randomFileWithContent """
        [vars]
        my_directory_path = "$directoryRef"
        """
        VariablesLoader loader = new VariablesLoader([new FileResolver(variablesFile)])

        when:
        Result<Plan> result = execute plan(loader) {
            directory("my-directory") {
                path = var_.my_directory_path
            }
        }

        then:
        result.success

        and:
        directoryRef.exists()

        cleanup:
        deleteDirs(directoryRef)
        deleteFiles(variablesFile)
    }
}
