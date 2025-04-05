package com.example.psvm.model;

import com.example.psvm.database.ChatDB;
import com.example.psvm.util.errors.DomainException;

public class Chat {
    private final ChatDB chatDB;

    public Chat(ChatDB chatDB) {
        this.chatDB = chatDB;
    }

    public void sendChatMessage(String messageText) {
        if (messageText == null || messageText.trim().isEmpty()) {
            throw new DomainException("Bericht mag niet leeg zijn.");
        }
        chatDB.sendMessage(messageText);
    }
}