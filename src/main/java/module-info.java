module net.ictcampus.semodul.anlagendashboard {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires mysql.connector.j;
    requires java.rmi;

    opens net.ictcampus.semodul.anlagendashboard to javafx.fxml;
    exports net.ictcampus.semodul.anlagendashboard;
    exports net.ictcampus.semodul.anlagendashboard.gui;
    opens net.ictcampus.semodul.anlagendashboard.gui to javafx.fxml;

    // Give utility package and user package access to module GSON
    opens net.ictcampus.semodul.anlagendashboard.utility to com.google.gson;
    opens net.ictcampus.semodul.anlagendashboard.user to com.google.gson;
    opens net.ictcampus.semodul.anlagendashboard.portfoliometrics to com.google.gson;
}