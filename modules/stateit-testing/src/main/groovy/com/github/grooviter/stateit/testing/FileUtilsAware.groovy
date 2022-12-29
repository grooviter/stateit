package com.github.grooviter.stateit.testing

import java.nio.file.Files
import java.nio.file.StandardCopyOption

trait FileUtilsAware {
    File randomFileWithContent(String content) {
        File randomFile = Files.createTempFile("stateit.vars.", "").toFile()
        randomFile.text = content
        return randomFile
    }

    File randomFileRef() {
        String randomString = (0..6).collect { new Random().nextInt(9) }.join("")
        return new File("/tmp", randomString)
    }

    File randomFileRefWithSuffix(String suffix) {
        String randomString = (0..6).collect { new Random().nextInt(9) }.join("")
        return new File("/tmp", "${randomString}.${suffix}")
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

    void mvFileToDir(File file, File directory) {
        Files.copy(file.toPath(), new File(directory, file.name).toPath(), StandardCopyOption.REPLACE_EXISTING)
    }

    File mkdir(String filePath) {
        File directory = file(filePath)
        directory.mkdirs()
        return directory
    }

    File mkdirRandom() {
        File randomDirRef = randomFileRef()
        randomDirRef.mkdir()
        return randomDirRef
    }
}