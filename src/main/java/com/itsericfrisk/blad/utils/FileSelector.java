package com.itsericfrisk.blad.utils;

import javafx.stage.FileChooser;

import java.io.File;

public class FileSelector {

    private FileSelector() {
    }

    /**
     * Handles the "Open File" action by displaying a file chooser for Markdown files.
     */
    public static File selectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open files");
        // Specify which files to accept
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.md"));
        // Open window and select file
        return fileChooser.showOpenDialog(null);
    }
}
