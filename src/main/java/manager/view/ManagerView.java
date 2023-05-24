package manager.view;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.util.Duration;
import manager.controller.ControllerFactory;
import manager.model.PassDbModel;
import manager.model.Password;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Пользовательский класс, представляющий главное окно приложения Password Manager.
 */
public class ManagerView extends View {

    /**
     * Конструктор класса ManagerView.
     *
     * @param stage Главный Stage приложения.
     */
    public ManagerView(Stage stage) {
        super(stage, null);
    }

    /**
     * Показывает главное окно приложения Password Manager.
     *
     * @throws IOException  Исключение ввода-вывода.
     * @throws SQLException Исключение SQL.
     */
    @Override
    public void show() throws IOException, SQLException {
        // Закрытие прошлого окна, полученного при создании объекта класса ManagerView
        stage.close();
        // Создание нового Stage
        stage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("manager.fxml"));
        loader.setControllerFactory(new ControllerFactory(PassDbModel.getInstance(), this));
        Parent root = loader.load();

        stage.setTitle("Password Manager");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Отображает данные в таблице паролей.
     *
     * @param updData Обновленные данные паролей.
     * @param table   Таблица паролей.
     */
    @Override
    public void displayTable(List<Password> updData, TableView<Password> table) {
        table.setItems(FXCollections.observableArrayList(updData));
        table.refresh();
    }

    /**
     * Добавляет новую строку в таблицу паролей.
     *
     * @param password Новый пароль.
     * @param table    Таблица паролей.
     */
    @Override
    public void addTableRow(Password password, TableView<Password> table) {
        table.getItems().add(password);
        table.refresh();
    }

    /**
     * Удаляет строку из таблицы паролей.
     *
     * @param table Таблица паролей.
     * @param id    Идентификатор строки для удаления.
     */
    @Override
    public void deleteTableRow(TableView<Password> table, int id) {
        ObservableList<Password> data = table.getItems();
        data.remove(id);
        for (int i = id; i < data.size(); i++) {
            Password password = data.get(i);
            password.setId(password.getId() - 1);
        }
        table.refresh();
    }

    /**
     * Отображает всплывающую подсказку.
     *
     * @param label Текст подсказки.
     */
    @Override
    public void showTooltip(String label) {
        Tooltip tooltip = new Tooltip(label);
        tooltip.show(stage,
                stage.getX() + 10,
                stage.getY() + stage.getHeight() - 40);

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> tooltip.hide());
        delay.play();
    }

}
