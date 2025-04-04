package com.example.psvm.controllers;

import com.example.psvm.database.LoginDB;
import com.example.psvm.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;

import static com.example.psvm.Startup.getUser;

public class LoginController {
    private User user;


    @FXML
    private TextField usernameField;

    @FXML
    private Label errorLabel;

    @FXML
    private CheckBox rememberMeCheckbox;

    private final LoginDB loginDB = new LoginDB();


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
        String deviceId = DeviceHerkenning.getMacAddress();

        if (username.isEmpty()) {
            showError("Voer een gebruikersnaam in.");
            return;
        }

        // Als de gebruiker geldig is in de database
        if (user.login(username)) {
//            if (rememberMeCheckbox.isSelected()) {
//                DeviceHerkenning.saveLogin(username, deviceId);  // Opslaan van login
//            }
            showSuccess("Succesvol ingelogd!");
            switchToView(event, "/com/example/psvm/screens/chatroom-screen.fxml");
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

    private void switchToView(ActionEvent event, String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            stage.setTitle("Loef");
            stage.setMaximized(true);

        } catch (IOException e) {
            System.err.println("Fout bij het laden van de view: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static class DeviceHerkenning {

        private static final String FILE_PATH = "login_data.txt";

        public static String getMacAddress() {
            try {
                InetAddress ip = InetAddress.getLocalHost();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                byte[] mac = network.getHardwareAddress();

                if (mac == null) {
                    return "UNKNOWN";
                }

                StringBuilder sb = new StringBuilder();
                for (byte b : mac) {
                    sb.append(String.format("%02X-", b));
                }
                return sb.substring(0, sb.length() - 1);

            } catch (Exception e) {
                return "UNKNOWN";
            }
        }

        public static void saveLogin(String username, String deviceId) {
            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                writer.write(username + "," + deviceId);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static boolean isUserLoggedIn(String username, String deviceId) {
            File file = new File(FILE_PATH);
            if (!file.exists()) return false;

            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String storedData = reader.readLine();
                if (storedData != null) {
                    String[] parts = storedData.split(",");
                    if (parts.length == 2) {
                        return parts[0].equals(username) && parts[1].equals(deviceId);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
