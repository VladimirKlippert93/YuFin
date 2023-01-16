package de.neuefische.backend.models;

public record OfferDTO(

        String id,
        String title,
        String price,
        Address address,
        String description,
        String author
) {
}
