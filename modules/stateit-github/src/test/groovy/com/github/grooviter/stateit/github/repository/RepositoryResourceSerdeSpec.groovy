package com.github.grooviter.stateit.github.repository

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import spock.lang.Specification

class RepositoryResourceSerdeSpec extends Specification {
    void 'deserialize a repository resource successfully'() {
        given:
        RepositoryProps props = new RepositoryProps("name", "owner")

        when:
        String jsonString = JsonOutput.toJson(props: props)
        Map<String,?> fromJson = new JsonSlurper().parseText(jsonString) as Map<String,?>

        and:
        RepositoryResourceSerde serde = new RepositoryResourceSerde()
        RepositoryResource resource = serde.fromMap(fromJson)

        then:
        resource.props == props
    }
}
