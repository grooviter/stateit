package com.github.grooviter.stateit.files.targz

import com.github.grooviter.stateit.core.Plan
import com.github.grooviter.stateit.core.Result
import com.github.grooviter.stateit.testing.FileUtilsAware
import spock.lang.Specification

import static com.github.grooviter.stateit.DSL.stateit
import static com.github.grooviter.stateit.DSL.execute

class TargzDSLSpec extends Specification implements FileUtilsAware {
    void 'create a tar.gz from a directory successfully'() {
        given:
        File uncompressed = mkdir("/tmp/test-dir")
        File compressed = file("/tmp/test-dir.tar.gz")

        when:
        Result<Plan> result = execute stateit {
            targz("test-dir-gzip") {
                input  = uncompressed.absolutePath
                output = compressed.absolutePath
                action = compress()
            }
        }

        then:
        result.isSuccess()

        and:
        compressed.exists()

        cleanup:
        deleteDirs(uncompressed)
        deleteFiles(compressed)
    }

    void 'decompress a tar.gz successfully'() {
        given:
        File compressed = createTarGzFile()
        File uncompressed = file("/tmp/uncompressed")

        when:
        Result<Plan> result = execute stateit {
            targz("test-dir-gzip") {
                input  = compressed.absolutePath
                output = uncompressed.absolutePath
                action = extract()
            }
        }

        then:
        result.isSuccess()

        cleanup:
        deleteDirs(uncompressed)
        deleteFiles(compressed)
    }

    File createTarGzFile() {
        File directoryToCompress = mkdirRandom()
        File directoryContent = randomFileWithContent("something")
        mvFileToDir(directoryContent, directoryToCompress)
        File compressedFile = randomFileRefWithSuffix("tar.gz")
        CompressionUtil.compressTargz(directoryToCompress.absolutePath, compressedFile.absolutePath, false)
        directoryToCompress.deleteDir()
        return compressedFile
    }

    void 'using dependencies'() {
        given:
        File compressed = file("/tmp/test-dir.tar.gz")
        File uncompressedFile = file("/tmp/ooo")
        File stateFile = file("/tmp/superstate.json")

        when:
        Result<Plan> result = execute stateit {
            def uncompressed = directory("id-data") {
                path = uncompressedFile.absolutePath
            }

            targz("test-dir-gzip") {
                input  = uncompressed.path
                output = compressed.absolutePath
                action = compress()
            }

            state {
                provider = fileState(stateFile.absolutePath)
            }
        }

        then:
        result.isSuccess()

        cleanup:
        deleteDirs(uncompressedFile)
        deleteFiles(stateFile)
    }
}
