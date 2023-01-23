package de.neuefische.backend.repository;

import de.neuefische.backend.models.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepo extends MongoRepository<ChatMessage, String> {

    ChatMessage findFirstBySenderUsernameOrderByTimestampDesc(String senderUsername);

    List<ChatMessage> findAllBySenderUsernameAndReceiverUsername(String senderUsername, String receiverUsername);
}