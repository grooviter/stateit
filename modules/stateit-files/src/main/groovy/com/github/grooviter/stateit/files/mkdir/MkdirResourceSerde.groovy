package com.github.grooviter.stateit.files.mkdir

import com.github.grooviter.stateit.core.ResourceSerde
import groovy.transform.CompileDynamic

class MkdirResourceSerde implements ResourceSerde<MkdirResource> {
    @Override
    @CompileDynamic
    MkdirResource fromMap(Map<String, ?> rawValues) {
        String id = rawValues.id?.toString()
        String path = rawValues.directory.path.toString()
        return new MkdirResource(id, new MkdirProps(path))
    }

    @Override
    String getType() {
        return MkdirResource.name
    }
}
