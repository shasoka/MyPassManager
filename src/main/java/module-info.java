module manager.mypassmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    exports manager;
    exports manager.view;
    exports manager.controller;
    exports manager.model;
    exports manager.statics;
    opens manager.view to javafx.fxml;
    opens manager.controller to javafx.fxml;
    opens manager.model to javafx.fxml;
    opens manager to javafx.fxml;
    opens manager.statics to javafx.fxml;
}
