package com.itsericfrisk.blad;

import com.itsericfrisk.blad.controllers.SearchController;
import com.itsericfrisk.blad.managers.FileManager;
import com.itsericfrisk.blad.managers.KeyboardShortcutManager;
import com.itsericfrisk.blad.managers.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

public class MainController {
    @FXML
    public BorderPane root;
    @FXML
    public ToggleButton toggleViewButton;
    @FXML
    public TextArea editorTextArea;
    @FXML
    public WebView previewWebView;
    @FXML
    public MenuItem saveFile;
    @FXML
    public MenuItem saveFileAs;
    @FXML
    public HBox searchBar;
    @FXML
    public TextField searchField;

    private ViewManager viewManager;
    private FileManager fileManager;
    private SearchController searchController;

    /**
     * Initialize the application
     */
    @FXML
    public void initialize() {
        viewManager = new ViewManager(editorTextArea, previewWebView, toggleViewButton);
        fileManager = new FileManager(editorTextArea);
        searchController = new SearchController(searchBar, searchField, editorTextArea);

        // Setup menu shortcuts
        saveFile.setAccelerator(KeyCombination.keyCombination("Shortcut+S"));
        saveFileAs.setAccelerator(KeyCombination.keyCombination("Shortcut+Shift+S"));

        root.sceneProperty().addListener((observableValue, oldScene, newScene) -> {
            if (newScene != null) {
                KeyboardShortcutManager.setupShortcuts(
                        newScene,
                        this::handleSearchShortcut,
                        viewManager::showEditorView,
                        this::handlePreviewShortcut
                );
            }
        });

        // Load last file
        fileManager.loadLastFile();
    }

    private void handleSearchShortcut() {
        if (viewManager.isPreviewVisible()) {
            viewManager.showEditorView();
        }
        searchController.toggle();
    }

    private void handlePreviewShortcut() {
        viewManager.showPreviewView();
        searchController.close();
    }

    @FXML
    public void toggleView() {
        viewManager.toggleView();
    }

    @FXML
    public void openFile() {
        fileManager.openFile();
        viewManager.showEditorView();
    }

    @FXML
    public void createNewFile() {
        fileManager.createNewFile();
        viewManager.showEditorView();
    }

    @FXML
    public void save() {
        fileManager.save(editorTextArea.getScene().getWindow());
    }

    @FXML
    public void saveAs() {
        fileManager.saveAs(editorTextArea.getScene().getWindow());
    }

    @FXML
    public void findPrevious() {
        searchController.findPrevious();
    }

    @FXML
    public void findNext() {
        searchController.findNext();
    }

    @FXML
    public void closeSearch() {
        searchController.close();
    }
}
