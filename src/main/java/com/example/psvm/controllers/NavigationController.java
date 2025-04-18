package com.example.psvm.controllers;

import com.example.psvm.NavigationManager;
import com.example.psvm.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.example.psvm.Startup.getUser;


/**
 * The NavigationController class manages navigation within the application and is responsible
 * for handling UI interactions related to scene transitions and user-specific actions.
 * It utilizes the NavigationManager to load different views and ensures user-specific
 * data is displayed in the interface, such as a personalized welcome message.
 */
public class NavigationController {

    @FXML
    private AnchorPane scenePane;

    @FXML
    private Button welkomNaam;

    private User user;

    public void initialize() {
        this.user = getUser();
        welkomNaam.setText("Welkom terug, " + user.getNameById(user.getId()) + "!");
    }

    private void switchScene(ActionEvent event, String fxmlFileName) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFileName)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void chatroom (ActionEvent event) throws IOException {
        NavigationManager.switchTo("/com/example/psvm/screens/chatroom-screen.fxml");
    }

    public void scrumboard(ActionEvent event) throws IOException {
        NavigationManager.switchTo("/com/example/psvm/screens/workitem-screen.fxml");
    }

    public void team (ActionEvent event) throws IOException {
        NavigationManager.switchTo("/com/example/psvm/screens/team-screen.fxml");
    }

    public void profiel (ActionEvent event) throws IOException {
        NavigationManager.switchTo("/com/example/psvm/screens/profiel-screen.fxml");
    }

    public void settings (ActionEvent event) throws IOException {
        NavigationManager.switchTo("/com/example/psvm/screens/settings-screen.fxml");
    }

    public void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to logout");
        alert.setContentText("Are you sure you want to logout?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            Stage stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("You've been logged out.");
            stage.close();
        }
    }
}
