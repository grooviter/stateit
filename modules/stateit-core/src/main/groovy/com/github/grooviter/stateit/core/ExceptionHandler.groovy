package com.github.grooviter.stateit.core

interface ExceptionHandler {
    public <U> Result<U> handle(Throwable th)
}