package com.github.grooviter.stateit.files.mkdir

import com.github.grooviter.stateit.core.DSLProps
import com.github.grooviter.stateit.core.Dependency
import groovy.transform.TupleConstructor

@TupleConstructor
class MkdirProps extends DSLProps {
    String path

    void setPath(Dependency dependency) {
        addDependencyForField("path", dependency)
    }

    void setPath(String path) {
        this.path = path
    }
}
