package com.github.grooviter.stateit.cli

import picocli.CommandLine

import java.util.concurrent.Callable

import static com.github.grooviter.stateit.DSL.destroy

@CommandLine.Command(name = "destroy", description = "destroys all resources declared in plan")
class DestroyCommand implements Callable<Integer>, PlanLoader {
    @CommandLine.ParentCommand
    Entrypoint entrypoint

    @Override
    Integer call() throws Exception {
        return evaluateCommandExit(destroy(loadPlan(entrypoint.plan)))
    }
}
