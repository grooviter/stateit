package com.github.grooviter.stateit.files

import com.github.grooviter.stateit.core.Provider
import com.github.grooviter.stateit.core.ResourceSerde
import com.github.grooviter.stateit.files.targz.TargzResourceSerde
import com.github.grooviter.stateit.files.directory.DirectoryResourceSerde

class SerdeProvider implements Provider {
    @Override
    List<ResourceSerde> getResourceSerdeList() {
        return [
            new DirectoryResourceSerde(),
            new TargzResourceSerde()
        ]
    }
}
