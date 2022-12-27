package com.github.grooviter.stateit.core

class Dependency {
    String sourceField
    String targetField
    Resource parent

    Dependency(Resource parent, String sourceField) {
        this.sourceField = sourceField
        this.parent = parent
    }
}
