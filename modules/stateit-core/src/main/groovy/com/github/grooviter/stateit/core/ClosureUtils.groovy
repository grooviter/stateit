package com.github.grooviter.stateit.core

class ClosureUtils {
    static <U> U applyPropsToClosure(U props, @DelegatesTo(genericTypeIndex = 0) Closure closure) {
        props.with(closure.clone() as Closure<U>)
        return props
    }
}
