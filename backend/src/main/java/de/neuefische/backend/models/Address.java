package de.neuefische.backend.models;


public record Address(
        String id,
        String street,
        String streetNumber,
        String city,
        int zip,
        String country
) {
}
