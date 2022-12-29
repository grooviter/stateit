package com.github.grooviter.stateit.core

import groovy.transform.builder.Builder

@Builder(includes = ["stateProvider", "resourcesDeclared", "resourcesInState"])
class Plan {
    StateProvider stateProvider
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
