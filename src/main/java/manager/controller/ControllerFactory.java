package manager.controller;

import javafx.util.Callback;
import manager.model.PassDbModel;
import manager.view.LogInView;
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
        }
        else if (type == ManagerController.class) {
            ManagerController controller = new ManagerController(model, view);
            view.setController(controller);
            return controller;
        }
        return null;
    }
}
