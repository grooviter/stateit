package com.github.grooviter.stateit.cli

import com.github.grooviter.stateit.core.PlanExecutor
import picocli.CommandLine

import java.util.concurrent.Callable

@CommandLine.Command(name = "execute")
class ExecuteCommand implements Callable<Integer>, PlanLoader {
    @CommandLine.ParentCommand
    Entrypoint entrypoint

    @Override
    Integer call() throws Exception {
        return new PlanExecutor(loadPlan(entrypoint.plan)).execute().isSuccess() ? 0 : 1
    }
}
