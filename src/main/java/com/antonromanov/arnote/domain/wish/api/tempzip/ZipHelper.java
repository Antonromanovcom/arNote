package com.antonromanov.arnote.domain.wish.api.tempzip;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipHelper {
    public static NumberFormat formater = NumberFormat.getNumberInstance(new Locale("pt", "BR"));

    public static List<Path> zip(Collection<File> inputFiles, long maxSize) throws IOException {

        int count = 0;
        long currentZipSize = maxSize;
        List<Path> response = new ArrayList<>();
        ZipOutputStream zip = null;

        for (File currentFile : inputFiles) {
            long nextFileSize = currentFile.length();
            long predictedZipSize = currentZipSize + nextFileSize;
            boolean needNewFile = predictedZipSize >= maxSize;

            System.out.println("[=] ZIP current (" + formater.format(currentZipSize) + ") + next file (" +
                    formater.format(nextFileSize) + ") = predicted (" + formater.format(predictedZipSize) +
                    ") > max (" + formater.format(maxSize) + ") ? " + needNewFile);

            if (needNewFile) {
                safeClose(zip);
                Path dir = Paths.get("/Users/18502648/Documents/IdeaProjects/arNote");
                Path fileToCreatePath = dir.resolve("teste-" + ("part." + count++ + ".zip"));
                Path tmpFile = Files.createFile(fileToCreatePath);
                System.out.println("[#] Starting new file: " + tmpFile);
                zip = new ZipOutputStream(Files.newOutputStream(tmpFile));
                // zip.setLevel(Deflater.BEST_COMPRESSION);
                response.add(tmpFile);
                currentZipSize = 0;
            }

            ZipEntry zipEntry = new ZipEntry(currentFile.getName());
            System.out.println("[<] Adding to ZIP: " + currentFile.getName());
            zip.putNextEntry(zipEntry);
            FileInputStream in = new FileInputStream(currentFile);
            zip.write(in.readAllBytes());
            zip.closeEntry();
            safeClose(in);
            long compressed = zipEntry.getCompressedSize();
            System.out.println("[=] Compressed current file: " + formater.format(compressed));
            currentZipSize += zipEntry.getCompressedSize();
        }
        safeClose(zip);
        return response;
    }

    public static void safeClose(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    try {
                        System.out.println("[X] Closing: (" + closeable.getClass() + ") - " + closeable);
                        closeable.close();
                    } catch (Throwable ex) {
                        System.err.println("[!] Error on close: " + closeable);
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    public static void readZip(ZipOutputStream outStream, String targetFile) throws Exception {
        ZipInputStream inStream = new ZipInputStream(new FileInputStream(
                targetFile));
        byte[] buffer = new byte[1024];
        int len = 0;

        for (ZipEntry e; (e = inStream.getNextEntry()) != null;) {
            outStream.putNextEntry(e);
            while ((len = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, len);
            }
        }
        inStream.close();
    }



    public static void jfhvbj(String zipFile){
        String[] srcFiles = { "/Users/18502648/Documents/IdeaProjects/arNote/teste-part.0.zip",
                "/Users/18502648/Documents/IdeaProjects/arNote/teste-part.1.zip"};
        try {

            // create byte buffer
            byte[] buffer = new byte[1024];

            FileOutputStream fos = new FileOutputStream(zipFile);

            ZipOutputStream zos = new ZipOutputStream(fos);

            for (int i=0; i < srcFiles.length; i++) {

                File srcFile = new File(srcFiles[i]);

                FileInputStream fis = new FileInputStream(srcFile);

                // begin writing a new ZIP entry, positions the stream to the start of the entry data
                zos.putNextEntry(new ZipEntry(srcFile.getName()));

                int length;

                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }

                zos.closeEntry();

                // close the InputStream
                fis.close();

            }

            // close the ZipOutputStream
            zos.close();

        }
        catch (IOException ioe) {
            System.out.println("Error creating zip file: " + ioe);
        }
    }
}
