package com.github.grooviter.stateit.core

class DSLProps {
    private List<Dependency> dependencyList = []

    void addDependencyForField(String fieldName, Dependency dependency) {
        dependency.targetField = fieldName
        this.dependencyList.add(dependency)
    }

    List<Dependency> getDependencies() {
        return this.dependencyList
    }
}
