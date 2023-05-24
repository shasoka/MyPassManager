package manager.view;

import javafx.scene.control.TableView;
import javafx.stage.Stage;
import manager.controller.ManagerController;
import manager.model.Password;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Абстрактный класс, представляющий базовое представление приложения.
 */
public abstract class View {

    protected Stage stage; // Сцена, связанная с представлением

    protected ManagerController parent; // Родительский контроллер представления

    /**
     * Получает родительский контроллер.
     *
     * @return Родительский контроллер.
     */
    public ManagerController getParent() {
        return parent;
    }

    /**
     * Устанавливает родительский контроллер.
     *
     * @param parent Родительский контроллер.
     */
    public void setParent(ManagerController parent) {
        this.parent = parent;
    }

    /**
     * Конструктор представления.
     *
     * @param stage  Сцена, связанная с представлением.
     * @param parent Родительский контроллер.
     */
    public View(Stage stage, ManagerController parent) {
        this.stage = stage;
        this.parent = parent;
    }

    /**
     * Получает сцену представления.
     *
     * @return Сцена представления.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Устанавливает сцену представления.
     *
     * @param stage Сцена представления.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Отображает представление.
     *
     * @throws IOException  Возникает при ошибке загрузки представления.
     * @throws SQLException Возникает при ошибке работы с базой данных.
     */
    public void show() throws IOException, SQLException {}

    /**
     * Отображает таблицу с данными паролей.
     *
     * @param udpData Данные паролей.
     * @param table   Таблица для отображения данных.
     */
    public void displayTable(List<Password> udpData, TableView<Password> table) {}

    /**
     * Добавляет новую строку в таблицу паролей.
     *
     * @param password Добавляемый пароль.
     * @param table    Таблица для добавления строки.
     */
    public void addTableRow(Password password, TableView<Password> table) {}

    /**
     * Удаляет строку из таблицы паролей.
     *
     * @param table Таблица, из которой удаляется строка.
     * @param id    Идентификатор удаляемой строки.
     */
    public void deleteTableRow(TableView<Password> table, int id) {}

    /**
     * Отображает всплывающую подсказку.
     *
     * @param label Текст подсказки.
     */
    public void showTooltip(String label) {}
}
