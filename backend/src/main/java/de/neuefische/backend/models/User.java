package de.neuefische.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("User")
public record User(
        @Id
        String id,
        String name,
        String password,
        String email,
        List<Offer> offerList
) {
}
