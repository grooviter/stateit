package com.github.grooviter.stateit.cli

import com.github.grooviter.stateit.cli.catalog.ApplyCatalogCommand
import com.github.grooviter.stateit.cli.catalog.ValidateCatalogCommand
import com.github.grooviter.stateit.cli.plan.DestroyPlanCommand
import com.github.grooviter.stateit.cli.plan.ApplyPlanCommand
import com.github.grooviter.stateit.cli.plan.ValidatePlanCommand
import picocli.CommandLine

import java.util.concurrent.Callable

@CommandLine.Command(subcommands = [
    ValidatePlanCommand, ApplyPlanCommand, DestroyPlanCommand, ValidateCatalogCommand, ApplyCatalogCommand
])
class Entrypoint implements Callable<Integer> {
    @CommandLine.Option(names = ["--plan"], description = "StateIT Plan")
    File plan

    @CommandLine.Option(names = ["--catalog"], description = "StateIT Catalog")
    File catalog

    @CommandLine.Option(names = ["--var-file"], description = "StateIT variable file")
    File varFile

    Integer call() throws Exception {}

    static void main(String[] args) {
        System.exit(new CommandLine(new Entrypoint()).execute(args))
    }
}
