package com.github.grooviter.stateit.cli.plan

import com.github.grooviter.stateit.cli.ExitResultEvaluator
import com.github.grooviter.stateit.cli.Entrypoint
import picocli.CommandLine
import java.util.concurrent.Callable
import static com.github.grooviter.stateit.DSL.apply

@CommandLine.Command(name = "apply-plan", description = "applies all resources declared in the plan", aliases = ["ap"])
class PlanApplyCommand extends PlanCommand implements Callable<Integer>, PlanLoader, ExitResultEvaluator {
    @CommandLine.ParentCommand
    Entrypoint entrypoint

    @Override
    Integer call() throws Exception {
        return evalExitResult(apply(loadPlan(plan, entrypoint.varFile)))
    }
}
