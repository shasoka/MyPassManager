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
    exports manager.controller.dialog;
    exports manager.model;
    exports manager.view.statics;
    exports manager.ie;
    opens manager to javafx.fxml;
    opens manager.view to javafx.fxml;
    opens manager.controller to javafx.fxml;
    opens manager.controller.dialog to javafx.fxml;
    opens manager.model to javafx.fxml;
    opens manager.view.statics to javafx.fxml;
    opens manager.ie to javafx.fxml;
}
