package de.neuefische.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Address")
public record Address(
        @Id
        String id,
        String street,
        String streetNumber,
        String city,
        int zip,
        String country,
        String offerId
) {
}
