package de.neuefische.backend.models;

import java.util.List;

public record UserDTO(
        String username,
        String password,
        String email,
        List<Offer> offerList
) {
}
