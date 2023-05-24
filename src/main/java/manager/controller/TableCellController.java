package manager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import manager.view.TableCellView;

import java.net.URL;
import java.util.ResourceBundle;

import static manager.encoder.Encoder.decrypt;

/**
 * Контроллер для ячейки таблицы с паролями.
 */
public class TableCellController implements Initializable {

    private TableCellView view;  // Представление TableCellView

    private final Button showPasswordButton = new Button("Show");  // Кнопка для показа пароля

    private boolean hidden = true;  // Флаг для кнопки

    /**
     * Возвращает представление ячейки таблицы.
     *
     * @return Представление ячейки таблицы
     */
    public TableCellView getView() {
        return view;
    }

    /**
     * Возвращает кнопку "Show" для отображения пароля.
     *
     * @return Кнопка "Show"
     */
    public Button getShowPasswordButton() {
        return showPasswordButton;
    }

    /**
     * Устанавливает представление ячейки таблицы.
     *
     * @param view Представление ячейки таблицы
     */
    public void setView(TableCellView view) {
        this.view = view;
    }

    /**
     * Конструктор класса.
     *
     * @param view Представление ячейки таблицы
     */
    public TableCellController(TableCellView view) {
        this.view = view;
    }

    /**
     * Переопределенный метод инициализации контроллера.
     *
     * @param url URL, вызвавший инициализацию.
     * @param resourceBundle Связанный с контроллером ResourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        view.setOnMouseClicked(this::mouseClickHandler);
        showPasswordButton.setOnAction(this::showBtnHandler);
    }

    /**
     * Обработчик событий мыши.
     *
     * @param event Событие мыши.
     */
    private void mouseClickHandler(MouseEvent event) {
        if (!hidden) {
            String password = view.getItem();
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            try {
                content.putString(decrypt(password));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            clipboard.setContent(content);
            view.showToolptip(event);
        }
    }

    /**
     * Обработчик события нажатия кнопки показа пароля.
     *
     * @param event Событие нажатия на кнопку.
     */
    private void showBtnHandler(ActionEvent event) {
        if (hidden) {
            try {
                view.setText(decrypt(view.getItem()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            showPasswordButton.setText("Hide");
        } else {
            view.setText(TableCellView.HIDDEN_PASS);
            showPasswordButton.setText("Show");
        }
        hidden = !hidden;
    }
}
