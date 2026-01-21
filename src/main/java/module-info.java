module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;

    opens net.ictcampus.semodul.anlagendashboard to javafx.fxml;
    exports net.ictcampus.semodul.anlagendashboard;
    exports net.ictcampus.semodul.anlagendashboard.gui;
    opens net.ictcampus.semodul.anlagendashboard.gui to javafx.fxml;
}