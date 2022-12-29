package com.github.grooviter.stateit.core.variables

import groovy.toml.TomlSlurper
import groovy.transform.TupleConstructor

@TupleConstructor
class FileResolver implements VariablesLoaderResolver {
    File variableFile

    @Override
    Variables load() {
        if (!variableFile.exists()) {
            return new Variables()
        }

        Map<String,?> result = new TomlSlurper().parse(variableFile) as Map<String,?>
        Variables variables = new Variables(result.vars as Map<String,?>, result.secrets as Map<String,?>)
        return variables
    }
}
