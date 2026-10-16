package pcd;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main {
    public static void main(String[] args) throws Exception {
        Platform.startup(() -> { });
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("lab2_interf.fxml"));
        Parent root = loader.load();
        Controller window = loader.getController();
        Platform.runLater(() -> {
            Stage stage = new Stage();
            stage.setTitle("PCD – Lucrarea de laborator nr. 2");
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> System.exit(0));
            stage.show();
        });

        boolean mediumPressed = false;
        boolean advancedPressed = false;
        while (!(mediumPressed && advancedPressed)) {
            boolean advanced = window.waitForStart();
            if (advanced) {advancedPressed = true;} else {mediumPressed = true;}

            window.show(advanced ? "Nivelul avansat: fiecare fir se prezintă singur din run()" : "Nivelul mediu: firele sunt enumerate de firul principal");
            Structure structure = new Structure(advanced, window);
            structure.start();
            new Lister(window).list(structure.getMain(), "");
            structure.join();
            window.show("Toate firele s-au terminat.");
            if (!(mediumPressed && advancedPressed)) {
                window.enableButtons();
            }
        }
        window.show("Ambele butoane au fost apăsate. Programul s-a terminat.");
    }
}
