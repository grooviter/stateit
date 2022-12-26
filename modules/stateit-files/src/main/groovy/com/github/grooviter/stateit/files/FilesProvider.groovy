package com.github.grooviter.stateit.files

import com.github.grooviter.stateit.core.Provider
import com.github.grooviter.stateit.core.ResourceSerde
import com.github.grooviter.stateit.files.targz.TargzResourceSerde
import com.github.grooviter.stateit.files.mkdir.MkdirResouceSerde

class FilesProvider implements Provider {
    Map<String, ResourceSerde<?>> SERDES =  [
        new MkdirResouceSerde(),
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
