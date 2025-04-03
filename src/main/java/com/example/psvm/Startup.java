package com.example.psvm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Startup extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        String loginDataFilePath = "login_data.txt";
        File loginDataFile = new File(loginDataFilePath);

        if (loginDataFile.exists()) {
            String storedData = readStoredLoginData(loginDataFile);
            String[] parts = storedData.split(",");
            if (parts.length == 2) {
                loadMainScreen(stage);
                return;
            }
        }
        loadLoginScreen(stage);
    }

    private void loadLoginScreen(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Startup.class.getResource("/com/example/psvm/screens/login-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("PSVM");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    private void loadMainScreen(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Startup.class.getResource("/com/example/psvm/screens/chatroom-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Loef");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    private String readStoredLoginData(File loginDataFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(loginDataFile));
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        launch();
    }
}
