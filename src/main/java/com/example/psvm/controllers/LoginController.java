package com.example.psvm.controllers;

import com.example.psvm.NavigationManager;
import com.example.psvm.database.LoginDB;
import com.example.psvm.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;

import static com.example.psvm.Startup.getUser;

/**
 * The LoginController class handles the user interface and actions related to logging in to the system.
 * The class communicates with the backend to validate user credentials and provides feedback
 * to the user on the success or failure of the login attempt.
 */
public class LoginController {
    private User user;

    @FXML
    private VBox loginContainer;
    @FXML
    private TextField usernameField;

    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;

    @FXML
    private CheckBox rememberMeCheckbox;

    private final LoginDB loginDB = new LoginDB();


    @FXML
    private void initialize() {
        loginContainer.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.ENTER) {
                loginButton.fire();
            }
        });
    }


    public LoginController() {
        this.user = getUser();
    }


    @FXML
    public void handleCloseAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleLoginAction(ActionEvent event) {
        String username = usernameField.getText().trim();

        if (username.isEmpty()) {
            showError("Voer een gebruikersnaam in.");
            return;
        }

        if (user.login(username)) {
            showSuccess("Succesvol ingelogd!");
            NavigationManager.switchTo("/com/example/psvm/screens/workitem-screen.fxml");
        } else {
            showError("Fout bij inloggen gebruiker.");
        }
    }

    private void showError(String message) {
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setText(message);
    }

    private void showSuccess(String message) {
        errorLabel.setStyle("-fx-text-fill: green;");
        errorLabel.setText(message);
    }

}
