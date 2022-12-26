package com.github.grooviter.stateit.files.targz

class TargzProps {
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
}
