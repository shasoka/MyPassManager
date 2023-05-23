package manager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import manager.model.PassDbModel;
import manager.model.Password;
import manager.view.ManagerView;
import manager.view.View;

import javax.xml.xpath.XPathEvaluationResult;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import static manager.encoder.Encoder.encrypt;
import static manager.statics.Alert.showAlert;

public class DialogController extends Controller implements Initializable {

    @FXML
    private Button submit;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField loginInput;

    @FXML
    private TextField passwordInput;

    public DialogController(PassDbModel model, View view) {
        super(model, view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        submit.setOnAction(this::submitHandler);
    }

    private void submitHandler(ActionEvent event) {
        String newName = nameInput.getText();
        String newLogin = loginInput.getText();
        String newPassword = passwordInput.getText();

        if (Stream.of(newName, newLogin, newPassword).anyMatch(field -> field == null || field.length() == 0)) {
            showAlert("Each field must be filled!", "Creation error");
            return;
        }

        int id = model.getTableSize();
        if (id != -1) {
            try {
                Password password = new Password(id, newName, newLogin, encrypt(newPassword));
                model.addPassword(newName, newLogin, newPassword);
                view.getParent().view.updateTable(password, view.getParent().table);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            showAlert("Error occured while requesting database.", "Request error");
        }

        view.getStage().close();

    }

}
