package manager.controller.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import manager.controller.ManagerController;
import manager.model.PassDbModel;
import manager.view.View;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import static manager.view.statics.Alert.showAlert;

/**
 * Контроллер для диалогового окна изменения пин-кода.
 * Расширяет класс ManagerController.
 */
public class ChangePinController extends ManagerController {

    @FXML
    public PasswordField oldPin;

    @FXML
    public PasswordField newPin;

    @FXML
    public Button submit;

    /**
     * Конструктор класса ChangePinController.
     *
     * @param model модель базы данных
     * @param view  объект представления
     */
    public ChangePinController(PassDbModel model, View view) {
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
        Stream.of(oldPin, newPin).forEach(
                textField -> textField.textProperty().addListener((observable, oldVal, newVal) -> {
                    if (newVal.length() < 4) {
                        textField.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
                    } else {
                        textField.setStyle("");
                    }
                }));

        submit.setOnAction(this::submitHandler);

    }

    /**
     * Обработчик события нажатия на кнопку подтверждения.
     *
     * @param event объект ActionEvent
     */
    private void submitHandler(ActionEvent event) {
        String oldP = oldPin.getText();
        String newP = newPin.getText();

        if (Stream.of(newP, oldP).anyMatch(field -> field.length() < 4)) {
            showAlert("Each field must be filled!", "Change error");
            return;
        }

        if (model.checkPinFromDb(oldP)) {
            model.updatePassword(0, "app", "user", newP);
        } else {
            showAlert("Incorrect old pin.", "Change error");
        }

        view.getStage().close();

    }

}
