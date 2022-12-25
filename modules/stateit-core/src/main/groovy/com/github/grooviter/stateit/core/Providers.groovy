package com.github.grooviter.stateit.core

@Singleton(lazy = true, strict = false)
class Providers {
    private ServiceLoader<Provider> loader

    private Providers() {
        loader = ServiceLoader.load(Provider, Providers.classLoader)
    }

    ResourceSerde resolveSerdeByType(String type) {
        Iterator<Provider> libraries = loader.iterator()
        ResourceSerde serde = null
        while (serde == null && libraries.hasNext()) {
            Provider library = libraries.next()
            serde = library.getSerdeByType(type)
        }
        return Optional.ofNullable(serde).orElse(null)
    }
}
