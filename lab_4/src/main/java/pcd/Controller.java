package pcd;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class Controller {
    @FXML private Button startButton;
    @FXML private Label depotLabel;
    @FXML private TextArea producerArea;
    @FXML private TextArea consumerArea;
    boolean started = false;

    @FXML
    private synchronized void onStart() {
        startButton.setDisable(true);
        started = true;
        notify();
    }

    public void showProducer(String line) {
        print(producerArea, line);
    }

    public void showConsumer(String line) {
        print(consumerArea, line);
    }

    public void showDepot(String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                depotLabel.setText(text);
            }
        });
    }

    private void print(TextArea area, String line) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                area.appendText(line + "\n");
            }
        });
    }
}
