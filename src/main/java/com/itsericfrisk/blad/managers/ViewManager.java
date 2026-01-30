package com.itsericfrisk.blad.managers;

import com.itsericfrisk.blad.utils.ConvertMarkdownToHtml;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.Objects;

/**
 * Manages switching between editor and preview views.
 * Handles view visibility and icon updates.
 */
public class ViewManager {
    private final TextArea editorTextArea;
    private final WebView previewWebView;
    private final ToggleButton toggleViewButton;

    private static final String PREVIEW_ICON_PATH = "/com/itsericfrisk/blad/images/PreviewIcon.png";
    private static final String EDITOR_ICON_PATH = "/com/itsericfrisk/blad/images/EditorIcon.png";
    private static final String WEBVIEW_CSS_PATH = "/com/itsericfrisk/blad/css/webView.css";

    public ViewManager(TextArea editorTextArea, WebView previewWebView, ToggleButton toggleViewButton) {
        this.editorTextArea = editorTextArea;
        this.previewWebView = previewWebView;
        this.toggleViewButton = toggleViewButton;

        // Initial state
        showEditorView();
    }

    private void setButtonIcon(String iconPath) {
        Image image = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream(iconPath)));
        ImageView icon = new ImageView(image);
        icon.setFitHeight(14);
        icon.setFitWidth(18);
        icon.setSmooth(false);
        toggleViewButton.setGraphic(icon);
    }

    /**
     * Toggle Editor/Preview
     */
    public void toggleView() {
        boolean isEditorVisible = editorTextArea.isVisible();
        if (isEditorVisible) {
            showPreviewView();
        } else {
            showEditorView();
        }
    }

    /**
     * Shows the editor view
     */
    public void showEditorView() {
        previewWebView.setVisible(false);
        editorTextArea.setVisible(true);
        setButtonIcon(PREVIEW_ICON_PATH);
    }


    /**
     * Shows the preview view
     * Converts Markdown to HTML and loads it in the WebView.
     */
    public void showPreviewView() {
        editorTextArea.setVisible(false);
        previewWebView.setVisible(true);
        setButtonIcon(EDITOR_ICON_PATH);

        // Get the text from the editor
        String markdownText = editorTextArea.getText();
        // Convert text from Markdown to HTML
        String htmlText = ConvertMarkdownToHtml.convertText(markdownText);
        // Get engine from WebView
        WebEngine webEngine = previewWebView.getEngine();
        // StyleSheet
        webEngine.setUserStyleSheetLocation(Objects.requireNonNull(getClass().getResource(WEBVIEW_CSS_PATH)).toString());
        webEngine.loadContent(htmlText, "text/html");
    }

    /**
     * Checks if the preview view is currently visible
     */
    public boolean isPreviewVisible() {
        return previewWebView.isVisible();
    }
}
