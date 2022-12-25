package com.github.grooviter.stateit.core

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

@ToString
@EqualsAndHashCode(includes = ["code"])
@TupleConstructor
class Error {
    String code
    String description
    String category
}
