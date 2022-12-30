package com.github.grooviter.stateit.cli

import picocli.CommandLine

import java.util.concurrent.Callable

@CommandLine.Command(subcommands = [ValidateCommand, ExecuteCommand, DestroyCommand])
class Entrypoint implements Callable<Integer> {
    @CommandLine.Option(names = ["--plan"], description = "Stateit Plan", required = true)
    String plan

    Integer call() throws Exception {}

    static void main(String[] args) {
        System.exit(new CommandLine(new Entrypoint()).execute(args))
    }
}
