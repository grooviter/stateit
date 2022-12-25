package com.github.grooviter.stateit.core

interface ErrorEnum {
    String getCode()
    String getDescription()
    ErrorType getType()

    default Error toError() {
        return new Error(this.code, this.description, this.type.name())
    }

    default <T> Result<T> toResult() {
        return Result.error(this.toError())
    }

    default <T> Result<T> toResult(T context) {
        return Result.error(context, this.toError())
    }
}
