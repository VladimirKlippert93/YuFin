package de.neuefische.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("Message")
public class ChatMessage {
    @Id
    private String id;
    private String senderUsername;
    private String receiverUsername;
    private String message;
    private LocalDateTime timestamp;


}
