package com.github.grooviter.stateit.core.variables

import groovy.transform.TupleConstructor

@TupleConstructor
class VariablesLoader {
    List<VariablesLoaderResolver> resolverList = []

    Variables load() {
        if (this.resolverList.empty) {
            return new HomeResolver().load()
        }

        return resolverList
            .collect{ it.load() }
            .inject(new Variables()) { Variables agg, Variables next ->
                if (next.secrets) {
                    agg.secrets.putAll(next.secrets)
                }

                if (next.vars) {
                    agg.vars.putAll(next.vars)
                }

                return agg
            }
    }
}
