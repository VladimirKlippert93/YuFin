package de.neuefische.backend.security;

import de.neuefische.backend.models.MongoUser;
import de.neuefische.backend.models.UserDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoUserRepo extends MongoRepository<UserDTO, String> {
    Optional<MongoUser> findByUsername(String name);
}
