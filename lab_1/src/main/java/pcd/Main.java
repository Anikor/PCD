package pcd;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main {
    public static void main(String[] args) throws Exception {
        Platform.startup(() -> { });
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("lab1_interf.fxml"));
        Parent root = loader.load();
        Controller window = loader.getController();
        Platform.runLater(() -> {
            Stage stage = new Stage();
            stage.setTitle("PCD – Lucrarea de laborator nr. 1");
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> System.exit(0));
            stage.show();
        });

        while (true) {
            int[] mas = window.waitForStart();
            int last = mas.length - 1;

            Thread[] threads = {
                    new CounterThread("Th1", 0, last, 1, mas, window),
                    new CounterThread("Th2", last, 0, -1, mas, window),
                    new Thread(new CounterRunnable(0, last, 1, mas, window), "Th1"),
                    new Thread(new CounterRunnable(last, 0, -1, mas, window), "Th2")
            };
            for (Thread thread : threads) {
                thread.start();
            }

            CounterRunnableAuto[] started = {
                    new CounterRunnableAuto("Th1", 0, last, 1, mas, window),
                    new CounterRunnableAuto("Th2", last, 0, -1, mas, window)
            };

            for (Thread thread : threads) {
                thread.join();
            }
            for (CounterRunnableAuto counter : started) {
                counter.join();
            }

            window.enableStart();
        }
    }
}
