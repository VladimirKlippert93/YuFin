package de.neuefische.backend.repository;

import de.neuefische.backend.models.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepo extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findAllBySenderUsernameOrReceiverUsername(String senderUsername, String receiverUsername);

    ChatMessage findFirstBySenderUsernameOrderByTimestampDesc(String senderUsername);
}