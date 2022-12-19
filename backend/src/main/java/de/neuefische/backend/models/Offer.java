package de.neuefische.backend.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("Offer")
public record Offer(
        String id,
        String title,
        String price,
        List<Address> address,
        String description
) {

}
