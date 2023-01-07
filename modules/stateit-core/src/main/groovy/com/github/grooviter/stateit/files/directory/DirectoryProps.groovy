package com.github.grooviter.stateit.files.directory

import com.github.grooviter.stateit.core.DSLProps
import com.github.grooviter.stateit.core.Dependency
import groovy.transform.TupleConstructor

@TupleConstructor
class DirectoryProps extends DSLProps {
    String path

    void setPath(Dependency dependency) {
        addDependencyForField("path", dependency)
    }

    void setPath(String path) {
        this.path = path
    }
}
