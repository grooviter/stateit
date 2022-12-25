package com.github.grooviter.stateit.files.mkdir

import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.core.Result
import groovy.transform.TupleConstructor


import static MkdirResourceErrors.ERROR_PATH_MISSING
import static MkdirResourceErrors.ERROR_UNDEFINED

@TupleConstructor(includes = ["id", "directory"])
class MkdirResource extends Resource {
    String id
    MkdirProps directory

    static final String TYPE = MkdirResource.name

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
