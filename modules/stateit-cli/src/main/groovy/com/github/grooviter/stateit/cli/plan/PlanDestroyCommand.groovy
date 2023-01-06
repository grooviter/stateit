package com.github.grooviter.stateit.cli.plan

import com.github.grooviter.stateit.cli.ExitResultEvaluator
import com.github.grooviter.stateit.cli.Entrypoint
import picocli.CommandLine

import java.util.concurrent.Callable

import static com.github.grooviter.stateit.DSL.destroy

@CommandLine.Command(name = "destroy-plan", description = "destroys all resources declared in plan", aliases = ["dp"])
class PlanDestroyCommand extends PlanCommand implements Callable<Integer>, PlanLoader, ExitResultEvaluator  {
    @CommandLine.ParentCommand
    Entrypoint entrypoint

    @Override
    Integer call() throws Exception {
        return evalExitResult(destroy(loadPlan(plan, entrypoint.varFile)))
    }
}
