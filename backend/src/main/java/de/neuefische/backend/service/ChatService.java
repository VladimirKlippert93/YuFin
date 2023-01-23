package de.neuefische.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.backend.models.ChatMessage;
import de.neuefische.backend.repository.ChatRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.CloseStatus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ChatService extends TextWebSocketHandler{

    private final ChatRepo chatRepo;
    private final Set<WebSocketSession> sessions = new HashSet<>();

    private final ObjectMapper objectMapper;
    public ChatService(ChatRepo chatRepo, ObjectMapper objectMapper) {
        this.chatRepo = chatRepo;
        this.objectMapper = objectMapper;
    }

    public ChatMessage saveMessage(ChatMessage message) {
        return chatRepo.save(message);
    }

    public void sendPreviousMessages(WebSocketSession session, String senderUsername, String receiverUsername) {
        List<ChatMessage> messages = chatRepo.findAllBySenderUsernameAndReceiverUsername(senderUsername, receiverUsername);
        messages.addAll(chatRepo.findAllBySenderUsernameAndReceiverUsername(receiverUsername, senderUsername));
        messages.sort(Comparator.comparing(ChatMessage::getTimestamp));
        for (ChatMessage message : messages) {
            try {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessions.add(session);

            String senderUsername = Objects.requireNonNull(session.getPrincipal()).getName();
            ChatMessage lastChatMessage = chatRepo.findFirstBySenderUsernameOrderByTimestampDesc(senderUsername);
            if (lastChatMessage != null) {
                sendPreviousMessages(session, senderUsername, lastChatMessage.getReceiverUsername());
            }

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);

        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        chatMessage.setSenderUsername(Objects.requireNonNull(session.getPrincipal()).getName());
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessage.setId(UUID.randomUUID().toString());

        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(chatMessage));

        for (WebSocketSession sessionFromSessions : sessions) {
            if (sessionFromSessions.getPrincipal() != null && Objects.requireNonNull(sessionFromSessions.getPrincipal()).getName().equals(chatMessage.getReceiverUsername())) {
                    try {
                        sessionFromSessions.sendMessage(textMessage);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
        session.sendMessage(textMessage);
        saveMessage(chatMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessions.remove(session);
    }
}
