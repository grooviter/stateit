package com.github.grooviter.stateit.files.targz

import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.core.Result
import spock.lang.Specification

class TargzResourceSpec extends Specification {
    void 'creating a gzip resource without INPUT path should fail'() {
        when:
        Result<Resource> result = TargzResource.builder().output("/tmp/gz/output.gz").build().create()

        then:
        result.error.code == 'gzip.input.missing'

        then:
        result.isFailure()
    }

    void 'creating a gzip resource without OUTPUT path should fail'() {
        when:
        Result<Resource> result = TargzResource.builder().input("/tmp/gz/input").build().create()

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
        Result<Resource> result = TargzResource.builder().input(inputFile.absolutePath)
                .output(outputFile.absolutePath)
                .build()
                .create()

        then:
        result.isSuccess()

        cleanup:
        inputFile.deleteDir()
        inputFile.delete()
    }
}
