package manager.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import manager.model.PassDbModel;
import manager.view.View;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogController extends Controller implements Initializable {

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

    }

}
