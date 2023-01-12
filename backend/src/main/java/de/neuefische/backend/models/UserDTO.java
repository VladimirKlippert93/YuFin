package de.neuefische.backend.models;

import java.util.List;

public record UserDTO(
        String name,
        String password,
        String email,
        List<Offer> offerList
) {
}
