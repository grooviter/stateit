package com.github.grooviter.stateit.core

@Singleton(lazy = true, strict = false)
class Providers {
    private ServiceLoader<Provider> loader
    private Map<String,ResourceSerde> serdeMap = [:]

    private Providers() {
        loader = ServiceLoader.load(Provider, Providers.classLoader)
        loader.iterator().each {serdeMap.putAll(prepare(it.resourceSerdeList)) }
    }

    ResourceSerde resolveSerdeByType(String type) {
        return serdeMap[type]
    }

    static Map<String, ResourceSerde> prepare(List<ResourceSerde> serdeList) {
        return serdeList.collectEntries { [(it.getType()): it] }
    }
}
