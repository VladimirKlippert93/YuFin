package de.neuefische.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.backend.models.ChatMessage;
import de.neuefische.backend.repository.ChatRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.CloseStatus;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<ChatMessage> getMessages(String senderUsername, String receiverUsername) {
        return chatRepo.findAllBySenderUsernameAndReceiverUsername(senderUsername, receiverUsername);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);

        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        chatMessage.setSenderUsername(session.getPrincipal().getName());
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessage.setId("13409");

        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(chatMessage));

        for (WebSocketSession s : sessions) {
            if (s.getPrincipal() != null) {

                if (s.getPrincipal().getName().equals(chatMessage.getReceiverUsername())) {
                    try {

                        s.sendMessage(textMessage);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        session.sendMessage(textMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessions.remove(session);
    }
}
