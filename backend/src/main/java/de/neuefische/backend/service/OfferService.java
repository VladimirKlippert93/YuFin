package de.neuefische.backend.service;

import de.neuefische.backend.models.Offer;
import de.neuefische.backend.repository.AddressRepo;
import de.neuefische.backend.repository.OfferRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {
    private final OfferRepo offerRepo;

    public OfferService(OfferRepo offerRepo, AddressRepo addressRepo) {
        this.offerRepo = offerRepo;

    }

    public List<Offer> getAllOffers(){
        return offerRepo.findAll();
    }
}
