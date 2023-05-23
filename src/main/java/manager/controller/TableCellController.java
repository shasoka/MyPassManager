package manager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import manager.encoder.Encoder;
import manager.view.TableCellView;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class TableCellController implements Initializable {

    private TableCellView view;

    private Button showPasswordButton = new Button("Show");

    private boolean hidden = true;

    public TableCellView getView() {
        return view;
    }

    public Button getShowPasswordButton() {
        return showPasswordButton;
    }

    public void setShowPasswordButton(Button showPasswordButton) {
        this.showPasswordButton = showPasswordButton;
    }

    public void setView(TableCellView view) {
        this.view = view;
    }

    public TableCellController(TableCellView view) {
        this.view = view;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        view.setOnMouseClicked(this::mouseClickHandler);
        showPasswordButton.setOnAction(this::showBtnHandler);
    }

    private void mouseClickHandler(MouseEvent event) {
        if (!hidden) {
            String password = view.getItem();
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(password);
            clipboard.setContent(content);
            view.showToolptip(event);
        }
    }

    private void showBtnHandler(ActionEvent event) {
        if (hidden) {
            try {
                view.setText(Encoder.decrypt(view.getItem()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            showPasswordButton.setText("Hide");
        } else {
            view.setText(view.HIDDEN_PASS);
            showPasswordButton.setText("Show");
        }
        hidden = !hidden;
    }

}
