package de.neuefische.backend.security;

import de.neuefische.backend.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoUserRepo extends MongoRepository<User, String> {
}
