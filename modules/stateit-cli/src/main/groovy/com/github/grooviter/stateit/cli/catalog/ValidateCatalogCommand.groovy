package com.github.grooviter.stateit.cli.catalog

import com.github.grooviter.stateit.DSL
import com.github.grooviter.stateit.cli.ExitResultEvaluator
import com.github.grooviter.stateit.cli.Entrypoint
import picocli.CommandLine

import java.util.concurrent.Callable

@CommandLine.Command(name = "validates", description = "validates resources contained in the catalog")
class ValidateCatalogCommand  implements Callable<Integer>, CatalogLoader, ExitResultEvaluator {
    @CommandLine.ParentCommand()
    Entrypoint entrypoint

    @Override
    Integer call() throws Exception {
        return evalExitResult(loadCatalog(entrypoint.catalog, entrypoint.varFile).flatMap(DSL::validate))
    }
}
