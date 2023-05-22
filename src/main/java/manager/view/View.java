package manager.view;

import javafx.stage.Stage;
import manager.controller.LoginController;
import manager.controller.ManagerController;
import manager.model.Password;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface View {

    default void displayTable(List<Password> updData) {}

    default void setController(ManagerController controller) {}

    void show() throws IOException, SQLException;

    void setStage(Stage stage);

    Stage getStage();
}
