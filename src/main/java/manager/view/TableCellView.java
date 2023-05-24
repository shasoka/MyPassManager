package manager.view;

import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import manager.controller.TableCellController;
import manager.model.Password;

/**
 * Пользовательский класс ячейки таблицы паролей.
 */
public class TableCellView extends TableCell<Password, String> {

    private boolean tooltipActive = false; // Флаг активности всплывающей подсказки

    public static final String HIDDEN_PASS = "*****"; // Заменяемый текст пароля

    /**
     * Обновляет ячейку таблицы.
     *
     * @param password Новый пароль.
     * @param empty    Флаг, указывающий пуста ли ячейка.
     */
    @Override
    protected void updateItem(String password, boolean empty) {
        super.updateItem(password, empty);

        // Контроллер ячейки таблицы
        TableCellController controller = new TableCellController(this);
        controller.initialize(null, null);

        if (!empty) {
            setText(HIDDEN_PASS);
            controller.getShowPasswordButton().setPrefWidth(50);
            setGraphic(controller.getShowPasswordButton());
        }
    }

    /**
     * Отображает всплывающую подсказку при нажатии на кнопку показа пароля.
     *
     * @param event Событие нажатия на кнопку
     */
    public void showToolptip(MouseEvent event) {
        if (!tooltipActive) {
            Tooltip tooltip = new Tooltip("Password copied!");
            tooltip.show((Node) event.getSource(), event.getScreenX() + 10, event.getScreenY() - 30);
            tooltipActive = true;

            PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
            delay.setOnFinished(e -> {
                tooltip.hide();
                tooltipActive = false;
            });
            delay.play();
        }
    }
}
