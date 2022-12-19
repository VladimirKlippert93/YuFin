package de.neuefische.backend.repository;

import de.neuefische.backend.models.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepo extends MongoRepository<Address, String> {
}
