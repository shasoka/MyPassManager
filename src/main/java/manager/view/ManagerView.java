package manager.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
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
        super(stage, null);
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

    public void addTableRow(Password password, TableView<Password> table) {
        table.getItems().add(password);
        table.refresh();
    }

    public void deleteTableRow(TableView<Password> table, int id) {
        ObservableList<Password> data = table.getItems();
        data.remove(id);
        for (int i = id; i < data.size(); i++) {
            Password password = data.get(i);
            password.setId(password.getId() - 1);
        }
        table.refresh();
    }

}
