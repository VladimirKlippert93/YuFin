package de.neuefische.backend.controller;

import de.neuefische.backend.models.Offer;
import de.neuefische.backend.models.OfferDTO;
import de.neuefische.backend.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Offer getOfferById(@PathVariable String id){
        return offerService.getOfferById(id);
    }
    @PostMapping("/addoffer")
    public Offer addOffer(@RequestBody OfferDTO offer){
        return offerService.saveOffer(offer);
    }
}
