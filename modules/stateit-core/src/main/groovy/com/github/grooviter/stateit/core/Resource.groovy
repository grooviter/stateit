package com.github.grooviter.stateit.core

abstract class Resource {
    abstract String getId()
    abstract Result<Resource> create()
    abstract Result<Resource> destroy()
    abstract Result<Resource> validate()
}