package de.neuefische.backend.models;

import org.springframework.data.annotation.Id;

public record Address(
        @Id
        String id,
        String street,
        String streetNumber,
        String city,
        int zip,
        String country
) {
}
