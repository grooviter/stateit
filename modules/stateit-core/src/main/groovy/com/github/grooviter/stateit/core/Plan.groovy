package com.github.grooviter.stateit.core

import groovy.transform.builder.Builder

@Builder(includes = ["statePath", "resourcesDeclared", "resourcesInState"])
class Plan {
    String statePath
    List<Resource> resourcesDeclared
    List<Resource> resourcesInState

    List<Resource> getResourcesApplied() {
        return intersection()
    }

    List<Resource> getResourcesToApply() {
        return resourcesDeclared - intersection()
    }

    List<Resource> getResourcesToRemove() {
        return resourcesInState - resourcesDeclared
    }

    private List<Resource> intersection() {
        return resourcesDeclared.intersect(resourcesInState)
    }
}
