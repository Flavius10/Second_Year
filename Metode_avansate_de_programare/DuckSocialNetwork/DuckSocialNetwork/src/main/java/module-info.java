module org.example {
    requires java.sql;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires org.postgresql.jdbc;
    requires java.datatransfer;
    requires java.desktop;
    requires javafx.base;

    opens org.example to javafx.fxml;

    opens org.example.domain to javafx.base;

    opens org.example.domain.ducks to javafx.base;

    exports org.example;
    exports org.example.controller;
    opens org.example.controller to javafx.fxml;
}