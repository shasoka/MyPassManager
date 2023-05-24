package manager.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import manager.model.PassDbModel;
import manager.model.Password;
import manager.view.*;

import static manager.statics.Alert.showAlert;

import java.io.File;
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

    public Password getSelectedItem() {
        return table.getSelectionModel().getSelectedItem();
    }

    public void clearSelectedItem() {
        table.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Инициализация ячеек таблицы
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordColumn.setCellFactory(column -> new TableCellView());

        // Хендлер для кнопки добавления
        btnAdd.setOnAction(event -> {
            try {
                addBtnHandler(event);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        });

        // Хендлер для кнопки изменения
        btnEdit.setOnAction(event -> {
            try {
                editBtnHandler(event);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Хендлер для кнопки удаления
        btnDelete.setOnAction(this::delBtnHandler);

        // Хендлер для меню изменения пина для входа
        subMenuProfile.setOnAction(actionEvent -> {
            try {
                changePinHandler(actionEvent);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        subMenuI.setOnAction(this::importHandler);
        subMenuE.setOnAction(this::exportHandler);

        // Отображение данных из бд на старте
        displayInitialData();
    }

    private void exportHandler(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = new Stage();
        File selectedDirectory = directoryChooser.showDialog(stage);
        System.out.println(selectedDirectory);
    }

    private void importHandler(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files (*.csv)", "*.csv"));
        Stage fdStage = new Stage();
        File selected = fileChooser.showOpenDialog(fdStage);
    }

    private void changePinHandler(ActionEvent actionEvent) throws SQLException, IOException {
        ChangePinView childView = new ChangePinView(view.getStage(), this);
        childView.show();
        showTooltip("Pin changed!");
    }

    private void editBtnHandler(ActionEvent event) throws SQLException, IOException {
        Password selected = getSelectedItem();
        if (selected != null) {
            DialogEditView childView = new DialogEditView(view.getStage(), this);
            childView.show();
        }
        else {
            showAlert("Select some row to edit it.", "No row seleceted.");
        }
    }

    private void addBtnHandler(ActionEvent event) throws IOException, SQLException {
        DialogAddView childView = new DialogAddView(view.getStage(), this);
        childView.show();
    }

    private void displayInitialData() {
        List<Password> data = model.getPasswords();
        view.displayTable(data, table);
    }

    private void delBtnHandler(ActionEvent event) {
        Password selected = getSelectedItem();
        if (selected != null) {
            int id = selected.getId();
            model.deletePassword(id);
            view.deleteTableRow(table, id - 1);
            table.getSelectionModel().clearSelection();
        } else {
            showAlert("Select some row to delete it.", "No row seleceted.");
        }
    }

    private void showTooltip(String lable) {
        Tooltip tooltip = new Tooltip(lable);
        tooltip.show(view.getStage(), view.getStage().getX() + view.getStage().getWidth() / 2 - 20,
                view.getStage().getY() + view.getStage().getHeight() / 2 - 20);

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> {
            tooltip.hide();
        });
        delay.play();
    }

}
