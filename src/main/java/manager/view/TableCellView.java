package manager.view;

import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import manager.controller.TableCellController;
import manager.model.Password;

public class TableCellView extends TableCell<Password, String> {

    private TableCellController controller;

    private boolean tooltipActive = false;

    public final String HIDDEN_PASS = "*****";

    @Override
    protected void updateItem(String password, boolean empty) {
        super.updateItem(password, empty);

        controller = new TableCellController(this);
        controller.initialize(null, null);

        setText(HIDDEN_PASS);
        controller.getShowPasswordButton().setPrefWidth(50);
        setGraphic(controller.getShowPasswordButton());
    }

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
