module com.itsericfrisk.blad {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires flexmark.util.data;
    requires flexmark;
    requires flexmark.util.ast;
    requires flexmark.ext.tables;
    requires flexmark.ext.gfm.strikethrough;
    requires flexmark.util.misc;
    requires java.prefs;

    opens com.itsericfrisk.blad to javafx.fxml;
    exports com.itsericfrisk.blad;
}