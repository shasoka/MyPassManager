package manager.controller.dialog;

import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import static manager.statics.Alert.showAlert;
import static manager.view.TableCellView.HIDDEN_PASS;

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

    public DialogEditController(PassDbModel model, View view) {
        super(model, view);
    }

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

        tmpShow.setOnMouseReleased(event -> {
            passwordInput.setText(HIDDEN_PASS);
        });

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

        nameInput.textProperty().addListener((observable, oldVal, newVal) -> {
            fieldHandler(observable, oldVal, newVal, nameInput);
        });
        loginInput.textProperty().addListener((observable, oldVal, newVal) -> {
            fieldHandler(observable, oldVal, newVal, loginInput);
        });
    }

    private void fieldHandler(Observable observable, String oldVal, String newVal, TextField field) {
        if (newVal.length() == 0) {
            field.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
        } else {
            field.setStyle("");
        }
    }

    private void submitHandler(ActionEvent actionEvent) {
        String newName = nameInput.getText();
        String newLogin = loginInput.getText();
        String newPassword;
        if (tmpShow.isDisabled()) {
            newPassword = passwordInput.getText();
        } else {
            newPassword = view.getParent().getSelectedItem().getPassword();
        }

        if (Stream.of(newName, newLogin).anyMatch(field -> field.length() == 0) || (newPassword.length() < 4)) {
            showAlert("Each field must be filled!", "Edition error");
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
