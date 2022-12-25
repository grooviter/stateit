package com.github.grooviter.stateit.files

import com.github.grooviter.stateit.DSL

class FilesExtensionModule {
    static DSL directory(DSL dsl, String id, @DelegatesTo(DirectoryProps) Closure closure) {
        DirectoryProps directory = new DirectoryProps()
        directory.with(closure.clone() as Closure<DirectoryProps>)
        dsl.addDeclaredResource(new DirectoryResource(id, directory))
        return dsl
    }
}
