package com.github.grooviter.stateit.github.repository

import com.github.grooviter.stateit.core.DSLProps
import groovy.transform.Canonical

@Canonical
class RepositoryProps extends DSLProps {
    String name
    String owner
    Boolean is_private
    String default_branch
    String from_template
}
