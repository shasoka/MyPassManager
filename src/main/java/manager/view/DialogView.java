package manager.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manager.controller.ControllerFactory;
import manager.model.PassDbModel;

import java.io.IOException;
import java.sql.SQLException;

public class DialogView extends View {

    private final ManagerView parent;

    public DialogView(Stage stage, ManagerView parent) {
        super(stage);
        this.parent = parent;
    }

    @Override
    public void show() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("addDialog.fxml"));
        loader.setControllerFactory(new ControllerFactory(PassDbModel.getInstance(), this));
        Parent dialog = loader.load();

        stage.getScene().getRoot().setDisable(true);

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(dialog));
        dialogStage.setResizable(false);

        dialogStage.showAndWait();

        stage.getScene().getRoot().setDisable(true);
    }

}
