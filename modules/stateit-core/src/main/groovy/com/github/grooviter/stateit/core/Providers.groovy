package com.github.grooviter.stateit.core

import com.github.grooviter.stateit.files.DirectoryResourceSerde

class Providers {

    ResourceSerde resolveSerdeByType(String type) {
        return new DirectoryResourceSerde()
    }
}
