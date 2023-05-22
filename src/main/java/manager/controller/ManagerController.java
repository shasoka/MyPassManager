package manager.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import manager.model.PassDbModel;
import manager.model.Password;
import manager.view.View;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {

    private View view;

    private PassDbModel model;

    @FXML
    public MenuBar menuGeneral;

    @FXML
    public Menu menuProfile;

    @FXML
    public Menu menuIE;

    @FXML
    public MenuItem subMenuE;

    @FXML
    public MenuItem subMenuI;

    @FXML
    public Menu menuHelp;

    @FXML
    public MenuItem subMenuProfile;

    @FXML
    public TableView<Password> table;

    @FXML
    public TableColumn<Password, Integer> idColumn;

    @FXML
    public TableColumn<Password, String> nameColumn;

    @FXML
    public TableColumn<Password, String> loginColumn;

    @FXML
    public TableColumn<Password, String> passwordColumn;

    @FXML
    public Button btnAdd;

    @FXML
    public Button btnEdit;

    @FXML
    public Button btnDelete;

    public ManagerController(PassDbModel model, View view) {
        this.model = model;
        this.view = view;
    }

    public TableView<Password> getTable() {
        return table;
    }

    public void setTable(TableView<Password> table) {
        this.table = table;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public PassDbModel getModel() {
        return model;
    }

    public void setModel(PassDbModel model) {
        this.model = model;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        updateView();
    }

    public void updateView() {
        List<Password> udpData = model.getPasswords();
        view.displayTable(udpData);
    }

}
