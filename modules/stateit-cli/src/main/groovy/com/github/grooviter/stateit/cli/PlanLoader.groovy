package com.github.grooviter.stateit.cli

import com.github.grooviter.stateit.DSL
import com.github.grooviter.stateit.core.Plan
import com.github.grooviter.stateit.core.Result
import com.github.grooviter.stateit.core.variables.FileResolver
import com.github.grooviter.stateit.core.variables.VariablesLoader

trait PlanLoader {
    static Plan loadPlan(File scriptFile, File varFile) {
        VariablesLoader loader = new VariablesLoader([new FileResolver(varFile)])
        GroovyShell shell = new GroovyShell(new Binding([DSL: DSL, loader: loader]))
        String scriptText = "DSL.stateit(loader) { ${scriptFile.text } }"
        return shell.evaluate(scriptText) as Plan
    }

    static Integer evaluateCommandExit(Result result) {
        return result.success ? 0 : 1
    }
}
