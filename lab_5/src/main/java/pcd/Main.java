package pcd;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main {
    static final int PRODUCERS = 3;
    static final int CONSUMERS = 2;
    static Controller window;
    static BlockingQueue<Boolean> start = new LinkedBlockingQueue<Boolean>();
    static Depot depot = new Depot();

    public static void main(String[] args) throws Exception {
        Platform.startup(new Runnable() {
            @Override
            public void run() {
            }
        });
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("lab5_interf.fxml"));
        Scene scene = new Scene(loader.load());
        window = loader.getController();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage stage = new Stage();
                stage.setTitle("PCD – Lucrarea de laborator nr. 5");
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

        start.take();
        ExecutorService pool = Executors.newFixedThreadPool(PRODUCERS + CONSUMERS);
        for (int i = 1; i <= PRODUCERS; i++) {
            pool.execute(new Producer("Producător " + i));
        }
        for (int i = 1; i <= CONSUMERS; i++) {
            pool.execute(new Consumer("Consumator " + i));
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);
        window.showProducer("Toate sarcinile s-au terminat, pool-ul s-a închis. Firul principal s-a terminat.");
        window.showConsumer("Toate sarcinile s-au terminat, pool-ul s-a închis. Firul principal s-a terminat.");
    }
}
