package com.github.grooviter.stateit.core.variables

import groovy.transform.TupleConstructor

@TupleConstructor
class Variables {
    Map<String,?> vars = [:]
    Map<String,?> secrets = [:]
}
