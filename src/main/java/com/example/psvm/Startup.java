package com.example.psvm;

import com.example.psvm.database.ChatDB;
import com.example.psvm.database.TeamDB;
import com.example.psvm.model.Chat;
import com.example.psvm.model.Team;
import com.example.psvm.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Startup extends Application {
    private static User user;
    private static Chat chat;
    private static ChatDB chatDB;
    private static Team team;

    @Override
    public void start(Stage stage) throws IOException {
        String loginDataFilePath = "login_data.txt";
        File loginDataFile = new File(loginDataFilePath);
        NavigationManager.setStage(stage);

        user = new User();
        chatDB = new ChatDB();
        chat = new Chat(chatDB);
        team = new Team();

        if (loginDataFile.exists()) {
            String storedData = readStoredLoginData(loginDataFile);
            String[] parts = storedData.split(",");
            if (parts.length == 2) {
                loadMainScreen(stage);
                return;
            }
        }
        NavigationManager.switchTo("/com/example/psvm/screens/login-screen.fxml");
    }

    private void loadMainScreen(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Startup.class.getResource("/com/example/psvm/screens/chatroom-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Teamflow");
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

    public static User getUser() {
        return user;
    }

    public static Chat getChat() {
        return chat;
    }

    public static Team getTeam() {
        return team;
    }
}
