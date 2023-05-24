package manager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import manager.model.PassDbModel;
import manager.model.Password;
import manager.view.dialogView.DialogAddView;
import manager.view.TableCellView;
import manager.view.View;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerController extends Controller {

    @FXML
    private MenuBar menuGeneral;

    @FXML
    private Menu menuProfile;

    @FXML
    private Menu menuIE;

    @FXML
    private MenuItem subMenuE;

    @FXML
    private MenuItem subMenuI;

    @FXML
    private Menu menuHelp;

    @FXML
    private MenuItem subMenuProfile;

    @FXML
    private TableView<Password> table;

    @FXML
    private TableColumn<Password, Integer> idColumn;

    @FXML
    private TableColumn<Password, String> nameColumn;

    @FXML
    private TableColumn<Password, String> loginColumn;

    @FXML
    private TableColumn<Password, String> passwordColumn;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    public ManagerController(PassDbModel model, View view) {
        super(model, view);
    }

    public TableView<Password> getTable() {
        return table;
    }

    public void setTable(TableView<Password> table) {
        this.table = table;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordColumn.setCellFactory(column -> new TableCellView());

        btnAdd.setOnAction(event -> {
            try {
                addBtnHandler(event);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        });

        btnEdit.setOnAction(event -> {
            try {
                editBtnHandler(event);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        btnDelete.setOnAction(this::delBtnHandler);

        displayInitialData();
    }

    private void editBtnHandler(ActionEvent event) throws SQLException, IOException {
        DialogAddView childView = new DialogAddView(view.getStage(), this);
        childView.show();
    }

    private void addBtnHandler(ActionEvent event) throws IOException, SQLException {
        DialogAddView childView = new DialogAddView(view.getStage(), this);
        childView.show();
    }

    private void displayInitialData() {
        List<Password> udpData = model.getPasswords();
        view.displayTable(udpData, table);
    }

    private void delBtnHandler(ActionEvent event) {
        Password selectedItem = table.getSelectionModel().getSelectedItem();
        int id = selectedItem.getId();
        model.deletePassword(id);
        view.deleteTableRow(table, id - 1);
    }

}
