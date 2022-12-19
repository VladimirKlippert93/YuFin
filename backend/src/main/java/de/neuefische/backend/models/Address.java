package de.neuefische.backend.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Address")
public record Address(
        String id,
        String street,
        String streetNumber,
        String city,
        int zip,
        String country
) {
}
