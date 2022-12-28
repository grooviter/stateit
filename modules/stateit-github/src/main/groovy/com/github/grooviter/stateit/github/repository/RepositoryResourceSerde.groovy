package com.github.grooviter.stateit.github.repository

import com.github.grooviter.stateit.core.ResourceSerde

class RepositoryResourceSerde implements ResourceSerde<RepositoryResource> {
    @Override
    RepositoryResource fromMap(Map<String, ?> rawValues) {
        RepositoryProps props = new RepositoryProps()
        Map<String,?> rawProps = rawValues.props as Map<String,?>
        props.name = rawProps?.name?.toString()
        props.owner = rawProps?.owner?.toString()
        return new RepositoryResource(rawValues.id?.toString(),props)
    }

    @Override
    String getType() {
        return RepositoryResource.name
    }
}
