package com.example.psvm.model;

import com.example.psvm.database.ChatDB;
import com.example.psvm.util.errors.DomainException;

import java.util.List;

import static com.example.psvm.Startup.getScrumBoard;
import static com.example.psvm.Startup.getUser;

public class Chat {
    private final ChatDB chatDB;
    private User currentUser;
    private List<Message> messages;
    private final ScrumBoard scrumBoard;

    /**
     * Constructs a new Chat instance with the provided ChatDB instance.
     * Initializes the current user and scrum board for the chat.
     *
     * @param chatDB the ChatDB instance used for database operations related to chat functionality
     */
    public Chat(ChatDB chatDB) {
        this.chatDB = chatDB;
        this.currentUser = getUser();
        this.scrumBoard = getScrumBoard();
    }

    public void sendChatMessage(int userId, String messageText, int team_id, Integer itemId, String itemType) {
        if (messageText == null || messageText.trim().isEmpty()) {
            throw new DomainException("Bericht mag niet leeg zijn.");
        }

        if (itemId != null && itemType != null) {
            chatDB.sendMessage(userId, messageText, team_id, itemId, itemType);
        } else {
            chatDB.sendMessage(userId, messageText, team_id, null, null);
        }
    }

    public List<Message> getMessages(int team_id) {
        messages = chatDB.getMessages(team_id);
        scrumBoard.load(team_id);
        return scrumBoard.enrichMessagesWithWorkItems(messages);
    }

}
