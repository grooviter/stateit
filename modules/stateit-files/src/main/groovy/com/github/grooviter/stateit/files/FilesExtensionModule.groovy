package com.github.grooviter.stateit.files

import com.github.grooviter.stateit.DSL
import com.github.grooviter.stateit.files.gzip.GzipProps
import com.github.grooviter.stateit.files.gzip.GzipResource
import com.github.grooviter.stateit.files.mkdir.MkdirProps
import com.github.grooviter.stateit.files.mkdir.MkdirResource

class FilesExtensionModule {
    static DSL mkdir(DSL dsl, String id, @DelegatesTo(MkdirProps) Closure closure) {
        MkdirProps mkdirProps = new MkdirProps()
        mkdirProps.with(closure.clone() as Closure<MkdirProps>)
        dsl.addDeclaredResource(new MkdirResource(id, mkdirProps))
        return dsl
    }

    static DSL gzip(DSL dsl, String id, @DelegatesTo(GzipProps) Closure closure) {
        GzipProps gzipProps = new GzipProps()
        gzipProps.with(closure.clone() as Closure<GzipProps>)
        dsl.addDeclaredResource(new GzipResource())
        return dsl
    }
}
