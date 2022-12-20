package de.neuefische.backend.service;
import de.neuefische.backend.models.Offer;
import de.neuefische.backend.repository.OfferRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {
    private final OfferRepo offerRepo;

    public OfferService(OfferRepo offerRepo) {
        this.offerRepo = offerRepo;
    }

    public List<Offer> getAllOffers(){
        return offerRepo.findAll();
    }
    public Optional<Offer> getOfferById(String id){
        return offerRepo.findById(id);
    }
}
