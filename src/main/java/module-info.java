module com.example.psvm {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.zaxxer.hikari;
    requires java.sql;
    requires java.prefs;


    opens com.example.psvm to javafx.fxml;
    exports com.example.psvm;
    exports com.example.psvm.controllers;
    opens com.example.psvm.controllers to javafx.fxml;
}