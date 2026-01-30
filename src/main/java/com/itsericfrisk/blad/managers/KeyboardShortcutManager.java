package com.itsericfrisk.blad.managers;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class KeyboardShortcutManager {

    public static void setupShortcuts(Scene scene, Runnable onSearch, Runnable onShowEditor, Runnable onShowPreview) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.isShortcutDown()) {
                switch (e.getCode()) {
                    case F:
                        onSearch.run();
                        e.consume();
                        break;
                    case E:
                        onShowEditor.run();
                        e.consume();
                        break;
                    case P:
                        onShowPreview.run();
                        e.consume();
                        break;
                }
            }
        });
    }
}
