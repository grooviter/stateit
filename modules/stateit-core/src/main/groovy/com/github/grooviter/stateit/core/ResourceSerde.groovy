package com.github.grooviter.stateit.core

interface ResourceSerde<T> {
    T fromMap(Map<String,?> rawValues)
    String getType()
}
