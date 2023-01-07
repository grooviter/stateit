package com.github.grooviter.stateit.files.targz

import com.github.grooviter.stateit.core.ErrorEnum
import com.github.grooviter.stateit.core.ErrorType

enum TargzErrors implements ErrorEnum {
    INPUT_MISSING("gzip.input.missing", "input is required", ErrorType.VALIDATION),
    OUTPUT_MISSING("gzip.output.missing", "output is required", ErrorType.VALIDATION),
    GZIP_ERROR("gzip.compressing.error", "something happened while compressing", ErrorType.EXECUTION)

    String code
    String description
    ErrorType type

    TargzErrors(String code, String description, ErrorType type) {
        this.code = code
        this.description = description
        this.type = type
    }
}