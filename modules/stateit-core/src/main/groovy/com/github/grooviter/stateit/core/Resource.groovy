package com.github.grooviter.stateit.core

import groovy.transform.Canonical

@Canonical
abstract class Resource {
    String id

    abstract Result<Resource> create()
    abstract Result<Resource> destroy()
    abstract Result<Resource> validate()

    String getType() {
        return this.getClass().name
    }
}