package manager.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import manager.model.PassDbModel;
import manager.model.Password;
import manager.view.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static manager.ie.Exporter.exportPasswordsToCSV;
import static manager.ie.Importer.importPasswordsFromCSV;
import static manager.view.statics.Alert.showAlert;
import static manager.view.statics.Alert.showHelp;

/**
 * Класс ManagerController является контроллером для главного окна приложения.
 */
public class ManagerController extends Controller {

    @FXML
    private MenuItem subMenuE;

    @FXML
    private MenuItem subMenuI;

    @FXML
    private MenuItem subMenuHelp;

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

    /**
     * Конструктор класса ManagerController.
     *
     * @param model Модель для работы с базой данных.
     * @param view  Представление для отображения данных.
     */
    public ManagerController(PassDbModel model, View view) {
        super(model, view);
    }

    /**
     * Возвращает таблицу паролей.
     *
     * @return Таблица паролей.
     */
    public TableView<Password> getTable() {
        return table;
    }

    /**
     * Возвращает выбранный элемент в таблице.
     *
     * @return Выбранный элемент.
     */
    public Password getSelectedItem() {
        return table.getSelectionModel().getSelectedItem();
    }

    /**
     * Очищает выбор в таблице.
     */
    public void clearSelectedItem() {
        table.getSelectionModel().clearSelection();
    }

    /**
     * Переопределенный метод инициализации контроллера.
     *
     * @param url URL, вызвавший инициализацию.
     * @param resourceBundle Связанный с контроллером ResourceBundle.
     */
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
                addBtnHandler();
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        });

        // Хендлер для кнопки изменения
        btnEdit.setOnAction(event -> {
            try {
                editBtnHandler();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Хендлер для кнопки удаления
        btnDelete.setOnAction(this::delBtnHandler);

        // Хендлер для меню изменения пина для входа
        subMenuProfile.setOnAction(actionEvent -> {
            try {
                changePinHandler();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Хендлер для меню импорта
        subMenuI.setOnAction(this::importHandler);

        // Хендлер для меню экспорта
        subMenuE.setOnAction(this::exportHandler);

        // Хендлер для меню помощи
        subMenuHelp.setOnAction(this::helpHandler);

        view.getStage().setOnCloseRequest(event -> model.kill());

        // Отображение данных из бд на старте
        displayInitialData();
    }

    /**
     * Обработчик события нажатия на подменю About.
     *
     * @param event Событие нажатия.
     */
    private void helpHandler(Event event) {
        showHelp();
    }

    /**
     * Обработчик события нажатия на подменю Export.
     *
     * @param actionEvent Событие нажатия.
     */
    private void exportHandler(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = new Stage();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            String status = exportPasswordsToCSV(selectedDirectory, model.getPasswords());
            if (status != null)
                view.showTooltip("Passwords saved to " + status);
            else
                view.showTooltip("Something went wrong. Check logs...");
        } else {
            view.showTooltip("Aborted!");
        }
    }

    /**
     * Обработчик события нажатия на подменю Import.
     *
     * @param actionEvent Событие нажатия.
     */
    private void importHandler(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files (*.csv)", "*.csv"));
        Stage fdStage = new Stage();
        File selected = fileChooser.showOpenDialog(fdStage);

        if (selected != null) {
            try {
                List<Password> imported = importPasswordsFromCSV(selected);
                for (Password password : imported) {
                    int id = model.getTableSize();
                    if (id != -1) {
                        try {
                            model.addPassword(password.getName(), password.getLogin(), password.getPassword(), id);
                            password.setId(id);
                            view.addTableRow(password, table);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        view.showTooltip("Successfully imported " + imported.size() + " rows!");
                    } else {
                        showAlert("Error occurred while requesting the database.", "Request error");
                    }
                }
            } catch (IOException e) {
                view.showTooltip("Something went wrong. Check logs...");
                throw new RuntimeException(e);
            }
        } else {
            view.showTooltip("Aborted!");
        }

    }

    /**
     * Обработчик события нажатия на подменю ChangePin.
     */
    private void changePinHandler() throws SQLException, IOException {
        ChangePinView childView = new ChangePinView(view.getStage(), this);
        childView.show();
        view.showTooltip("Pin changed!");
    }

    /**
     * Обработчик события нажатия на кнопку редактирования.
     */
    private void editBtnHandler() throws SQLException, IOException {
        Password selected = getSelectedItem();
        if (selected != null) {
            DialogEditView childView = new DialogEditView(view.getStage(), this);
            childView.show();
        } else {
            showAlert("Select some row to edit it.", "No row selected.");
        }
    }

    /**
     * Обработчик события нажатия на кнопку добавления.
     */
    private void addBtnHandler() throws IOException, SQLException {
        DialogAddView childView = new DialogAddView(view.getStage(), this);
        childView.show();
    }

    /**
     * Метод, отсылающий сигнал view для отображения данных на старте.
     */
    private void displayInitialData() {
        List<Password> data = model.getPasswords();
        view.displayTable(data, table);
    }

    /**
     * Обработчик события нажатия на кнопку удаления.
     *
     * @param event Событие нажатия на кнопку.
     */
    private void delBtnHandler(ActionEvent event) {
        Password selected = getSelectedItem();
        if (selected != null) {
            int id = selected.getId();
            model.deletePassword(id);
            view.deleteTableRow(table, id - 1);
            table.getSelectionModel().clearSelection();
        } else {
            showAlert("Select some row to delete it.", "No row selected.");
        }
    }

}
