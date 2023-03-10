package de.neuefische.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.backend.models.ChatMessage;
import de.neuefische.backend.repository.ChatRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Captor
    private ArgumentCaptor<TextMessage> textMessageCaptor;

    @BeforeEach
    public void setUp() {
        chatService = new ChatService(chatRepo, objectMapper);
    }

    @Test
    void saveMessageTest() {
        ChatMessage message = ChatMessage.builder()
                .senderUsername("sender")
                .receiverUsername("receiver")
                .message("Hello")
                .timestamp(LocalDateTime.now())
                .build();
        when(chatRepo.save(message)).thenReturn(message);

        ChatMessage savedMessage = chatService.saveMessage(message);

        assertEquals(message, savedMessage);
        verify(chatRepo, times(1)).save(message);
    }

    @Test
    void sendPreviousMessagesTest() throws Exception {
        List<ChatMessage> messages = Arrays.asList(
                ChatMessage.builder()
                        .senderUsername("sender")
                        .receiverUsername("receiver")
                        .message("Hello")
                        .timestamp(LocalDateTime.now())
                        .build(),
                ChatMessage.builder()
                        .senderUsername("receiver")
                        .receiverUsername("sender")
                        .message("Hi")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
        lenient().when(session.getPrincipal()).thenReturn(() -> "sender");
        when(objectMapper.writeValueAsString(messages.get(0))).thenReturn("{\"sender\":\"sender\",\"receiver\":\"receiver\",\"message\":\"Hello\",\"timestamp\":\"" + messages.get(0).getTimestamp() + "\"}");
        when(objectMapper.writeValueAsString(messages.get(1))).thenReturn("{\"sender\":\"receiver\",\"receiver\":\"sender\",\"message\":\"Hi\",\"timestamp\":\"" + messages.get(1).getTimestamp() + "\"}");
        when(chatRepo.findAllBySenderUsernameAndReceiverUsername("sender", "receiver"))
                .thenReturn(messages);
        chatService.sendPreviousMessages(session, "sender", "receiver");
        ArgumentCaptor<TextMessage> textMessageArgumentCaptor = ArgumentCaptor.forClass(TextMessage.class);
        verify(session, times(2)).sendMessage(textMessageArgumentCaptor.capture());
        List<TextMessage> capturedTextMessages = textMessageArgumentCaptor.getAllValues();

        assertEquals("{\"sender\":\"sender\",\"receiver\":\"receiver\",\"message\":\"Hello\",\"timestamp\":\"" + messages.get(0).getTimestamp() + "\"}", capturedTextMessages.get(0).getPayload());
        assertEquals("{\"sender\":\"receiver\",\"receiver\":\"sender\",\"message\":\"Hi\",\"timestamp\":\"" + messages.get(1).getTimestamp() + "\"}", capturedTextMessages.get(1).getPayload());
        verify(chatRepo).findAllBySenderUsernameAndReceiverUsername("sender", "receiver");
        verify(chatRepo).findAllBySenderUsernameAndReceiverUsername("receiver", "sender");
        verifyNoMoreInteractions(session, chatRepo, objectMapper);
    }

    @Test
    void handleTextMessage_shouldSaveMessageInRepo() throws Exception {

        ChatMessage chatMessage = ChatMessage.builder()
                .senderUsername("sender")
                .receiverUsername("receiver")
                .message("Hello")
                .timestamp(LocalDateTime.now())
                .build();
        when(objectMapper.readValue(anyString(), eq(ChatMessage.class))).thenReturn(chatMessage);
        when(session.getPrincipal()).thenReturn(() -> "sender");
        when(objectMapper.writeValueAsString(chatMessage)).thenReturn("{\"senderUsername\":\"sender\",\"receiverUsername\":\"receiver\",\"message\":\"Hello\",\"timestamp\":\"2022-01-23T10:15:30\"}");


        chatService.handleTextMessage(session, new TextMessage("{\"senderUsername\":\"sender\",\"receiverUsername\":\"receiver\",\"message\":\"Hello\",\"timestamp\":\"2022-01-23T10:15:30\"}"));


        verify(chatRepo, times(1)).save(chatMessage);
    }

    @Test
    void handleTextMessage_shouldSendMessageToReceiver() throws Exception {

        ChatMessage chatMessage = ChatMessage.builder()
                .senderUsername("sender")
                .receiverUsername("receiver")
                .message("Hello")
                .timestamp(LocalDateTime.now())
                .build();
        when(objectMapper.readValue(anyString(), eq(ChatMessage.class))).thenReturn(chatMessage);
        when(session.getPrincipal()).thenReturn(() -> "sender");
        when(objectMapper.writeValueAsString(chatMessage)).thenReturn("{\"senderUsername\":\"sender\",\"receiverUsername\":\"receiver\",\"message\":\"Hello\",\"timestamp\":\"2022-01-23T10:15:30\"}");

        chatService.handleTextMessage(session, new TextMessage("{\"senderUsername\":\"sender\",\"receiverUsername\":\"receiver\",\"message\":\"Hello\",\"timestamp\":\"2022-01-23T10:15:30\"}"));


        verify(session, times(1)).sendMessage(textMessageCaptor.capture());
        TextMessage sentTextMessage = textMessageCaptor.getValue();
        assertEquals("{\"senderUsername\":\"sender\",\"receiverUsername\":\"receiver\",\"message\":\"Hello\",\"timestamp\":\"2022-01-23T10:15:30\"}", sentTextMessage.getPayload());
    }

    @Test
    void handleTextMessage_shouldSetId() throws Exception {

        ChatMessage chatMessage = ChatMessage.builder()
                .senderUsername("sender")
                .receiverUsername("receiver")
                .message("Hello")
                .timestamp(LocalDateTime.now())
                .build();
        when(objectMapper.readValue(anyString(), eq(ChatMessage.class))).thenReturn(chatMessage);
        when(session.getPrincipal()).thenReturn(() -> "sender");
        when(objectMapper.writeValueAsString(chatMessage)).thenReturn("{\"senderUsername\":\"sender\",\"receiverUsername\":\"receiver\",\"message\":\"Hello\",\"timestamp\":\"2022-01-23T10:15:30\"}");

        chatService.handleTextMessage(session, new TextMessage("{\"senderUsername\":\"sender\",\"receiverUsername\":\"receiver\",\"message\":\"Hello\",\"timestamp\":\"2022-01-23T10:15:30\"}"));


        assertEquals(String.class, chatMessage.getId().getClass());
    }

    @Test
    void afterConnectionEstablished_shouldAddSessionToSessions() throws Exception {

        when(session.getPrincipal()).thenReturn(() -> "sender");
        ChatMessage lastChatMessage = ChatMessage.builder()
                .senderUsername("sender")
                .receiverUsername("receiver")
                .message("Hello")
                .timestamp(LocalDateTime.now())
                .build();
        when(chatRepo.findFirstBySenderUsernameOrderByTimestampDesc("sender")).thenReturn(lastChatMessage);

        chatService.afterConnectionEstablished(session);

        assertTrue(chatService.getSessions().contains(session));
    }

    @Test
    void afterConnectionEstablished_shouldSendPreviousMessages() throws Exception {

        ChatService chatServiceSpy = spy(chatService);
        when(session.getPrincipal()).thenReturn(() -> "sender");
        ChatMessage lastChatMessage = ChatMessage.builder()
                .senderUsername("sender")
                .receiverUsername("receiver")
                .message("Hello")
                .timestamp(LocalDateTime.now())
                .build();
        when(chatRepo.findFirstBySenderUsernameOrderByTimestampDesc("sender")).thenReturn(lastChatMessage);

        chatServiceSpy.afterConnectionEstablished(session);

        verify(chatRepo, times(1)).findFirstBySenderUsernameOrderByTimestampDesc("sender");
        verify(chatServiceSpy, times(1)).sendPreviousMessages(session, "sender", "receiver");
        }
    @Test
    void afterConnectionClosed_shouldRemoveSessionFromSessions() throws Exception {

        WebSocketSession session = mock(WebSocketSession.class);
        CloseStatus status = new CloseStatus(1000, "reason");
        chatService.getSessions().add(session);

        chatService.afterConnectionClosed(session, status);

        assertFalse(chatService.getSessions().contains(session));
    }

    @Test
    void getPreviousMessages_shouldReturnSortedListOfPreviousMessages() {

        ChatMessage message1 = ChatMessage.builder().id("1").senderUsername("sender1").receiverUsername("receiver1").message("message1").timestamp(LocalDateTime.of(2020, 1, 1, 0, 0, 0)).build();
        ChatMessage message2 = ChatMessage.builder().id("2").senderUsername("receiver1").receiverUsername("sender1").message("message2").timestamp(LocalDateTime.of(2021, 1, 2, 0, 9, 0)).build();
        List<ChatMessage> messageList = new ArrayList<>(Arrays.asList(message1, message2));
        when(chatRepo.findAllBySenderUsernameAndReceiverUsername(anyString(), anyString())).thenReturn(messageList);

        List<ChatMessage> previousMessages = chatService.getPreviousMessages("sender1", "receiver1");

        assertEquals(4, previousMessages.size());
        assertTrue(previousMessages.get(0).getTimestamp().isBefore(previousMessages.get(previousMessages.size()-1).getTimestamp()));
        verify(chatRepo).findAllBySenderUsernameAndReceiverUsername("sender1", "receiver1");
        verify(chatRepo).findAllBySenderUsernameAndReceiverUsername("receiver1", "sender1");
    }
}
