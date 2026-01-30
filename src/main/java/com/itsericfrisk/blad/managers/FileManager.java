package com.itsericfrisk.blad.managers;

import com.itsericfrisk.blad.io.ImportMarkdown;
import com.itsericfrisk.blad.utils.AppPreferences;
import com.itsericfrisk.blad.utils.FileSelector;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Manages file operations including opening, saving, and creating files.
 * Tracks the current file path and handles user preferences.
 */
public class FileManager {
    private final TextArea editorTextArea;
    private String currentFilePath;

    public FileManager(TextArea editorTextArea) {
        this.editorTextArea = editorTextArea;
    }

    /**
     * Loads the last opened file from preferences
     */
    public void loadLastFile() {
        String lastOpenedFile = AppPreferences.getLastFile();

        if (lastOpenedFile != null) {
            File file = new File(lastOpenedFile);
            if (file.exists()) {
                currentFilePath = lastOpenedFile;
                try {
                    String fileText = ImportMarkdown.importMd(lastOpenedFile);
                    editorTextArea.setText(fileText);
                } catch (Exception e) {
                    AppPreferences.saveLastFile(null);
                }
            }
        }
    }

    /**
     * Opens a file chooser and loads the selected Markdown file
     */
    public void openFile() {
        File selectedFile = FileSelector.selectFile();

        if (selectedFile != null) {
            currentFilePath = selectedFile.getAbsolutePath();
            AppPreferences.saveLastFile(currentFilePath);

            // Import text
            String fileText = ImportMarkdown.importMd(selectedFile.toString());

            // Add text to the textArea
            editorTextArea.setText(fileText);
        }
    }

    /**
     * Creates a new empty file by clearing the editor
     */
    public void createNewFile() {
        editorTextArea.clear();
        currentFilePath = null;
    }

    /**
     * If it's the first time saving the file, open a file chooser to select location.
     * If the file already is saved on the computer, save it to that directory.
     */
    public void save(Window window) {
        if (currentFilePath != null) {
            // Save to existing file
            try {
                Files.writeString(Path.of(currentFilePath), editorTextArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // No file open, show Save As dialog
            saveAs(window);
        }
    }

    /**
     * Opens a file chooser where the user can select where to save the file.
     */
    public void saveAs(Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Markdown Files", "*.md")
        );

        // If there's a current file, use its directory
        if (currentFilePath != null) {
            File currentFile = new File(currentFilePath);
            fileChooser.setInitialDirectory(currentFile.getParentFile());
            fileChooser.setInitialFileName(currentFile.getName());
        } else {
            fileChooser.setInitialFileName("untitled.md");
        }

        File file = fileChooser.showSaveDialog(window);

        if (file != null) {
            try {
                Files.writeString(file.toPath(), editorTextArea.getText());
                currentFilePath = file.getAbsolutePath();
                AppPreferences.saveLastFile(currentFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }
}
