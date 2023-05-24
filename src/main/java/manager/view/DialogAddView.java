package manager.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manager.controller.ControllerFactory;
import manager.controller.ManagerController;
import manager.model.PassDbModel;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Класс представления для диалогового окна добавления.
 */
public class DialogAddView extends View {

    /**
     * Конструктор класса DialogAddView.
     *
     * @param stage  Экземпляр класса Stage для отображения диалогового окна.
     * @param parent Родительский контроллер, связанный с диалоговым окном.
     */
    public DialogAddView(Stage stage, ManagerController parent) {
        super(stage, parent);
    }

    /**
     * Показывает диалоговое окно добавления.
     *
     * @throws IOException  Возникает, если произошла ошибка ввода-вывода при загрузке FXML-файла.
     * @throws SQLException Возникает, если произошла ошибка при работе с базой данных.
     */
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
