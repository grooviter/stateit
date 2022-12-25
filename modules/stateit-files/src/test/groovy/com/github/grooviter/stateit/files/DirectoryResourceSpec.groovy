package com.github.grooviter.stateit.files

import com.github.grooviter.stateit.core.Result
import com.github.grooviter.stateit.files.mkdir.MkdirProps
import com.github.grooviter.stateit.files.mkdir.MkdirResource
import spock.lang.Specification

class DirectoryResourceSpec extends Specification {
    void 'creating a directory resource without path should fail'() {
        given:
        MkdirProps directory = new MkdirProps()
        MkdirResource directoryResource = new MkdirResource("id", directory)

        when:
        Result<MkdirResource> result = directoryResource.create()

        then:
        result.isFailure()

        and:
        result.error.description == "path required"
    }

    void 'creating a directory resource changes its state to created'() {
        given:
        File directoryPath = new File("/tmp/kk1")
        MkdirProps directory = new MkdirProps(directoryPath.absolutePath)
        MkdirResource directoryResource = new MkdirResource("id", directory)

        when:
        Result<MkdirResource> result = directoryResource.create()

        then:
        result.isSuccess()

        cleanup:
        directoryPath.deleteDir()
    }

    void 'creating a directory resource creates a directory'() {
        given:
        File directoryPath = new File("/tmp/kk2")
        MkdirProps directory = new MkdirProps(directoryPath.absolutePath)
        MkdirResource directoryResource = new MkdirResource("id", directory)

        when:
        Result<MkdirResource> result = directoryResource.create()

        then:
        result.isSuccess()

        and:
        directoryPath.exists()
        directoryPath.isDirectory()

        cleanup:
        directoryPath.deleteDir()
    }

    void 'creating a directory in wrong place should not fulfill created'() {
        File directoryPath = new File("/kk")
        MkdirProps directory = new MkdirProps(directoryPath.absolutePath)
        MkdirResource directoryResource = new MkdirResource("id", directory)

        when:
        Result<MkdirResource> result = directoryResource.create()

        then:
        result.isFailure()
    }
}
