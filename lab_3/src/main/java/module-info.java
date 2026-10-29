module pcd {
    requires javafx.controls;
    requires javafx.fxml;

    opens pcd to javafx.fxml;
    exports pcd;
}
