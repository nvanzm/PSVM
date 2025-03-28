module com.example.psvm {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.zaxxer.hikari;
    requires java.sql;


    opens com.example.psvm to javafx.fxml;
    exports com.example.psvm;
}