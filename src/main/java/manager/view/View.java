package manager.view;

import javafx.scene.control.TableView;
import javafx.stage.Stage;
import manager.controller.ManagerController;
import manager.model.Password;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public abstract class View {

    protected Stage stage;

    protected ManagerController parent;

    public ManagerController getParent() {
        return parent;
    }

    public void setParent(ManagerController parent) {
        this.parent = parent;
    }

    public View(Stage stage, ManagerController parent) {
        this.stage = stage;
        this.parent = parent;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void show() throws IOException, SQLException{};

    public void displayTable(List<Password> udpData, TableView<Password> table) {};

    public void addTableRow(Password password, TableView<Password> table) {}

    public void deleteTableRow(TableView<Password> table, int id) {}

}
