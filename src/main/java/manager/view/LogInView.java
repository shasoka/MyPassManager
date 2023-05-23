package manager.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import manager.controller.ControllerFactory;
import manager.model.PassDbModel;

import java.io.IOException;
import java.sql.SQLException;

public class LogInView extends View {

    public LogInView(Stage stage) {
        super(stage);
    }

    @Override
    public void show() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        loader.setControllerFactory(new ControllerFactory(PassDbModel.getInstance(), this));
        Parent root = loader.load();

        stage.setTitle("Log In Window");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

}
