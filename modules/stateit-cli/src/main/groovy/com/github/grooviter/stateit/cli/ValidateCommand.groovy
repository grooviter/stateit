package com.github.grooviter.stateit.cli

import com.github.grooviter.stateit.core.PlanExecutor
import picocli.CommandLine
import java.util.concurrent.Callable

@CommandLine.Command(name = "validate", description = "validates resources declared in the plan")
class ValidateCommand implements Callable<Integer>, PlanLoader {
    @CommandLine.ParentCommand()
    Entrypoint entrypoint

    @Override
    Integer call() throws Exception {
        return new PlanExecutor(loadPlan(entrypoint.plan)).validate().isSuccess() ? 0 : 1
    }
}
