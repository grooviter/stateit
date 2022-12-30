package com.github.grooviter.stateit.cli

import picocli.CommandLine

import java.util.concurrent.Callable

@CommandLine.Command(subcommands = [ValidateCommand, ExecuteCommand, DestroyCommand])
class Entrypoint implements Callable<Integer> {
    @CommandLine.Option(names = ["--plan"], description = "StateIT Plan", required = true)
    File plan

    @CommandLine.Option(names = ["--var-file"], description = "StateIT variable file")
    File varFile

    Integer call() throws Exception {}

    static void main(String[] args) {
        System.exit(new CommandLine(new Entrypoint()).execute(args))
    }
}
