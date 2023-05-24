package manager.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import manager.controller.ControllerFactory;
import manager.model.PassDbModel;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Класс представления для окна входа в систему.
 */
public class LogInView extends View {

    /**
     * Конструктор класса LogInView.
     *
     * @param stage Экземпляр класса Stage для отображения окна.
     */
    public LogInView(Stage stage) {
        super(stage, null);
    }

    /**
     * Показывает окно входа в систему.
     *
     * @throws IOException  Возникает, если произошла ошибка ввода-вывода при загрузке FXML-файла.
     * @throws SQLException Возникает, если произошла ошибка при работе с базой данных.
     */
    @Override
    public void show() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        loader.setControllerFactory(new ControllerFactory(PassDbModel.getInstance(), this));
        Parent root = loader.load();

        stage.setTitle("Log In Window");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

}
