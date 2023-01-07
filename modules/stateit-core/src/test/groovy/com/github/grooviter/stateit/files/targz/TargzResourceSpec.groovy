package com.github.grooviter.stateit.files.targz

import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.core.Result
import com.github.grooviter.stateit.testing.FileUtilsAware
import spock.lang.Specification

class TargzResourceSpec extends Specification implements FileUtilsAware {
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
        File inputFile = mkdir("/tmp/to-compress")
        File outputFile = new File("/tmp/to-compress.tar.gz")

        when:
        TargzProps props = new TargzProps()
        props.input = inputFile.absolutePath
        props.output = outputFile.absolutePath
        Result<Resource> result = new TargzResource("id", props).create()

        then:
        result.isSuccess()

        cleanup:
        deleteDirs(inputFile)
        deleteFiles(inputFile)
    }
}
