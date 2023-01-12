package de.neuefische.backend.security;

import de.neuefische.backend.models.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoUserRepo extends MongoRepository<MongoUser, String> {
    Optional<MongoUser> finByUsername(String username);
}
