package com.github.grooviter.stateit.files.mkdir

import com.github.grooviter.stateit.core.Dependency
import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.core.Result
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor

import static MkdirErrors.ERROR_PATH_MISSING
import static MkdirErrors.ERROR_UNDEFINED

@TupleConstructor
@EqualsAndHashCode(includes = ["id"])
class MkdirResource extends Resource {
    String id
    MkdirProps props

    @Override
    Result<Resource> applyWhenCreating() {
        boolean successful = new File(props.path).mkdirs()

        if (!successful) {
            return ERROR_UNDEFINED.toResult(this) as Result<Resource>
        }

        return Result.of(this) as Result<Resource>
    }

    @Override
    Result<Resource> applyWhenDestroying() {
        boolean successful = new File(props.path).deleteDir()

        if (!successful) {
            return ERROR_UNDEFINED.toResult(this) as Result<Resource>
        }

        return Result.of(this) as Result<Resource>
    }

    @Override
    Result<Resource> validate() {
        if (!props?.path){
            return ERROR_PATH_MISSING.toResult(this) as Result<Resource>
        }
        return Result.of(this) as Result<Resource>
    }

    Dependency getPath() {
        return new Dependency(this, "path")
    }
}
