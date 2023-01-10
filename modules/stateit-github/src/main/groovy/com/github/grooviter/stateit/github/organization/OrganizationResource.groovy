package com.github.grooviter.stateit.github.organization

import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.core.ResourceConventions
import com.github.grooviter.stateit.core.Result

@ResourceConventions
class OrganizationResource extends Resource {
    String id
    OrganizationProps props

    @Override
    Result<Resource> applyWhenCreating() {
        return Result.of(this) as Result<Resource>
    }

    @Override
    Result<Resource> applyWhenDestroying() {
        return Result.of(this) as Result<Resource>
    }

    @Override
    Result<Resource> validate() {
        return Result.of(this) as Result<Resource>
    }

    String getName() {
        return this.props.name
    }
}
