package com.github.grooviter.stateit.cli.catalog

import picocli.CommandLine

@CommandLine.Command(synopsisHeading      = "%nUsage:%n%n",
        descriptionHeading   = "%nDescription:%n%n",
        parameterListHeading = "%nParameters:%n%n",
        optionListHeading    = "%nOptions:%n%n",
        commandListHeading   = "%nCommands:%n%n")
class CatalogCommand {
    @CommandLine.Parameters(index = "0")
    File catalog
}
