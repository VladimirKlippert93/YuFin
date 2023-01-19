package de.neuefische.backend.controller;

import de.neuefische.backend.models.ChatMessage;
import de.neuefische.backend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat/{senderUsername}/{receiverUsername}")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        return chatService.saveMessage(message);
    }

    @MessageMapping("/getMessages/{senderUsername}/{receiverUsername}")
    @SendToUser("/queue/messages")
    public List<ChatMessage> getMessages(@DestinationVariable String senderUsername,
                                         @DestinationVariable String receiverUsername) {
        return chatService.getMessages(senderUsername, receiverUsername);
    }
}