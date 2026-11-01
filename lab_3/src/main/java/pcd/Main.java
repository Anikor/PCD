package pcd;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main {
    static Controller window;
    static Thread mainThread;
    static int[] mas;
    static Thread th1, th2, th3, th4;

    public static void main(String[] args) throws Exception {
        Platform.startup(new Runnable() {
            @Override
            public void run() {
            }
        });
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("lab3_interf.fxml"));
        Scene scene = new Scene(loader.load());
        window = loader.getController();
        mainThread = Thread.currentThread();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage stage = new Stage();
                stage.setTitle("PCD – Lucrarea de laborator nr. 3");
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

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            window.show("Butonul Start a trezit firul principal.");
        }
        th1 = new SumForward();
        th2 = new SumBackward();
        th3 = new WalkForward();
        th4 = new WalkBackward();
        th1.setName("Th1");
        th2.setName("Th2");
        th3.setName("Th3");
        th4.setName("Th4");
        th1.start();
        th2.start();
        th3.start();
        th4.start();
        th1.join();
        th2.join();
        th3.join();
        th4.join();
        window.show("Toate firele s-au terminat. Firul principal s-a terminat.");
    }
}
