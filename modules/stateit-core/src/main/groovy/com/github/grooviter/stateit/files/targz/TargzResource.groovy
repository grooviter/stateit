package com.github.grooviter.stateit.files.targz

import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.core.ResourceConventions
import com.github.grooviter.stateit.core.Result

@ResourceConventions
class TargzResource extends Resource {
    String id
    TargzProps props

    @Override
    Result<Resource> applyWhenCreating() {
        try {
            if (TargzProps.Action.COMPRESS == this.props.action) {
                CompressionUtil.compressTargz(this.props.input, this.props.output, this.props.overwrite)
            }

            if (TargzProps.Action.EXTRACT == this.props.action) {
                CompressionUtil.decompressTargz(this.props.input, this.props.output, this.props.overwrite)
            }

        } catch (IOException ignored) {
            return TargzErrors.GZIP_ERROR.toResult(this) as Result<Resource>
        }

        return Result.of(this) as Result<Resource>
    }

    @Override
    Result<Resource> applyWhenDestroying() {
        return null
    }

    @Override
    Result<Resource> validate() {
        if (!props.input) {
            return TargzErrors.INPUT_MISSING.toResult(this) as Result<Resource>
        }

        if (!props.output) {
            return TargzErrors.OUTPUT_MISSING.toResult(this) as Result<Resource>
        }
        return Result.of(this) as Result<Resource>
    }
}
