package com.itsericfrisk.blad.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Handles search functionality for the text editor.
 * Manages search UI visibility and performs text searches.
 */
public class SearchController {
    private final HBox searchBar;
    private final TextField searchField;
    private final TextArea editorTextArea;

    public SearchController(HBox searchBar, TextField searchField, TextArea editorTextArea) {
        this.searchBar = searchBar;
        this.searchField = searchField;
        this.editorTextArea = editorTextArea;

        searchBar.managedProperty().bind(searchBar.visibleProperty());
    }

    /**
     * Opens the search function and changes the view to the editor
     */
    public void open() {
        searchBar.setVisible(true);
        searchBar.requestFocus();
    }

    /**
     * Closes the search function
     */
    public void close() {
        searchBar.setVisible(false);
    }

    /**
     * Toggles search bar visibility
     */
    public void toggle() {
        if (searchBar.isVisible()) {
            searchBar.setVisible(false);
        } else {
            open();
        }
    }

    /**
     * Finds and selects the next occurrence of the search text in the editor.
     * Performs a case-insensitive search forwards from the current selection.
     * If no match is found after the current position, wraps around to search from the beginning.
     * Does nothing if the search field or editor is empty, or if no matches exist.
     */
    @FXML
    public void findNext() {
        String searchedText = searchField.getText().toLowerCase();

        if (editorTextArea.getText().isEmpty() || searchField.getText().isEmpty()) {
            return;
        }

        IndexRange selection = editorTextArea.getSelection();
        int startPos = selection != null ? selection.getEnd() : 0;
        int textPos = editorTextArea.getText().toLowerCase().indexOf(searchedText, startPos);
        if (textPos == -1) {
            textPos = editorTextArea.getText().toLowerCase().indexOf(searchedText, 0);

            if (textPos == -1) {
                return;
            }
        }

        editorTextArea.selectRange(textPos, textPos + searchedText.length());
    }

    /**
     * Finds and selects the previous occurrence of the search text in the editor.
     * Performs a case-insensitive search backwards from the current selection.
     * If no match is found before the current position, wraps around to search from the end.
     * Does nothing if the search field or editor is empty, or if no matches exist.
     */
    @FXML
    public void findPrevious() {
        String searchedText = searchField.getText().toLowerCase();

        if (editorTextArea.getText().isEmpty() || searchField.getText().isEmpty()) {
            return;
        }

        IndexRange selection = editorTextArea.getSelection();
        int endPos = selection == null ? editorTextArea.getLength() : selection.getStart();
        int textPos = editorTextArea.getText().toLowerCase().substring(0, endPos).lastIndexOf(searchedText);
        if (textPos == -1) {
            textPos = editorTextArea.getText().toLowerCase().lastIndexOf(searchedText);

            if (textPos == -1) {
                return;
            }
        }

        editorTextArea.selectRange(textPos, textPos + searchedText.length());
    }
}
