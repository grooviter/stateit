package com.github.grooviter.stateit.core

import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j

@Slf4j
class Json {

    static List<Map<String,?>> loadResources(String path) {
        File jsonFile = new File(path)

        if (!jsonFile.exists()) {
            log.info "state file not found"
            return [] as List<Map<String,?>>
        }

        log.info "state file found... loading resources"
        return new JsonSlurper().parse(jsonFile) as List<Map<String,?>>
    }
}
