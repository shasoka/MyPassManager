package manager;

import javafx.application.Application;
import javafx.stage.Stage;
import manager.view.LogInView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LogInView view = new LogInView(primaryStage);
        view.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
