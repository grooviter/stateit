package com.github.grooviter.stateit.files

import com.github.grooviter.stateit.DSL
import com.github.grooviter.stateit.files.targz.TargzProps
import com.github.grooviter.stateit.files.targz.TargzResource
import com.github.grooviter.stateit.files.mkdir.MkdirProps
import com.github.grooviter.stateit.files.mkdir.MkdirResource

class FilesExtensionModule {
    static DSL mkdir(DSL dsl, String id, @DelegatesTo(MkdirProps) Closure closure) {
        MkdirProps mkdirProps = new MkdirProps()
        mkdirProps.with(closure.clone() as Closure<MkdirProps>)
        dsl.addDeclaredResource(new MkdirResource(id, mkdirProps))
        return dsl
    }

    static DSL targz(DSL dsl, String id, @DelegatesTo(TargzProps) Closure closure) {
        TargzProps gzipProps = new TargzProps()
        gzipProps.with(closure.clone() as Closure<TargzProps>)
        dsl.addDeclaredResource(TargzResource.builder()
                .id(id)
                .input(gzipProps.input)
                .output(gzipProps.output)
                .type(gzipProps.action).build())
        return dsl
    }
}
