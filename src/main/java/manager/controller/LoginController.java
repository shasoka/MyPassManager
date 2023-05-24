package manager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import manager.model.PassDbModel;
import manager.view.ManagerView;
import manager.view.View;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static manager.view.statics.Alert.showAlert;

/**
 * Класс контроллера для окна входа в приложение.
 */
public class LoginController extends Controller {

    @FXML
    private Button enterBtn;

    @FXML
    private PasswordField pin;

    /**
     * Конструктор класса LoginController.
     *
     * @param model модель базы данных.
     * @param view  представление.
     */
    public LoginController(PassDbModel model, View view) {
        super(model, view);
    }

    /**
     * Переопределенный метод инициализации контроллера.
     *
     * @param url URL, вызвавший инициализацию.
     * @param resourceBundle Связанный с контроллером ResourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Установка обработчика события для кнопки входа
        enterBtn.setOnAction(event -> {
            try {
                logInHandler();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Установка стиля для поля ввода PIN-кода
        pin.setStyle("-fx-text-box-border: green; -fx-focus-color: lightgreen;");

        // Обработка события нажатия клавиши Enter в поле ввода PIN-кода
        pin.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    logInHandler();
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Обработчик события для кнопки входа.
     *
     * @throws SQLException при ошибке выполнения SQL-запроса.
     * @throws IOException  при ошибке ввода-вывода.
     */
    private void logInHandler() throws SQLException, IOException {
        String userInput = pin.getText();
        if (userInput.length() == 0) {
            showAlert("Empty pin! Try again.", "Log In Failed");
        } else if (model.checkPinFromDb(userInput)) {
            // Переход к главному окну при успешной аутентификации
            ManagerView managerView = new ManagerView(view.getStage());
            managerView.show();
        } else {
            showAlert("Incorrect pin! Try again.", "Log In Failed");
            pin.clear();
        }
    }

}
