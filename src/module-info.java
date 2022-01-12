module PanSoftware {
    requires javafx.controls;
    requires java.sql;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.desktop;
    requires jakarta.servlet;

    opens logic to javafx.graphics, javafx.fxml;
    opens logic.ui.controller to javafx.fxml;
}
