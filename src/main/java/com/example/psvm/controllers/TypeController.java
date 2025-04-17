package com.example.psvm.controllers;

import com.example.psvm.model.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TypeController {

    @FXML
    private Label typeInfoLabel;

    public void setMessage(Message message) {
        typeInfoLabel.setText("ID: " + message.getItemId()
                + "\nType: " + message.getTypeLabel()
                + "\nMessage: " + message.getText());
//                + "\n " + message.get
    }

    @FXML
    private void handleClose(ActionEvent event) {
        Stage stage = (Stage) typeInfoLabel.getScene().getWindow();
        stage.close();
    }
}
