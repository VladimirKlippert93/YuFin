package de.neuefische.backend.service;

import de.neuefische.backend.models.Offer;
import de.neuefische.backend.repository.AddressRepo;
import de.neuefische.backend.repository.OfferRepo;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OfferServiceTest {

    OfferRepo offerRepo = mock(OfferRepo.class);
    private AddressRepo addressRepo;
    OfferService offerService = new OfferService(offerRepo, addressRepo);

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
}