package com.github.grooviter.stateit.files.targz

import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.core.Result
import groovy.transform.builder.Builder

@Builder
class TargzResource extends Resource {
    String id
    String input
    String output
    TargzProps.Action type

    @Override
    Result<Resource> create() {
        if (!input) {
            return TargzErrors.INPUT_MISSING.toResult(this) as Result<Resource>
        }

        if (!output) {
            return TargzErrors.OUTPUT_MISSING.toResult(this) as Result<Resource>
        }

        try {
            if (TargzProps.Action.COMPRESS == type) {
                CompressionUtil.compressTargz(this.input, this.output)
            }

            if (TargzProps.Action.EXTRACT == type) {
                CompressionUtil.decompressTargz(this.input, this.output)
            }

        } catch (IOException ignored) {
            return TargzErrors.GZIP_ERROR.toResult(this) as Result<Resource>
        }

        return Result.of(this) as Result<Resource>
    }

    @Override
    Result<Resource> destroy() {
        return null
    }

    @Override
    Result<Resource> validate() {
        return null
    }
}
