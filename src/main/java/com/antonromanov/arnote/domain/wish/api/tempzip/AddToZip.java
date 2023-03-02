package com.antonromanov.arnote.domain.wish.api.tempzip;

import java.net.URI;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class AddToZip {


    public static void copy() {

        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        URI uri = URI.create("jar:file:/Users/18502648/Documents/IdeaProjects/arNote/comb.zip");

        try {
            FileSystem zipfs = FileSystems.newFileSystem(uri, env);
            Path externalTxtFile = Paths.get("/Users/18502648/Documents/IdeaProjects/arNote/copy-front-end.sh");
            Path pathInZipfile = zipfs.getPath(externalTxtFile.getFileName().toString());
            Files.copy(externalTxtFile, pathInZipfile,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}
