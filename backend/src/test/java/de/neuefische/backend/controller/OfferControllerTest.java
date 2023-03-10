package de.neuefische.backend.controller;

import de.neuefische.backend.models.Address;
import de.neuefische.backend.models.Offer;
import de.neuefische.backend.repository.OfferRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OfferControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OfferRepo offerRepo;
    @Test
    @DirtiesContext
    void expectEmptyListOnGet() throws Exception {
        mockMvc.perform(get("/api/offers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    void getOfferById() throws Exception {
        Address address = new Address(
                "3",
                "street",
                "streetnumber",
                "city",
                23,
                "country");

        Offer newOffer = new Offer(
                "1",
                "help",
                "money",
                address,
                "very good help",
                "author"
        );

        Offer result = offerRepo.save(newOffer);
        mockMvc.perform(get("/api/offers/"+result._id()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            {
                            "_id": "1",
                            "title": "help",
                            "price": "money",
                            "address": {
                            "id":"3",
                            "street":"street",
                            "streetNumber":"streetnumber",
                            "city":"city",
                            "zip":23,
                            "country":"country"
                            },
                            "description": "very good help",
                            "author": "author"
                            }\040\040\040\040\040\040\040\040\040\040\040\040\040\040\040\040\040\040\040\040\040
                            """));
        }

    @Test
    @DirtiesContext
    void deleteOfferById() throws Exception {
        Address address = new Address(
                "3",
                "street",
                "streetnumber",
                "city",
                23,
                "country");

        Offer offer = new Offer(
                "1",
                "title",
                "price",
                address,
                "description",
                "author");

        mockMvc.perform(delete("/api/offers/" + offer._id()).with(csrf()))
                .andExpect(status().isOk());
    }
}