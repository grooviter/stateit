package com.github.grooviter.stateit.files.targz

import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream

import java.nio.file.Files

class CompressionUtil {

    static void compressTargz(String inputFile, String outputFile)  {
        File inputDir = new File(inputFile)
        File tarFile = Files.createTempFile(new File(outputFile).name, "").toFile()
        TarArchiveOutputStream tarOutput = new TarArchiveOutputStream(tarFile.newDataOutputStream())
        inputDir.listFiles().each {
            TarArchiveEntry entry = new TarArchiveEntry(it.name)
            entry.setSize(it.size())
            tarOutput.putArchiveEntry(entry)
            tarOutput.write(it.bytes)
            tarOutput.closeArchiveEntry()
        }

        tarOutput.close()

        FileInputStream fis = new FileInputStream(tarFile);
        FileOutputStream fos = new FileOutputStream(outputFile);
        GzipCompressorOutputStream gzipOS = new GzipCompressorOutputStream(fos)
        byte[] buffer = new byte[1024];

        fis.withStream { cfis ->
            gzipOS.withStream { cout ->
                int len;
                while((len=cfis.read(buffer)) != -1){
                    cout.write(buffer, 0, len);
                }
            }
        }
    }

    static void decompressTargz(String inputFile, String outputFile) {

    }
}
