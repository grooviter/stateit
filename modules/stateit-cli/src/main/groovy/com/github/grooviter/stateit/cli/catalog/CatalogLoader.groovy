package com.github.grooviter.stateit.cli.catalog

import com.github.grooviter.stateit.DSL
import com.github.grooviter.stateit.core.Plan
import com.github.grooviter.stateit.core.Result

trait CatalogLoader {
    static Result<Plan> loadCatalog(File catalog, File variableFile) {
        return DSL.catalog(catalog, variableFile)
    }
}