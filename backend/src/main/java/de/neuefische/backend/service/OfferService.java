package de.neuefische.backend.service;
import de.neuefische.backend.models.Offer;
import de.neuefische.backend.models.OfferDTO;
import de.neuefische.backend.repository.OfferRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {
    private final OfferRepo offerRepo;

    public OfferService(OfferRepo offerRepo) {
        this.offerRepo = offerRepo;
    }

    public List<Offer> getAllOffers(){
        return offerRepo.findAll();
    }
    public Offer getOfferById(String id){
        return offerRepo.findById(id).orElseThrow();
    }
    public Offer saveOffer(OfferDTO offer){
        Offer newOffer = new Offer(
                offer.id(),
                offer.title(),
                offer.price(),
                offer.address(),
                offer.description(),
                offer.author()
        );
        return offerRepo.save(newOffer);
    }
}
