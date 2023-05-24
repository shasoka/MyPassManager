package manager;

import javafx.application.Application;
import javafx.stage.Stage;
import manager.view.LogInView;

public class Main extends Application {

    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Метод, вызываемый при запуске приложения.
     *
     * @param primaryStage основное окно приложения.
     * @throws Exception возникающие исключения при запуске.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        LogInView view = new LogInView(primaryStage);
        view.show();
    }

}
