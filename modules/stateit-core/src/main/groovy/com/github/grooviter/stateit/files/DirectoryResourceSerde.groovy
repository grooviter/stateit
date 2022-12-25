package com.github.grooviter.stateit.files

import com.github.grooviter.stateit.core.ResourceSerde
import groovy.transform.CompileDynamic

class DirectoryResourceSerde implements ResourceSerde<DirectoryResource> {
    @Override
    @CompileDynamic
    DirectoryResource fromMap(Map<String, ?> rawValues) {
        String id = rawValues.id?.toString()
        String path = rawValues.directory.path.toString()
        return new DirectoryResource(id, new DirectoryProps(path))
    }
}
