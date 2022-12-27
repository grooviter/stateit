package com.github.grooviter.stateit.files.directory

import com.github.grooviter.stateit.core.ResourceSerde

class MkdirResourceSerde implements ResourceSerde<DirectoryResource> {
    @Override
    DirectoryResource fromMap(Map<String, ?> rawValues) {
        Map props = rawValues.props as Map
        String id = rawValues?.id?.toString()
        String path = props?.path?.toString()
        return new DirectoryResource(id, new DirectoryProps(path))
    }

    @Override
    String getType() {
        return DirectoryResource.name
    }
}
