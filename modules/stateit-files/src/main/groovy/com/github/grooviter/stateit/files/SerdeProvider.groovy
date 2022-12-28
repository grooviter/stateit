package com.github.grooviter.stateit.files

import com.github.grooviter.stateit.core.Provider
import com.github.grooviter.stateit.core.ResourceSerde
import com.github.grooviter.stateit.files.targz.TargzResourceSerde
import com.github.grooviter.stateit.files.directory.DirectoryResourceSerde

class SerdeProvider implements Provider {
    Map<String, ResourceSerde<?>> SERDES =  [
            new DirectoryResourceSerde(),
            new TargzResourceSerde()
    ].collectEntries { process it }

    @Override
    ResourceSerde<?> getSerdeByType(String type) {
        return SERDES[type]
    }

    private static Map<String, ResourceSerde> process(ResourceSerde resourceSerde){
        return [(resourceSerde.getType()): resourceSerde]
    }
}
