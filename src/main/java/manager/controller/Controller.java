package manager.controller;

import javafx.fxml.Initializable;
import manager.model.PassDbModel;
import manager.view.LogInView;
import manager.view.View;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class Controller implements Initializable {

    protected View view;

    protected PassDbModel model;

    protected Controller() {}

    public Controller(PassDbModel model, View view) {
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
    public void initialize(URL url, ResourceBundle resourceBundle) {}

}
