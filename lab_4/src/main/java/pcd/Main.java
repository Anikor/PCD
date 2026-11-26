package pcd;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main {
    static Controller window;
    static Depot depot = new Depot();
    static Thread[] producers = new Thread[3];
    static Thread[] consumers = new Thread[2];

    public static void main(String[] args) throws Exception {
        Platform.startup(new Runnable() {
            @Override
            public void run() {
            }
        });
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("lab4_interf.fxml"));
        Scene scene = new Scene(loader.load());
        window = loader.getController();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage stage = new Stage();
                stage.setTitle("PCD – Lucrarea de laborator nr. 4");
                stage.setScene(scene);
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        System.exit(0);
                    }
                });
                stage.show();
            }
        });

        synchronized (window) {
            while (!window.started) {
                window.wait();
            }
        }
        for (int i = 0; i < producers.length; i++) {
            producers[i] = new Producer();
            producers[i].setName("Producător " + (i + 1));
            producers[i].start();
        }
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer();
            consumers[i].setName("Consumator " + (i + 1));
            consumers[i].start();
        }
        for (Thread consumer : consumers) {
            consumer.join();
        }
        depot.finish();
        for (Thread producer : producers) {
            producer.join();
        }
        window.showProducer("Toate firele s-au terminat. Firul principal s-a terminat.");
        window.showConsumer("Toate firele s-au terminat. Firul principal s-a terminat.");
    }
}
