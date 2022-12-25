package com.github.grooviter.stateit.core

interface Provider {
    ResourceSerde getSerdeByType(String type)
}