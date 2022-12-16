package de.neuefische.backend.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Offer")
public record Offer(
        String id,
        String title,
        String price,
        String description
) {

}
