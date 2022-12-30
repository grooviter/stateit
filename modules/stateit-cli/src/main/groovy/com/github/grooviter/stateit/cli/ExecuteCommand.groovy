package com.github.grooviter.stateit.cli


import picocli.CommandLine
import java.util.concurrent.Callable
import static com.github.grooviter.stateit.DSL.execute

@CommandLine.Command(name = "execute", description = "applies all resources declared in the plan")
class ExecuteCommand implements Callable<Integer>, PlanLoader {
    @CommandLine.ParentCommand
    Entrypoint entrypoint

    @Override
    Integer call() throws Exception {
        return evaluateCommandExit(execute(loadPlan(entrypoint.plan, entrypoint.varFile)))
    }
}
