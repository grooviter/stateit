package com.github.grooviter.stateit.core

import com.github.grooviter.stateit.core.variables.FileResolver
import com.github.grooviter.stateit.core.variables.Variables
import com.github.grooviter.stateit.core.variables.VariablesLoader
import com.github.grooviter.stateit.testing.FileUtilsAware
import spock.lang.Specification

class VariablesLoaderSpec extends Specification implements FileUtilsAware {
    void 'loading variables doesn\'t fail ever if there is no home file'() {
        when:
        Variables variables = new VariablesLoader().load()

        then:
        variables
    }

    void 'loading from a specific file returns empty if file doesn\'t exists'() {
        given:
        File variableFile = randomFileWithContent """
        [vars]
        version = "1.0.0"
        
        [secrets]
        password = "superpower"
        """

        when:
        Variables variables = new VariablesLoader([new FileResolver(variableFile)]).load()

        then:
        variables.vars.version == "1.0.0"
        variables.secrets.password == "superpower"

        cleanup:
        deleteFiles(variableFile)
    }
}
