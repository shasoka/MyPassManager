package manager.controller;

import javafx.fxml.Initializable;
import manager.model.PassDbModel;
import manager.view.LogInView;
import manager.view.View;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Базовый абстрактный класс контроллера.
 * Реализует интерфейс Initializable для инициализации контроллера.
 * Содержит ссылки на модель базы данных и представление.
 */
public abstract class Controller implements Initializable {

    protected View view;  // Представление

    protected PassDbModel model;  // Модель

    /**
     * Конструктор без параметров класса Controller.
     */
    protected Controller() {}

    /**
     * Конструктор класса Controller.
     *
     * @param model модель базы данных
     * @param view  представление
     */
    public Controller(PassDbModel model, View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Получает объект представления.
     *
     * @return объект представления
     */
    public View getView() {
        return view;
    }

    /**
     * Устанавливает объект представления.
     *
     * @param view объект представления
     */
    public void setView(LogInView view) {
        this.view = view;
    }

    /**
     * Получает модель базы данных.
     *
     * @return модель базы данных
     */
    public PassDbModel getModel() {
        return model;
    }

    /**
     * Устанавливает модель базы данных.
     *
     * @param model модель базы данных
     */
    public void setModel(PassDbModel model) {
        this.model = model;
    }

    /**
     * Переопределенный метод инициализации контроллера.
     *
     * @param url URL, вызвавший инициализацию.
     * @param resourceBundle Связанный с контроллером ResourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
