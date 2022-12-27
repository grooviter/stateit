package com.github.grooviter.stateit.files.targz

import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.core.Result
import spock.lang.Specification

class TargzResourceSpec extends Specification {
    void 'creating a gzip resource without INPUT path should fail'() {
        when:
        TargzProps props = new TargzProps()
        props.output = "/tmp/output-dir"
        Result<Resource> result = new TargzResource("id", props).create()

        then:
        result.error.code == 'gzip.input.missing'

        then:
        result.isFailure()
    }

    void 'creating a gzip resource without OUTPUT path should fail'() {
        when:
        TargzProps props = new TargzProps()
        props.input = "/tmp/output-dir"
        Result<Resource> result = new TargzResource("id", props).create()

        then:
        result.error.code == 'gzip.output.missing'

        then:
        result.isFailure()
    }

    void 'creating a gzip resource with all parameters succeeds'() {
        given:
        File inputFile = new File("/tmp/to-compress")
        File outputFile = new File("/tmp/to-compress.tar.gz")

        when:
        inputFile.mkdir()
        TargzProps props = new TargzProps()
        props.input = inputFile.absolutePath
        props.output = outputFile.absolutePath
        Result<Resource> result = new TargzResource("id", props).create()

        then:
        result.isSuccess()

        cleanup:
        inputFile.deleteDir()
        inputFile.delete()
    }
}
