package com.itsericfrisk.blad.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ImportMarkdown {

    private ImportMarkdown() {
    }

    public static String importMd(String filePath) {
        Path path = Path.of(filePath);
        try {
            List<String> lines = Files.readAllLines(path);
            return String.join("\n", lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
