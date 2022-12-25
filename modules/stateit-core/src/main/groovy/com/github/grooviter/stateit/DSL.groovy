package com.github.grooviter.stateit

import com.github.grooviter.stateit.core.Json
import com.github.grooviter.stateit.core.Plan
import com.github.grooviter.stateit.core.Providers
import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.core.StateProps

class DSL {

    private List<Resource> declaredResources = []
    private List<Resource> stateResources = []
    private String statePath = ""

    static Plan stateit(@DelegatesTo(DSL) Closure closure) {
        DSL dsl = new DSL()
        dsl.with(closure.clone() as Closure<DSL>)
        return Plan.builder()
            .statePath(dsl.statePath)
            .resourcesDeclared(dsl.declaredResources)
            .resourcesInState(dsl.stateResources)
            .build()
    }

    DSL state(@DelegatesTo(StateProps) Closure closure) {
        StateProps stateProps = new StateProps()
        stateProps.with(closure.clone() as Closure<StateProps>)
        this.stateResources = loadResourcesFromState(stateProps)
        this.statePath = stateProps.path
        return this
    }

    private static List<Resource> loadResourcesFromState(StateProps stateProps) {
        return Json.loadResources(stateProps.path).collect { Map<String, ?> resource ->
            return Providers.instance.resolveSerdeByType(resource.type.toString()).fromMap(resource)
        } as List<Resource>
    }

    void addDeclaredResource(Resource resource) {
        this.declaredResources.add(resource)
    }
}