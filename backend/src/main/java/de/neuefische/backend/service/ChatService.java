package de.neuefische.backend.service;

import de.neuefische.backend.models.ChatMessage;
import de.neuefische.backend.repository.ChatRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatRepo chatRepo;

    public ChatService(ChatRepo chatRepo) {
        this.chatRepo = chatRepo;
    }

    public ChatMessage saveMessage(ChatMessage message) {
        return chatRepo.save(message);
    }

    public List<ChatMessage> getMessages(String senderUsername, String receiverUsername) {
        return chatRepo.findAllBySenderUsernameAndReceiverUsername(senderUsername, receiverUsername);
    }
}
