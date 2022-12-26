package com.github.grooviter.stateit.files.targz

import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.core.Result
import groovy.transform.builder.Builder
import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream

import java.nio.file.Files

@Builder
class TargzResource extends Resource {
    String id
    String input
    String output
    TargzProps.Action type

    @Override
    Result<Resource> create() {
        if (!input) {
            return TargzErrors.INPUT_MISSING.toResult(this) as Result<Resource>
        }

        if (!output) {
            return TargzErrors.OUTPUT_MISSING.toResult(this) as Result<Resource>
        }

        try {
            if (TargzProps.Action.COMPRESS == type) {
                compressTargz(this.input, this.output)
            }

            if (TargzProps.Action.EXTRACT == type) {
                decompressTargz(this.input, this.output)
            }

        } catch (IOException ignored) {
            return TargzErrors.GZIP_ERROR.toResult(this) as Result<Resource>
        }

        return Result.of(this) as Result<Resource>
    }

    private static void decompressTargz(String inputFile, String outputFile) {

    }

    private static void compressTargz(String inputFile, String outputFile)  {
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

    @Override
    Result<Resource> destroy() {
        return null
    }

    @Override
    Result<Resource> validate() {
        return null
    }
}
