package com.github.grooviter.stateit.files.targz

import com.github.grooviter.stateit.core.DSLProps
import com.github.grooviter.stateit.core.Dependency

class TargzProps extends DSLProps {
    String input
    String output
    Action action

    static enum Action {
        COMPRESS, EXTRACT
    }

    Action compress() {
        return Action.COMPRESS
    }

    Action extract() {
        return Action.EXTRACT
    }

    void setInput(Dependency dependency) {
        addDependencyForField("input", dependency)
    }

    void setInput(String path) {
        this.input = path
    }

    void setOutput(Dependency dependency) {
        addDependencyForField("output", dependency)
    }

    void setOutput(String path) {
        this.output = path
    }
}
