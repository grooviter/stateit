package com.github.grooviter.stateit.core

import groovy.transform.TupleConstructor

@TupleConstructor
trait ErrorEnum {
    String code
    String description
    ErrorType type

    Error toError() {
        return new Error(this.code, this.description, this.type.name())
    }

    public <T> Result<T> toResult() {
        return Result.error(this.toError())
    }

    public <T> Result<T> toResult(T context) {
        return Result.error(context, this.toError())
    }

    static ErrorType validationType() {
        return ErrorType.VALIDATION
    }

    static ErrorType executionType() {
        return ErrorType.EXECUTION
    }
}
