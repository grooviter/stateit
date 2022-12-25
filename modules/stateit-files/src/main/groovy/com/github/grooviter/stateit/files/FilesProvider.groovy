package com.github.grooviter.stateit.files

import com.github.grooviter.stateit.core.Provider
import com.github.grooviter.stateit.core.ResourceSerde
import com.github.grooviter.stateit.files.mkdir.MkdirResource
import com.github.grooviter.stateit.files.mkdir.MkdirResouceSerde

class FilesProvider implements Provider {
    Map<String, ResourceSerde<?>> SERDES =  [
        (MkdirResource.class.name): new MkdirResouceSerde()
    ] as Map<String, ResourceSerde<?>>

    @Override
    ResourceSerde<?> getSerdeByType(String type) {
        return SERDES[type]
    }
}
