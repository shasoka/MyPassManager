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

import static manager.encoder.Encoder.decrypt;
import static manager.encoder.Encoder.encrypt;
import static manager.view.statics.Alert.showAlert;
import static manager.view.TableCellView.HIDDEN_PASS;

/**
 * Контроллер для диалогового окна редактирования пароля.
 * Расширяет класс ManagerController.
 */
public class DialogEditController extends ManagerController {

    @FXML
    private Button submit;

    @FXML
    private Button tmpShow;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField loginInput;

    @FXML
    private TextField passwordInput;

    /**
     * Конструктор класса DialogEditController.
     *
     * @param model модель базы данных
     * @param view  объект представления
     */
    public DialogEditController(PassDbModel model, View view) {
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
        Password selected = view.getParent().getSelectedItem();
        nameInput.setText(selected.getName());
        loginInput.setText(selected.getLogin());
        passwordInput.setText(HIDDEN_PASS);

        tmpShow.setOnMousePressed(event -> {
            try {
                passwordInput.setText(decrypt(selected.getPassword()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        tmpShow.setOnMouseReleased(event -> passwordInput.setText(HIDDEN_PASS));

        submit.setOnAction(this::submitHandler);

        passwordInput.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.length() < 4) {
                passwordInput.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
            } else {
                passwordInput.setStyle("");
            }
        });

        passwordInput.setOnKeyPressed(event -> {
            passwordInput.clear();
            tmpShow.setDisable(true);
            passwordInput.setOnKeyPressed(null);
        });

        Stream.of(nameInput, loginInput).forEach(
                textField -> textField.textProperty().addListener((observable, oldVal, newVal) ->
                        fieldHandler(newVal, textField))
        );

        passwordInput.textProperty().addListener(this::passFieldHandler);
    }

    /**
     * Обработчик изменений в полях ввода имени и логина.
     *
     * @param newVal     новое значение
     * @param field      текстовое поле
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
        String newPassword;
        if (tmpShow.isDisabled()) {
            newPassword = passwordInput.getText();
        } else {
            newPassword = view.getParent().getSelectedItem().getPassword();
        }

        if (Stream.of(nameInput, loginInput, passwordInput).anyMatch(field -> !Objects.equals(field.getStyle(), ""))) {
            showAlert("Each field must be filled!\nAlso you can't use comma.", "Creation error");
            return;
        }

        int id = view.getParent().getSelectedItem().getId();
        try {
            view.getParent().getTable().getItems().get(id - 1).setName(newName);
            view.getParent().getTable().getItems().get(id - 1).setLogin(newLogin);
            view.getParent().getTable().getItems().get(id - 1).setPassword(encrypt(newPassword));
            model.updatePassword(id, newName, newLogin, newPassword);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        view.getParent().clearSelectedItem();
        view.getParent().getTable().refresh();
        view.getStage().close();
    }

}
