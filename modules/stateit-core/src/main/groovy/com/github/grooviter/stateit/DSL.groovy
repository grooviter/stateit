package com.github.grooviter.stateit

import com.github.grooviter.stateit.core.Json
import com.github.grooviter.stateit.core.Plan
import com.github.grooviter.stateit.core.Providers
import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.files.DirectoryProps
import com.github.grooviter.stateit.files.DirectoryResource
import com.github.grooviter.stateit.core.StateProps

class DSL {

    private List<Resource> declaredResources = []
    private List<Resource> stateResources = []
    private String statePath = ""
    private Providers providers = new Providers()

    static Plan stateit(@DelegatesTo(DSL) Closure closure) {
        DSL dsl = new DSL()
        dsl.with(closure.clone() as Closure<DSL>)
        return Plan.builder()
            .statePath(dsl.statePath)
            .resourcesDeclared(dsl.declaredResources)
            .resourcesInState(dsl.stateResources)
            .build()
    }

    DSL directory(String id, @DelegatesTo(DirectoryProps) Closure closure) {
        DirectoryProps directory = new DirectoryProps()
        directory.with(closure.clone() as Closure<DirectoryProps>)
        declaredResources.add(new DirectoryResource(id, directory))
        return this
    }

    DSL state(@DelegatesTo(StateProps) Closure closure) {
        StateProps stateProps = new StateProps()
        stateProps.with(closure.clone() as Closure<StateProps>)
        this.stateResources = loadResourcesFromState(stateProps)
        this.statePath = stateProps.path
        return this
    }

    private List<Resource> loadResourcesFromState(StateProps stateProps) {
        return Json.loadResources(stateProps.path).collect { Map<String, ?> resource ->
            return this.providers.resolveSerdeByType(resource.type.toString()).fromMap(resource)
        } as List<Resource>
    }
}