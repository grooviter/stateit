package com.github.grooviter.stateit.github

import com.github.grooviter.stateit.DSL
import com.github.grooviter.stateit.github.repository.RepositoryProps
import com.github.grooviter.stateit.github.repository.RepositoryResource

import static com.github.grooviter.stateit.core.ClosureUtils.applyPropsToClosure

class DSLExtensionModule {
    static DSL github_repository(DSL dsl, String id, @DelegatesTo(RepositoryProps) Closure closure) {
        RepositoryProps props = applyPropsToClosure(new RepositoryProps(), closure)
        dsl.addDeclaredResource(new RepositoryResource(id, props))
        return dsl
    }
}
