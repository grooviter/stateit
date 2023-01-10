package com.github.grooviter.stateit.github

import com.github.grooviter.stateit.DSL
import com.github.grooviter.stateit.github.organization.OrganizationProps
import com.github.grooviter.stateit.github.organization.OrganizationResource
import com.github.grooviter.stateit.github.repository.RepositoryProps
import com.github.grooviter.stateit.github.repository.RepositoryResource

import static com.github.grooviter.stateit.core.ClosureUtils.applyPropsToClosure

class DSLExtensionModule {
    static RepositoryResource github_repository(DSL dsl, String id, @DelegatesTo(RepositoryProps) Closure closure) {
        RepositoryProps props = applyPropsToClosure(new RepositoryProps(), closure)
        RepositoryResource resource = new RepositoryResource(id, props)
        dsl.addDeclaredResource(resource)
        return resource
    }

    static OrganizationResource github_organization(DSL dsl, String id, @DelegatesTo(OrganizationProps) Closure closure) {
        OrganizationProps props = applyPropsToClosure(new OrganizationProps(), closure)
        OrganizationResource resource = new OrganizationResource(id, props)
        dsl.addDeclaredResource(resource)
        return resource
    }
}
