package com.github.grooviter.stateit.files.targz

import groovy.util.logging.Slf4j
import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream
import org.apache.commons.compress.archivers.tar.TarFile
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Slf4j
class CompressionUtil {
    static void compressTargz(String inputFile, String outputFilePath, boolean overwrite)  {
        log.info "compressing file"
        File outputFile = new File(outputFilePath)

        if (outputFile.exists() && !overwrite) {
            log.info "output file exists and can't overwrite skipping"
            return
        }

        compressFile(createTarFile(new File(inputFile)), outputFile)
    }

    private static File createTarFile(File sourceDir) {
        File tarFile = Files.createTempFile("statit", "").toFile()
        TarArchiveOutputStream tarOutput = new TarArchiveOutputStream(tarFile.newDataOutputStream())

        sourceDir.listFiles().each {
            TarArchiveEntry entry = new TarArchiveEntry(it.name)
            entry.setSize(it.size())
            tarOutput.putArchiveEntry(entry)
            tarOutput.write(it.bytes)
            tarOutput.closeArchiveEntry()
        }

        tarOutput.close()
        return tarFile
    }

    private static void compressFile(File tarFile, File outputFilePath) {
        FileInputStream fis = new FileInputStream(tarFile);
        FileOutputStream fos = new FileOutputStream(outputFilePath);
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

    static void decompressTargz(String inputFile, String outputFile, boolean overwrite) {
        log.info "decompressing file"
        File destinationDir = new File(outputFile)

        if (destinationDir.exists() && !overwrite) {
            log.info "output file exists and can't overwrite skipping"
        }

        expandTarFile(uncompressFile(inputFile), destinationDir)
    }

    private static TarFile uncompressFile(String inputFile) {
        InputStream fin = Files.newInputStream(Paths.get(inputFile));
        BufferedInputStream bis = new BufferedInputStream(fin);
        Path temporalTarFile = Files.createTempFile("stateit", "")
        OutputStream out = Files.newOutputStream(temporalTarFile);
        GzipCompressorInputStream gzIn = new GzipCompressorInputStream(bis);

        final byte[] buffer = new byte[1024];
        int n = 0;
        while (-1 != (n = gzIn.read(buffer))) {
            out.write(buffer, 0, n);
        }
        out.close();
        gzIn.close();

        TarFile tarFile = new TarFile(temporalTarFile.toFile())
        return tarFile
    }

    private static void expandTarFile(TarFile tarFile, File destinationDir) {
        destinationDir.mkdirs()
        final byte[] buffer = new byte[1024];
        tarFile.entries.each { TarArchiveEntry entry ->
            FileOutputStream fileOutputStream = new FileOutputStream(new File(destinationDir, entry.name))

            tarFile.getInputStream(entry).withCloseable {cfis ->
                fileOutputStream.withCloseable { cout ->
                    int len;
                    while((len=cfis.read(buffer)) != -1){
                        cout.write(buffer, 0, len);
                    }
                }
            }
        }
    }
}
