package com.github.grooviter.stateit.files.mkdir

import com.github.grooviter.stateit.core.ResourceSerde
import groovy.transform.CompileDynamic

class MkdirResourceSerde implements ResourceSerde<MkdirResource> {
    @Override
    MkdirResource fromMap(Map<String, ?> rawValues) {
        Map props = rawValues.props as Map
        String id = rawValues?.id?.toString()
        String path = props?.path?.toString()
        return new MkdirResource(id, new MkdirProps(path))
    }

    @Override
    String getType() {
        return MkdirResource.name
    }
}
