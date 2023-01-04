package com.github.grooviter.stateit.cli.plan

import com.github.grooviter.stateit.cli.ExitResultEvaluator
import com.github.grooviter.stateit.cli.Entrypoint
import picocli.CommandLine
import java.util.concurrent.Callable
import static com.github.grooviter.stateit.DSL.validate

@CommandLine.Command(name = "validate", description = "validates resources declared in the plan")
class ValidatePlanCommand implements Callable<Integer>, PlanLoader, ExitResultEvaluator  {
    @CommandLine.ParentCommand()
    Entrypoint entrypoint

    @Override
    Integer call() throws Exception {
        return evalExitResult(validate(loadPlan(entrypoint.plan, entrypoint.varFile)))
    }
}
