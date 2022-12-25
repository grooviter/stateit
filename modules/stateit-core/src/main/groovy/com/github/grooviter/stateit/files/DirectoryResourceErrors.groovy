package com.github.grooviter.stateit.files

import com.github.grooviter.stateit.core.ErrorEnum
import groovy.transform.TupleConstructor

@TupleConstructor(includeFields = true)
enum DirectoryResourceErrors implements ErrorEnum {
    ERROR_PATH_MISSING("directory.path.missing", "path required", validationType()),
    ERROR_UNDEFINED("directory.exception", "can't create path", executionType())
}