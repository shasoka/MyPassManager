package manager.view;

import javafx.scene.control.TableView;
import javafx.stage.Stage;
import manager.model.Password;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public abstract class View {

    protected Stage stage;

    public View(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void show() throws IOException, SQLException {};

    public void displayTable(List<Password> udpData, TableView<Password> table) {};

}
