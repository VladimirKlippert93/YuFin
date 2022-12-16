package de.neuefische.backend.repository;

import de.neuefische.backend.models.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepo extends MongoRepository<Offer, String> {
}
