package com.github.grooviter.stateit.github.repository

import com.github.grooviter.stateit.core.ErrorEnum
import com.github.grooviter.stateit.core.ErrorType

import static com.github.grooviter.stateit.core.ErrorType.VALIDATION

enum RepositoryErrors implements ErrorEnum {
    ERROR_NAME_MISSING("stateit.github.name.missing", "repository name is required", VALIDATION),
    ERROR_OWNER_MISSING("stateit.github.owner.missing", "repository owner is required", VALIDATION)

    String code
    String description
    ErrorType type

    RepositoryErrors(String code, String description, ErrorType type) {
        this.code = code
        this.description = description
        this.type = type
    }
}
