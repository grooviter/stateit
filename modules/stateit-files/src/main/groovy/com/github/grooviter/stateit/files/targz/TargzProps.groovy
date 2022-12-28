package com.github.grooviter.stateit.files.targz

import com.github.grooviter.stateit.core.DSLProps
import com.github.grooviter.stateit.core.Dependency

import java.util.function.Supplier

class TargzProps extends DSLProps {
    String input
    String output
    Action action
    boolean overwrite
    String cron

    static enum Action {
        COMPRESS, EXTRACT
    }

    static Action compress() {
        return Action.COMPRESS
    }

    static Action extract() {
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

    void setOutput(Supplier<String> output) {

    }

    void setOutput(String path) {
        this.output = path
    }
}
