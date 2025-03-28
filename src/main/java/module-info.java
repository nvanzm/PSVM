module com.example.psvm {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.psvm to javafx.fxml;
    exports com.example.psvm;
}