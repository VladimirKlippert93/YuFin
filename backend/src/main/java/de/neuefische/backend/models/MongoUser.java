package de.neuefische.backend.models;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("User")
public record MongoUser(
        String username,
        String password,
        @Indexed(unique = true)
        String email,
        List<Offer> offerList
) {
}
