package manager.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import manager.controller.ControllerFactory;
import manager.model.PassDbModel;
import manager.model.Password;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ManagerView extends View {

    public ManagerView(Stage stage) {
        super(stage);
    }

    @Override
    public void show() throws IOException, SQLException {
        // Закрытие прошлого окна, полученного при создании объекта класса ManagerView
        stage.close();
        // Создание нового Stage
        stage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("manager.fxml"));
        loader.setControllerFactory(new ControllerFactory(PassDbModel.getInstance(), this));
        Parent root = loader.load();

        stage.setTitle("Password Manager");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void displayTable(List<Password> updData, TableView<Password> table) {
        table.setItems(FXCollections.observableArrayList(updData));
        table.refresh();
    }

}
