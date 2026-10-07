package pcd;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Controller {
    @FXML private TextArea arrayArea;
    @FXML private Button startButton;
    @FXML private TextArea out1;
    @FXML private TextArea out2;
    @FXML private TextArea out3;

    private final BlockingQueue<int[]> starts = new LinkedBlockingQueue<>();

    @FXML
    private void initialize() {
        StringBuilder text = new StringBuilder();
        for (int number : DataArray.MAS) {
            text.append(number).append(" ");
        }
        arrayArea.setText(text.toString().trim());
    }

    @FXML
    private void onStart() {
        try {
            String[] parts = arrayArea.getText().trim().split("\\s+");
            int[] mas = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                mas[i] = Integer.parseInt(parts[i]);
            }
            out1.clear();
            out2.clear();
            out3.clear();
            startButton.setDisable(true);
            starts.add(mas);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Tabloul trebuie să conțină doar numere întregi, separate prin spațiu.").show();
        }
    }

    public int[] waitForStart() throws InterruptedException {
        return starts.take();
    }

    public void enableStart() {
        Platform.runLater(() -> startButton.setDisable(false));
    }

    public void show(int student, String line) {
        TextArea[] areas = {out1, out2, out3};
        Platform.runLater(() -> areas[student - 1].appendText(line + "\n"));
    }
}
