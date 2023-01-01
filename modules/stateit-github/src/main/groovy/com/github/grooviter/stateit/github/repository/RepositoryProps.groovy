package com.github.grooviter.stateit.github.repository

import com.github.grooviter.stateit.core.DSLProps
import groovy.transform.Canonical

@Canonical
class RepositoryProps extends DSLProps {

    static class Collaborators {
        List<String> ADMIN
        List<String> MAINTAINER
    }

    String name
    String owner
    Boolean is_private
    String default_branch
    String from_template
    Collaborators collaborators
}
