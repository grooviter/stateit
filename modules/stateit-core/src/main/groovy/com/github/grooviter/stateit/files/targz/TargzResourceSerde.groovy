package com.github.grooviter.stateit.files.targz

import com.github.grooviter.stateit.core.ResourceSerde

class TargzResourceSerde implements ResourceSerde<TargzResource> {
    @Override
    TargzResource fromMap(Map<String, ?> rawValues) {
        TargzProps props = new TargzProps()
        props.input = rawValues.input?.toString()
        props.output = rawValues.output?.toString()
        props.action = TargzProps.Action.valueOf(rawValues.type?.toString())
        return new TargzResource(rawValues.id?.toString(), props)
    }

    @Override
    String getType() {
        return TargzResource.name
    }
}
