package com.github.grooviter.stateit.core

import groovy.transform.AnnotationCollector
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor

@TupleConstructor
@EqualsAndHashCode(includes = ["id"])
@AnnotationCollector
@interface ResourceConventions {

}