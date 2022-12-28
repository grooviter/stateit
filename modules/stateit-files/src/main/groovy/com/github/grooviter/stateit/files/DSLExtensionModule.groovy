package com.github.grooviter.stateit.files

import com.github.grooviter.stateit.DSL
import com.github.grooviter.stateit.files.targz.TargzProps
import com.github.grooviter.stateit.files.targz.TargzResource
import com.github.grooviter.stateit.files.directory.DirectoryProps
import com.github.grooviter.stateit.files.directory.DirectoryResource

import static com.github.grooviter.stateit.core.ClosureUtils.applyPropsToClosure

class DSLExtensionModule {
    static DirectoryResource directory(DSL dsl, String id, @DelegatesTo(DirectoryProps) Closure closure) {
        DirectoryProps props = applyPropsToClosure(new DirectoryProps(), closure)
        DirectoryResource resource = new DirectoryResource(id, props)
        dsl.addDeclaredResource(resource)
        return resource
    }

    static TargzResource targz(DSL dsl, String id, @DelegatesTo(TargzProps) Closure closure) {
        TargzProps props = applyPropsToClosure(new TargzProps(), closure)
        TargzResource resource = new TargzResource(id, props)
        dsl.addDeclaredResource(resource)
        return resource
    }
}
