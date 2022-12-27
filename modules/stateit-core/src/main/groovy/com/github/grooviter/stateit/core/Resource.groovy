package com.github.grooviter.stateit.core

abstract class Resource {
    abstract String getId()
    abstract Result<Resource> applyWhenCreating()
    abstract Result<Resource> applyWhenDestroying()
    abstract Result<Resource> validate()
    abstract DSLProps getProps()

    Result<Resource> create() {
        resolveDependencies()
        Result<Resource> validation = this.validate()

        if (validation.isFailure()) {
            return validation
        }

        return this.applyWhenCreating()
    }

    private void resolveDependencies() {
        this.props.dependencies.each { Dependency dependency ->
            this.setPropertyValue(dependency.targetField, dependency.parent.getPropertyValue(dependency.sourceField))
        }
    }

    def <U> U getPropertyValue(String propertyName) {
        return this.props[propertyName] as U
    }
    void setPropertyValue(String propertyName, Object value) {
        this.props[propertyName] = value
    }
}