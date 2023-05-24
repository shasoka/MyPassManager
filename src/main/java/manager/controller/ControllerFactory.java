package manager.controller;

import javafx.util.Callback;
import manager.controller.dialog.ChangePinController;
import manager.controller.dialog.DialogAddController;
import manager.controller.dialog.DialogEditController;
import manager.model.PassDbModel;
import manager.view.View;

public class ControllerFactory implements Callback<Class<?>, Object> {
    private final PassDbModel model;
    private final View view;

    public ControllerFactory(PassDbModel model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public Object call(Class<?> type) {
        if (type == LoginController.class) {
            return new LoginController(model, view);
        } else if (type == ManagerController.class) {
            return new ManagerController(model, view);
        } else if (type == DialogAddController.class) {
            return new DialogAddController(model, view);
        } else if (type == DialogEditController.class) {
            return new DialogEditController(model, view);
        } else if (type == ChangePinController.class) {
            return new ChangePinController(model, view);
        }
        return null;
    }

}
