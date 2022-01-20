module com.example.pansoftware {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.pansoftware.logic to javafx.fxml;
    opens com.pansoftware.logic.ui.controller to javafx.fxml;
    exports com.pansoftware.logic;
}