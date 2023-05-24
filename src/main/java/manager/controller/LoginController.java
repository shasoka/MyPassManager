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

import static manager.statics.Alert.showAlert;

public class LoginController extends Controller {

    @FXML
    private Button enterBtn;

    @FXML
    private PasswordField pin;

    public LoginController(PassDbModel model, View view) {
        super(model, view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enterBtn.setOnAction(event -> {
            try {
                logInHandler();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });

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

    private void logInHandler() throws SQLException, IOException {
        String userInput = pin.getText();
        if (userInput.length() == 0) {
            showAlert("Empty pin! Try again.", "Log In Failed");
        }
        else if (model.checkPinFromDb(userInput)) {
            ManagerView managerView = new ManagerView(view.getStage());
            managerView.show();
        } else {
            showAlert("Incorrect pin! Try again.", "Log In Failed");
        }
    }

}
