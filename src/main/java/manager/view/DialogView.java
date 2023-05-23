package manager.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manager.controller.ControllerFactory;
import manager.controller.ManagerController;
import manager.model.PassDbModel;
import manager.model.Password;

import java.io.IOException;
import java.sql.SQLException;

public class DialogView extends View {

    public DialogView(Stage stage, ManagerController parent) {
        super(stage, parent);
    }

    @Override
    public void show() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("addDialog.fxml"));
        loader.setControllerFactory(new ControllerFactory(PassDbModel.getInstance(), this));
        Parent dialog = loader.load();

        Stage parentStage = stage;
        parentStage.getScene().getRoot().setDisable(true);

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(dialog));
        stage.setResizable(false);
        stage.showAndWait();

        parentStage.getScene().getRoot().setDisable(false);
    }

}
