package com.github.grooviter.stateit.files.targz

import com.github.grooviter.stateit.core.ResourceSerde

class TargzResourceSerde implements ResourceSerde<TargzResource> {
    @Override
    TargzResource fromMap(Map<String, ?> rawValues) {
        return TargzResource.builder()
            .id(rawValues.id?.toString())
            .input(rawValues.input?.toString())
            .output(rawValues.output?.toString())
            .type(TargzProps.Action.valueOf(rawValues.type?.toString()))
            .build()
    }

    @Override
    String getType() {
        return TargzResource.name
    }
}
