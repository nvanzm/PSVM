package com.example.psvm.model;

import com.example.psvm.database.ChatDB;
import com.example.psvm.util.errors.DomainException;

import java.util.ArrayList;
import java.util.List;

import static com.example.psvm.Startup.getUser;

public class Chat {
    private final ChatDB chatDB;
    private User currentUser;
    private ArrayList<Message> messages;



    public Chat(ChatDB chatDB) {
        this.chatDB = chatDB;
        this.currentUser = getUser();
    }

    public void sendChatMessage(int userId, String messageText) {
        if (messageText == null || messageText.trim().isEmpty()) {
            throw new DomainException("Bericht mag niet leeg zijn.");
        }
        chatDB.sendMessage(userId, messageText);
    }

    public List<Message> getMessages() {
        return chatDB.getMessages();
    }
}