package com.example.psvm.model;

import com.example.psvm.database.ChatDB;
import com.example.psvm.util.errors.DomainException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatTest {

    /**
     * Tests for the sendChatMessage method in the Chat class.
     * <p>
     * The sendChatMessage method is responsible for sending a message to the provided user/team
     * and optionally associates the message with an item if itemId and itemType are provided.
     * It ensures that the message text is not null or empty, and the operation is handled by the ChatDB instance.
     */

    @Test
    void sendChatMessage_shouldSendValidMessage() {
        ChatDB chatDB = mock(ChatDB.class);
        Chat chat = new Chat(chatDB);

        int userId = 1;
        String messageText = "Hello, team!";
        int teamId = 10;

        chat.sendChatMessage(userId, messageText, teamId, null, null);

        verify(chatDB, times(1)).sendMessage(userId, messageText, teamId, null, null);
    }

    @Test
    void sendChatMessage_shouldThrowExceptionForEmptyMessage() {
        ChatDB chatDB = mock(ChatDB.class);
        Chat chat = new Chat(chatDB);

        int userId = 1;
        String messageText = "   ";
        int teamId = 10;

        DomainException thrown = assertThrows(DomainException.class, () ->
                chat.sendChatMessage(userId, messageText, teamId, null, null));

        assertEquals("Bericht mag niet leeg zijn.", thrown.getMessage());
        verify(chatDB, never()).sendMessage(anyInt(), anyString(), anyInt(), any(), any());
    }

    @Test
    void sendChatMessage_shouldSendMessageWithItemIdAndItemType() {
        ChatDB chatDB = mock(ChatDB.class);
        Chat chat = new Chat(chatDB);

        int userId = 1;
        String messageText = "Task update.";
        int teamId = 10;
        int itemId = 5;
        String itemType = "Task";

        chat.sendChatMessage(userId, messageText, teamId, Integer.valueOf(itemId), itemType);

        verify(chatDB, times(1)).sendMessage(userId, messageText, teamId, Integer.valueOf(itemId), itemType);
    }

    @Test
    void sendChatMessage_shouldThrowExceptionForNullMessage() {
        ChatDB chatDB = mock(ChatDB.class);
        Chat chat = new Chat(chatDB);

        int userId = 1;
        String messageText = null;
        int teamId = 10;

        DomainException thrown = assertThrows(DomainException.class, () ->
                chat.sendChatMessage(userId, messageText, teamId, null, null));

        assertEquals("Bericht mag niet leeg zijn.", thrown.getMessage());
        verify(chatDB, never()).sendMessage(anyInt(), anyString(), anyInt(), any(), any());
    }
}