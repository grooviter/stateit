package com.github.grooviter.stateit.files

import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.core.Result
import groovy.transform.TupleConstructor


import static DirectoryResourceErrors.ERROR_PATH_MISSING
import static DirectoryResourceErrors.ERROR_UNDEFINED

@TupleConstructor(includes = ["id", "directory"])
class DirectoryResource extends Resource {
    String id
    DirectoryProps directory

    static final String TYPE = DirectoryResource.name

    @Override
    Result<Resource> create() {
        Result<Resource> validation = validate()

        if (validation.isFailure()) {
            return validation as Result<Resource>
        }

        boolean successful = new File(directory.path).mkdirs()

        if (!successful) {
            return ERROR_UNDEFINED.toResult(this) as Result<Resource>
        }

        return Result.of(this) as Result<Resource>
    }

    @Override
    Result<Resource> destroy() {
        return Result.of(this) as Result<Resource>
    }

    @Override
    Result<Resource> validate() {
        if (!directory?.path){
            return ERROR_PATH_MISSING.toResult(this) as Result<Resource>
        }
        return Result.of(this) as Result<Resource>
    }
}
