package com.github.grooviter.stateit.github

import com.github.grooviter.stateit.DSL
import com.github.grooviter.stateit.github.branch.BranchProtectionProps
import com.github.grooviter.stateit.github.organization.OrganizationProps
import com.github.grooviter.stateit.github.repository.RepositoryProps
import com.github.grooviter.stateit.github.repository.RepositoryResource
import com.github.grooviter.stateit.github.team.TeamProps

import static com.github.grooviter.stateit.core.ClosureUtils.applyPropsToClosure

class DSLExtensionModule {
    static DSL github_repository(DSL dsl, String id, @DelegatesTo(RepositoryProps) Closure closure) {
        RepositoryProps props = applyPropsToClosure(new RepositoryProps(), closure)
        dsl.addDeclaredResource(new RepositoryResource(id, props))
        return dsl
    }

    static DSL github_branch_protection(DSL dsl, id, @DelegatesTo(BranchProtectionProps) Closure closure) {
        return dsl
    }

    static DSL github_organization(DSL dsl, id, @DelegatesTo(OrganizationProps) Closure closure) {

    }

    static DSL github_team(DSL dsl, id, @DelegatesTo(TeamProps) Closure closure) {

    }
}
