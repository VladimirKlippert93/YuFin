package de.neuefische.backend.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Offer")
public record Offer(
        String title,
        String price,
        Address address,
        String description,
        String author
) {

}
