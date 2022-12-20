package de.neuefische.backend.controller;

import de.neuefische.backend.models.Offer;
import de.neuefische.backend.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/offers")
public class OfferController {
    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public List<Offer> getAllOffers(){
        return offerService.getAllOffers();
    }
    @GetMapping("/{id}")
    public Optional<Offer> getOfferById(@PathVariable String id){
        return offerService.getOfferById(id);
    }
}
