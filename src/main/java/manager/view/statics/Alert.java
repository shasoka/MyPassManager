package manager.view.statics;

import javafx.scene.control.Label;

/**
 * Класс Alert предоставляет статические методы для отображения всплывающих сообщений.
 */
public class Alert {

    /**
     * Отображает окно с сообщением об ошибке.
     *
     * @param msg  Сообщение об ошибке.
     * @param name Заголовок окна.
     */
    public static void showAlert(String msg, String name) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(name);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Отображает окно с информацией о приложении.
     */
    public static void showHelp() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("MyPassManager");
        alert.getDialogPane().setContent( new Label("""
                Welcome to simple password manager!
                Here you can add / edit or delete your passwords. Also you can change your pin, import or export .csv.
                It uses SQlite for database, Blowfish algorythm for encoding and architectured with MVC pattern."""));
        alert.showAndWait();
    }

}
