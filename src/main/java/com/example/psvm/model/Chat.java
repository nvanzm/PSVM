package com.example.psvm.model;

import com.example.psvm.database.ChatDB;
import com.example.psvm.util.errors.DomainException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.psvm.Startup.getUser;

public class Chat {
    private final ChatDB chatDB;
    private User currentUser;
    private List<Message> messages;

    public Chat(ChatDB chatDB) {
        this.chatDB = chatDB;
        this.currentUser = getUser();
    }

    public void sendChatMessage(int userId, String messageText, int team_id) {
        if (messageText == null || messageText.trim().isEmpty()) {
            throw new DomainException("Bericht mag niet leeg zijn.");
        }
        chatDB.sendMessage(userId, messageText, team_id);
    }

//    private void loadMessages(int team_id) {
//        messages = chatDB.getMessages(team_id);
//    }

    public List<Message> getMessages(int team_id) {
        return chatDB.getMessages(team_id);
    }
}