package com.github.grooviter.stateit.cli

import picocli.CommandLine
import java.util.concurrent.Callable
import static com.github.grooviter.stateit.DSL.validate

@CommandLine.Command(name = "validate", description = "validates resources declared in the plan")
class ValidateCommand implements Callable<Integer>, PlanLoader {
    @CommandLine.ParentCommand()
    Entrypoint entrypoint

    @Override
    Integer call() throws Exception {
        return evaluateCommandExit(validate(loadPlan(entrypoint.plan, entrypoint.varFile)))
    }
}
