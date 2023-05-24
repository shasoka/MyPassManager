package manager.controller;

import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import manager.model.PassDbModel;
import manager.model.Password;
import manager.view.View;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import static manager.encoder.Encoder.encrypt;
import static manager.statics.Alert.showAlert;

public class DialogAddController extends Controller implements Initializable {

    @FXML
    private Button submit;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField loginInput;

    @FXML
    private TextField passwordInput;

    public DialogAddController(PassDbModel model, View view) {
        super(model, view);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        submit.setOnAction(this::submitHandler);

        passwordInput.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.length() < 4) {
                passwordInput.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
            } else {
                passwordInput.setStyle("");
            }
        });

        nameInput.textProperty().addListener((observable, oldVal, newVal) -> {
            fieldHandler(observable, oldVal, newVal, nameInput);
        });
        loginInput.textProperty().addListener((observable, oldVal, newVal) -> {
            fieldHandler(observable, oldVal, newVal, loginInput);
        });
    }

    protected void fieldHandler(Observable observable, String oldVal, String newVal, TextField field) {
        if (newVal.length() == 0) {
            field.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
        } else {
            field.setStyle("");
        }
    }

    protected void submitHandler(ActionEvent actionEvent) {
        String newName = nameInput.getText();
        String newLogin = loginInput.getText();
        String newPassword = passwordInput.getText();

        if (Stream.of(newName, newLogin).anyMatch(field -> field.length() == 0) || (newPassword.length() < 4)) {
            showAlert("Each field must be filled!", "Creation error");
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
            showAlert("Error occured while requesting database.", "Request error");
        }

        view.getStage().close();
    }

}
