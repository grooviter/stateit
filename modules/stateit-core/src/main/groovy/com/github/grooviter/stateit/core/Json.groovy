package com.github.grooviter.stateit.core

import groovy.json.JsonSlurper

class Json {

    static List<Map<String,?>> loadResources(String path) {
        File jsonFile = new File(path)

        if (!jsonFile.exists()) {
            return [] as List<Map<String,?>>
        }

        return new JsonSlurper().parse(jsonFile) as List<Map<String,?>>
    }
}
