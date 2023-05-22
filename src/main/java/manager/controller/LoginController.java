package manager.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import manager.model.PassDbModel;
import manager.view.LogInView;
import manager.view.ManagerView;
import manager.view.View;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static manager.statics.Alert.showAlert;

public class LoginController implements Initializable {

    private View view;
    private PassDbModel model;

    @FXML
    private Button enterBtn;

    @FXML
    private PasswordField pin;

    public LoginController(PassDbModel model, View view) {
        this.model = model;
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void setView(LogInView view) {
        this.view = view;
    }

    public PassDbModel getModel() {
        return model;
    }

    public void setModel(PassDbModel model) {
        this.model = model;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enterBtn.setOnAction(event -> {
            try {
                handleLogin();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void handleLogin() throws SQLException, IOException {
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
