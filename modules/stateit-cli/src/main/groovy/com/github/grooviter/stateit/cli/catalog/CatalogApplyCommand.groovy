package com.github.grooviter.stateit.cli.catalog

import com.github.grooviter.stateit.DSL
import com.github.grooviter.stateit.cli.ExitResultEvaluator
import com.github.grooviter.stateit.cli.Entrypoint
import picocli.CommandLine

import java.util.concurrent.Callable

@CommandLine.Command(
        name = "apply",
        description = "apply resources contained in the catalog",
        aliases = ["ac", "apply-catalog"]
)
class CatalogApplyCommand extends CatalogCommand implements Callable<Integer>, CatalogLoader, ExitResultEvaluator {
    @CommandLine.ParentCommand()
    Entrypoint entrypoint

    @Override
    Integer call() throws Exception {
        return evalExitResult(loadCatalog(catalog, entrypoint.varFile).flatMap(DSL::apply))
    }
}
