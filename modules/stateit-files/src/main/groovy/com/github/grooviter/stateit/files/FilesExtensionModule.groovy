package com.github.grooviter.stateit.files

import com.github.grooviter.stateit.DSL
import com.github.grooviter.stateit.files.targz.TargzProps
import com.github.grooviter.stateit.files.targz.TargzResource
import com.github.grooviter.stateit.files.directory.DirectoryProps
import com.github.grooviter.stateit.files.directory.DirectoryResource

class FilesExtensionModule {
    static DirectoryResource directory(DSL dsl, String id, @DelegatesTo(DirectoryProps) Closure closure) {
        DirectoryProps props = new DirectoryProps()
        props.with(closure.clone() as Closure<DirectoryProps>)
        DirectoryResource resource = new DirectoryResource(id, props)
        dsl.addDeclaredResource(resource)
        return resource
    }

    static TargzResource targz(DSL dsl, String id, @DelegatesTo(TargzProps) Closure closure) {
        TargzProps props = new TargzProps()
        props.with(closure.clone() as Closure<TargzProps>)
        TargzResource resource = new TargzResource(id, props)
        dsl.addDeclaredResource(resource)
        return resource
    }
}
