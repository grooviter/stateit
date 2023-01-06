package com.github.grooviter.stateit.cli

import com.github.grooviter.stateit.cli.catalog.CatalogApplyCommand
import com.github.grooviter.stateit.cli.catalog.CatalogDestroyCommand
import com.github.grooviter.stateit.cli.catalog.CatalogValidateCommand
import com.github.grooviter.stateit.cli.plan.PlanDestroyCommand
import com.github.grooviter.stateit.cli.plan.PlanApplyCommand
import com.github.grooviter.stateit.cli.plan.PlanValidateCommand
import picocli.CommandLine

import java.util.concurrent.Callable

@CommandLine.Command(
    name = "stateit",
    subcommands = [
        CatalogValidateCommand,
        CatalogApplyCommand,
        CatalogDestroyCommand,
        PlanValidateCommand,
        PlanApplyCommand,
        PlanDestroyCommand
    ])
class Entrypoint implements Callable<Integer> {
    @CommandLine.Option(names = ["-V", "--version"], versionHelp = true, description = "display version info")
    boolean versionInfoRequested

    @CommandLine.Option(names = ["-h", "--help"], usageHelp = true, description = "display this help message")
    boolean usageHelpRequested

    @CommandLine.Option(names = ["--var-file"], description = "variable file")
    File varFile

    Integer call() throws Exception {}

    static void main(String[] args) {
        System.exit(new CommandLine(new Entrypoint()).execute(args))
    }
}
