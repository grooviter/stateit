package com.github.grooviter.stateit.files

import com.github.grooviter.stateit.DSL
import com.github.grooviter.stateit.files.targz.TargzProps
import com.github.grooviter.stateit.files.targz.TargzResource
import com.github.grooviter.stateit.files.mkdir.MkdirProps
import com.github.grooviter.stateit.files.mkdir.MkdirResource

class FilesExtensionModule {
    static MkdirResource mkdir(DSL dsl, String id, @DelegatesTo(MkdirProps) Closure closure) {
        MkdirProps props = new MkdirProps()
        props.with(closure.clone() as Closure<MkdirProps>)
        MkdirResource resource = new MkdirResource(id, props)
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
