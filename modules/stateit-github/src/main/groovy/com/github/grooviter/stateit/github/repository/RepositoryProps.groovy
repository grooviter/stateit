package com.github.grooviter.stateit.github.repository

import com.github.grooviter.stateit.core.DSLProps
import groovy.transform.Canonical

@Canonical
class RepositoryProps extends DSLProps {
    String name
    String owner
}
