package com.github.grooviter.stateit.test

import com.github.grooviter.stateit.core.Plan
import com.github.grooviter.stateit.core.PlanExecutor
import com.github.grooviter.stateit.core.Result
import com.github.grooviter.stateit.files.targz.CompressionUtil
import spock.lang.Specification

import java.nio.file.Files

class BaseSpecification extends Specification {

    Result<Plan> executePlan(Plan plan) {
        return new PlanExecutor(plan).execute()
    }

    void deleteDirs(File... directories) {
        directories*.deleteDir()
    }

    void deleteFiles(File... files) {
        files*.delete()
    }

    File file(String filePath) {
        return new File(filePath)
    }

    File mkdir(String filePath) {
        File directory = file(filePath)
        directory.mkdirs()
        return directory
    }

    File createFileWithText(String path, String text) {
        File textFile = file(path)
        textFile.text = text
        return textFile
    }

    void createTextFilesInDir(File parentDir, Map<String, String> fileAndContent) {
        fileAndContent.each { fileName, content ->
            new File(parentDir, "${fileName}.txt").text = content
        }
    }

    void createTarGzFile(String outputPath) {
        File tempDir = Files.createTempDirectory("stateit").toFile()
        createTextFilesInDir(tempDir, [hello: "hi!", bye: "bye!"])
        CompressionUtil.compressTargz(tempDir.absolutePath, outputPath)
    }
}
