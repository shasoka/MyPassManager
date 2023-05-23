package manager.controller;

import manager.model.PassDbModel;
import manager.view.LogInView;
import manager.view.View;

public abstract class Controller {

    protected View view;

    protected PassDbModel model;

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

}
