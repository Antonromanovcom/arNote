package com.antonromanov.arnote.domain.wish.api.tempzip;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ModZip {
    // 4MB buffer
    private static final byte[] BUFFER = new byte[4096 * 1024];

    // copy input to output stream
    public static void copy(InputStream input, OutputStream output) throws IOException {
        int bytesRead;
        while ((bytesRead = input.read(BUFFER))!= -1) {
            output.write(BUFFER, 0, bytesRead);
        }
    }



    public static void patch() throws Exception {
        // read the original zip
        ZipFile originalZip = new ZipFile("/Users/18502648/Documents/IdeaProjects/arNote/teste-part.0.zip");


        // write the modded zip with new Name
        ZipOutputStream moddedZip = new ZipOutputStream(new FileOutputStream( "/Users/18502648/Documents/IdeaProjects/arNote/comb.zip"));

        // copy contents from original zip to the modded zip
        Enumeration<? extends ZipEntry> entries = originalZip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry e = entries.nextElement();
            System.out.println("copy: " + e.getName());
            moddedZip.putNextEntry(e);
            System.out.println("putnextEntry done");
            if (!e.isDirectory()) {
                copy(originalZip.getInputStream(e), moddedZip);
            }
            moddedZip.closeEntry();
        }

        // replace the original zip-files with new ones
        ZipFile newZip = new ZipFile("/Users/18502648/Documents/IdeaProjects/arNote/teste-part.1.zip");
        Enumeration<? extends ZipEntry> newentries = newZip.entries();
        System.out.println(newentries);
        while (newentries.hasMoreElements()) {
            ZipEntry e = newentries.nextElement();
            System.out.println("append: " + e.getName());
            moddedZip.putNextEntry(e);
            System.out.println("putnextEntry done");
            if (!e.isDirectory()) {
                copy(newZip.getInputStream(e), moddedZip);
            }
            moddedZip.closeEntry();
        }

        System.out.println("appending done ");

        // close
        originalZip.close();
        newZip.close();
        moddedZip.close();
        System.out.println("all done");
    }
}
