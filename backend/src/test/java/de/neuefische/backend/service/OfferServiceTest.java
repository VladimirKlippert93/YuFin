package de.neuefische.backend.service;

import de.neuefische.backend.models.Address;
import de.neuefische.backend.models.Offer;
import de.neuefische.backend.models.OfferDTO;
import de.neuefische.backend.repository.OfferRepo;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OfferServiceTest {

    OfferRepo offerRepo = mock(OfferRepo.class);
    OfferService offerService = new OfferService(offerRepo);

    @Test
    void getAllOffersTest() {
        //GIVEN
        List<Offer> expected = Collections.emptyList();
        //WHEN
        when(offerRepo.findAll()).thenReturn(expected);
        List<Offer> result = offerService.getAllOffers();
        //THEN
        assertEquals(expected,result);
        verify(offerRepo).findAll();
    }

    @Test
    void saveOffer() {
        //GIVEN
        Address address = new Address("3", "street", "strnumber","city",12345,"country");
        OfferDTO offer = new OfferDTO("1","title","price",address,"description","author");

        Offer expected = new Offer("1","title","price",address,"description","author");
        //WHEN
        when(offerRepo.save(expected)).thenReturn(expected);

        Offer actual = offerService.saveOffer(offer);

        //THEN
        assertEquals(actual,expected);
        verify(offerRepo).save(expected);
    }
}