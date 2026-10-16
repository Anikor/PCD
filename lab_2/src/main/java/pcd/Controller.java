package pcd;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class Controller {
    @FXML private Label formula;
    @FXML private Button mediumButton;
    @FXML private Button advancedButton;
    @FXML private TextArea out;

    private final BlockingQueue<Boolean> starts = new LinkedBlockingQueue<>();

    @FXML
    private void initialize() {
        formula.setText(Structure.FORMULA);
    }

    @FXML
    private void onMedium() {
        begin(false);
    }

    @FXML
    private void onAdvanced() {
        begin(true);
    }

    private void begin(boolean advanced) {
        out.clear();
        mediumButton.setDisable(true);
        advancedButton.setDisable(true);
        starts.add(advanced);
    }

    public boolean waitForStart() throws InterruptedException {
        return starts.take();
    }

    public void enableButtons() {
        Platform.runLater(() -> {
            mediumButton.setDisable(false);
            advancedButton.setDisable(false);
        });
    }

    public void show(String line) {
        Platform.runLater(() -> out.appendText(line + "\n"));
    }
}
