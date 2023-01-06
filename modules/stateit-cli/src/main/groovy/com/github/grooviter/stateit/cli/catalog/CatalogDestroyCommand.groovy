package com.github.grooviter.stateit.cli.catalog

import com.github.grooviter.stateit.DSL
import com.github.grooviter.stateit.cli.Entrypoint
import com.github.grooviter.stateit.cli.ExitResultEvaluator
import picocli.CommandLine

import java.util.concurrent.Callable

@CommandLine.Command(
    name = "destroy",
    description = "destroys @|fg(red) ALL |@ resources contained in the catalog",
    aliases = ["dc", "destroy-catalog"]
)
class CatalogDestroyCommand extends CatalogCommand implements Callable<Integer>, CatalogLoader, ExitResultEvaluator {
    @CommandLine.ParentCommand()
    Entrypoint entrypoint

    @Override
    Integer call() throws Exception {
        return evalExitResult(loadCatalog(catalog, entrypoint.varFile).flatMap(DSL::destroy))
    }
}
