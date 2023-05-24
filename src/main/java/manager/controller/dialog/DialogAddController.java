package manager.controller.dialog;

import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import manager.controller.ManagerController;
import manager.model.PassDbModel;
import manager.model.Password;
import manager.view.View;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import static manager.encoder.Encoder.encrypt;
import static manager.view.statics.Alert.showAlert;

/**
 * Контроллер для диалогового окна добавления пароля.
 * Расширяет класс ManagerController.
 */
public class DialogAddController extends ManagerController {

    @FXML
    private Button submit;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField loginInput;

    @FXML
    private TextField passwordInput;

    /**
     * Конструктор класса DialogAddController.
     *
     * @param model модель базы данных.
     * @param view  объект представления.
     */
    public DialogAddController(PassDbModel model, View view) {
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
        submit.setOnAction(this::submitHandler);

        passwordInput.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.length() < 4) {
                passwordInput.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
            } else {
                passwordInput.setStyle("");
            }
        });

        Stream.of(nameInput, loginInput).forEach(
                textField -> textField.textProperty().addListener((observable, oldVal, newVal) ->
                        fieldHandler(newVal, textField))
        );

        passwordInput.textProperty().addListener(this::passFieldHandler);
    }

    /**
     * Обработчик изменений в полях ввода имени, логина и пароля.
     *
     * @param newVal новое значение.
     * @param field текстовое поле.
     */
    private void fieldHandler(String newVal, TextField field) {
        if (newVal.length() == 0 || newVal.contains(",")) {
            field.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
        } else {
            field.setStyle("");
        }
    }

    /**
     * Обработчик изменений в поле ввода пароля.
     *
     * @param observable объект Observable
     * @param oldVal     старое значение
     * @param newVal     новое значение
     */
    private void passFieldHandler(Observable observable, String oldVal, String newVal) {
        if (newVal.length() < 4 || newVal.contains(",")) {
            passwordInput.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
        } else {
            passwordInput.setStyle("");
        }
    }

    /**
     * Обработчик события нажатия на кнопку подтверждения.
     *
     * @param actionEvent объект ActionEvent
     */
    private void submitHandler(ActionEvent actionEvent) {
        String newName = nameInput.getText();
        String newLogin = loginInput.getText();
        String newPassword = passwordInput.getText();

        if (Stream.of(nameInput, loginInput, passwordInput).anyMatch(field -> !Objects.equals(field.getStyle(), "")
                || field.getText().length() == 0)) {
            showAlert("Each field must be filled!\nAlso you can't use comma.", "Creation error");
            return;
        }

        int id = model.getTableSize();
        if (id != -1) {
            try {
                Password password = new Password(id, newName, newLogin, encrypt(newPassword));
                model.addPassword(newName, newLogin, password.getPassword(), id);
                view.getParent().getView().addTableRow(password, view.getParent().getTable());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            showAlert("Error occurred while requesting database.", "Request error");
        }

        view.getStage().close();
    }
}
