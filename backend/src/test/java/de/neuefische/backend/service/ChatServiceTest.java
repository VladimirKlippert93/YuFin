package de.neuefische.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.backend.models.ChatMessage;
import de.neuefische.backend.repository.ChatRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private ChatRepo chatRepo;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private WebSocketSession session;
    @InjectMocks
    private ChatService chatService;

    @Test
    void saveMessageTest() {
        ChatMessage message = new ChatMessage("sender", "receiver", "Hello, how are you?", LocalDateTime.now());
        when(chatRepo.save(message)).thenReturn(message);

        ChatMessage savedMessage = chatService.saveMessage(message);

        assertEquals(message, savedMessage);
        verify(chatRepo, times(1)).save(message);
    }

    @Test
    void sendPreviousMessagesTest() throws Exception {
            List<ChatMessage> messages = Arrays.asList(
                    new ChatMessage("sender", "receiver", "Hello", LocalDateTime.now()),
                    new ChatMessage("receiver", "sender", "Hi", LocalDateTime.now())
            );
            lenient().when(session.getPrincipal()).thenReturn(new Principal() {
                @Override
                public String getName() {
                    return "sender";
                }
            });
            when(objectMapper.writeValueAsString(messages.get(0))).thenReturn("{\"sender\":\"sender\",\"receiver\":\"receiver\",\"message\":\"Hello\",\"timestamp\":\""+messages.get(0).getTimestamp()+"\"}");
            when(objectMapper.writeValueAsString(messages.get(1))).thenReturn("{\"sender\":\"receiver\",\"receiver\":\"sender\",\"message\":\"Hi\",\"timestamp\":\""+messages.get(1).getTimestamp()+"\"}");
            when(chatRepo.findAllBySenderUsernameAndReceiverUsername("sender", "receiver"))
                    .thenReturn(messages);
            chatService.sendPreviousMessages(session, "sender", "receiver");
            ArgumentCaptor<TextMessage> textMessageArgumentCaptor = ArgumentCaptor.forClass(TextMessage.class);
            verify(session, times(2)).sendMessage(textMessageArgumentCaptor.capture());
            List<TextMessage> capturedTextMessages = textMessageArgumentCaptor.getAllValues();

            assertEquals("{\"sender\":\"sender\",\"receiver\":\"receiver\",\"message\":\"Hello\",\"timestamp\":\""+messages.get(0).getTimestamp()+"\"}", capturedTextMessages.get(0).getPayload());
            assertEquals("{\"sender\":\"receiver\",\"receiver\":\"sender\",\"message\":\"Hi\",\"timestamp\":\""+messages.get(1).getTimestamp()+"\"}", capturedTextMessages.get(1).getPayload());
            verify(chatRepo).findAllBySenderUsernameAndReceiverUsername("sender", "receiver");
            verify(chatRepo).findAllBySenderUsernameAndReceiverUsername("receiver", "sender");
            verifyNoMoreInteractions(session, chatRepo, objectMapper);
    }
}