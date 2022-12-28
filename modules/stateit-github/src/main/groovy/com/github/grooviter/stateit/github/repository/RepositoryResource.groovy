package com.github.grooviter.stateit.github.repository

import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.core.Result
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor

import static com.github.grooviter.stateit.github.repository.RepositoryErrors.ERROR_NAME_MISSING
import static com.github.grooviter.stateit.github.repository.RepositoryErrors.ERROR_OWNER_MISSING

@TupleConstructor
@EqualsAndHashCode(includes = ["id"])
class RepositoryResource extends Resource {
    String id
    RepositoryProps props

    @Override
    Result<Resource> applyWhenCreating() {
        return null
    }

    @Override
    Result<Resource> applyWhenDestroying() {
        return null
    }

    @Override
    Result<Resource> validate() {
        if (!props.name) {
            return ERROR_NAME_MISSING.toResult(this) as Result<Resource>
        }

        if (!props.owner) {
            return ERROR_OWNER_MISSING.toResult(this) as Result<Resource>
        }

        return Result.of(this) as Result<Resource>
    }
}
