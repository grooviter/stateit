package com.github.grooviter.stateit.core.variables

class HomeResolver extends FileResolver {
    HomeResolver() {
        super(homeDirFile())
    }

    @Override
    Variables load() {
        return super.load()
    }

    static File homeDirFile() {
        return new File(System.getProperty("user.home"), ".stateit/stateit.vars.toml")
    }
}