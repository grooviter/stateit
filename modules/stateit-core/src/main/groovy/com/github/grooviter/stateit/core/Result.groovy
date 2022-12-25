package com.github.grooviter.stateit.core

import groovy.transform.TupleConstructor

import java.util.function.*

@TupleConstructor
class Result<T> {
    T context
    Error error

    static <A> Result<A> error(A context, Error error) {
        return new Result<A>(context, error)
    }

    static <A> Result<A> error(Error error) {
        return new Result<A>(null, error)
    }

    static <A> Result<A> of(A context) {
        return new Result<A>(context, null)
    }

    static <A> Result<A> of(Optional<A> optional, Error error) {
        if (optional.isEmpty()) {
            return Result.error(error)
        }
        return of(optional.get())
    }

    static <A> Result<A> of(Optional<A> optional, ErrorEnum errorEnum) {
        return of(optional, errorEnum.toError())
    }

    static <A> Result<A> resultOf(Optional<A> optional, ErrorEnum errorEnum) {
        return of(optional, errorEnum.toError())
    }

    static <A> Result<A> resultOfNullable(A nullable, ErrorEnum errorEnum) {
        return resultOf(Optional.ofNullable(nullable), errorEnum)
    }

    boolean isFailure() {
        return this.error != null
    }

    boolean isSuccess() {
        return !isFailure()
    }

    Error getError() {
        return this.error
    }

    T getContext() {
        return this.context
    }

    public <B> Result<B> map(Function<T, B> fn) {
        if (this.isFailure()) {
            return Result.error(this.error)
        }

        return Result.of(fn.apply(this.context))
    }

    public <C, B> Result<C> mapWith(Result<B> with, BiFunction<T, B, C> fn) {
        return this.flatMap((T context) -> with.map((B o) -> fn.apply(context, o)))
    }

    Result<T> peek(Consumer<T> mutation) {
        mutation.accept(this.context)
        return this
    }

    Result<T> sideEffect(Consumer<T> mutation) {
        if (!this.isFailure()) {
            mutation.accept(this.context)
        }
        return this
    }


    Result<T> filter(Predicate<T> check, Error error) {
        if (this.isFailure() || check.test(this.context)) {
            return this
        }

        return Result.error(this.context, error)
    }

    Result<T> filter(Predicate<T> check, ErrorEnum errorEnum) {
        return filter(check, errorEnum.toError())
    }

    @SuppressWarnings("unchecked")
    public <B> Result<B> flatMap(Function<T, Result<B>> fn) {
        if (this.isFailure()) {
            return (Result<B>) this
        }

        return fn.apply(this.context)
    }

    public <B> Result<B> flatMap(Function<T, Optional<B>> fn, ErrorEnum errorEnum) {
        if (this.isFailure()) {
            return this
        }
        return resultOf(fn.apply(this.context), errorEnum)
    }


    public <B> Result<B> flatMapSupplier(Supplier<Result<B>> success, Supplier<Result<B>> error) {
        if (this.isFailure()) {
            return error.get()
        }
        return success.get()
    }

    Result<Boolean> mapToBoolean() {
        if (this.isFailure()) {
            return Result.of(false)
        }
        return Result.of(true)
    }

    Optional<T> toOptional() {
        return this.isFailure() ? Optional.empty() : Optional.of(this.context)
    }

    public <B, C> Result<C> flatMapWith(Result<B> other, BiFunction<T, B, Result<C>> fn) {
        return this.flatMap(context -> other.flatMap(o -> fn.apply(context, o)))
    }

    static <A, B, C> Result<C> flatMapWith(Result<A> left, Result<B> right, BiFunction<A, B, Result<C>> fn) {
        return left.flatMap(l -> right.flatMap(r -> fn.apply(l, r)))
    }

    Result<T> orResult(Result<T> result) {
        if (this.isFailure()) {
            return result
        }
        return this
    }

    Result<T> orContext(T context) {
        if (this.isFailure()) {
            return Result.of(context)
        }
        return this
    }
}
