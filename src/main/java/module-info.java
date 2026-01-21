module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    opens net.ictcampus.semodul.anlagendashboard to javafx.fxml;
    exports net.ictcampus.semodul.anlagendashboard;
}