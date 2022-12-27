package com.github.grooviter.stateit.files.directory

import com.github.grooviter.stateit.core.ErrorEnum
import com.github.grooviter.stateit.core.ErrorType

enum MkdirErrors implements ErrorEnum {
    ERROR_PATH_MISSING("directory.path.missing", "path required", ErrorType.VALIDATION),
    ERROR_UNDEFINED("directory.exception", "can't create path", ErrorType.EXECUTION)

    String code
    String description
    ErrorType type

    MkdirErrors(String code, String description, ErrorType type) {
        this.code = code
        this.description = description
        this.type = type
    }
}