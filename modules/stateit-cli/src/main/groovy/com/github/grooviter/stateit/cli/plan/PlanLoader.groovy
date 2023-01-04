package com.github.grooviter.stateit.cli.plan

import com.github.grooviter.stateit.DSL
import com.github.grooviter.stateit.core.Plan

trait PlanLoader {
    static Plan loadPlan(File scriptFile, File varFile) {
        return DSL.stateit(scriptFile, varFile)
    }
}
