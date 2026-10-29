package pcd;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Controller {
    @FXML private TextArea arrayArea;
    @FXML private Button startButton;
    @FXML private TextArea out;

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
            out.clear();
            startButton.setDisable(true);
            Main.mas = mas;
            Main.mainThread.interrupt();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Tabloul trebuie să conțină doar numere întregi, separate prin spațiu.").show();
        }
    }

    public void show(String line) {
        print(line + "\n");
    }

    public void type(String text) {
        for (char letter : text.toCharArray()) {
            print(String.valueOf(letter));
        }
        print("\n");
    }

    private void print(String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                out.appendText(text);
            }
        });
    }
}
