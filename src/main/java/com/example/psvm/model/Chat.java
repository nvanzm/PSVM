package com.example.psvm.model;

import com.example.psvm.database.ChatDB;
import com.example.psvm.util.enums.Filter;
import com.example.psvm.util.errors.DomainException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.psvm.Startup.getUser;

public class Chat {
    private final ChatDB chatDB;
    private User currentUser;
    private List<Message> messages;
    private List<Message> filteredMessages;


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

    private void loadMessages() {
        messages = chatDB.getMessages();
    }

    public List<Message> getMessages(Optional<Filter> filter) {
        if (filter.isPresent()) {
            switch (filter.get()) {
                case TEAM:
                    loadMessages();
                    break;
                case WORKITEM:

            }
        }
        return this.messages;
    }
}