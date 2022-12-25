package com.github.grooviter.stateit.files

import com.github.grooviter.stateit.core.Provider
import com.github.grooviter.stateit.core.ResourceSerde

class DirectoryProvider implements Provider {
    Map<String, ResourceSerde<?>> SERDES =  [
        (DirectoryResource.class.name): new DirectoryResourceSerde()
    ] as Map<String, ResourceSerde<?>>

    @Override
    ResourceSerde<?> getSerdeByType(String type) {
        return SERDES[type]
    }
}
