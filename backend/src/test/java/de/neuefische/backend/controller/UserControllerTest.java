package de.neuefische.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.backend.models.MongoUser;
import de.neuefische.backend.security.MongoUserRepo;
import de.neuefische.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    MongoUserRepo mongoUserRepo;

    @MockBean
    private UserService userService;
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DirtiesContext
    @WithMockUser(username="Vladi",password = "vladimir")
    void helloMeWhenLoggedInExpectStatusOk() throws Exception {
        mockMvc.perform(get("/api/users/me")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void login() throws Exception {
        mockMvc.perform(post("/api/users/login").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "user",
                                    "password": "123"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("user"));
    }

    @Test
    @DirtiesContext
    @WithMockUser("vladi")
    void logout() throws Exception {
        mockMvc.perform(post("/api/users/logout").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("anonymousUser"));
    }

    @Test
    @DirtiesContext
    void saveUser() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "user",
                                    "password": "123",
                                    "email": "ab@212"
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void getAllUsersShouldReturnListOfUsers() throws Exception {
        MongoUser user1 = new MongoUser("user1", "password", "email@email.com", new ArrayList<>());
        MongoUser user2 = new MongoUser("user2", "password", "email2@email.com", new ArrayList<>());
        List<MongoUser> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        MvcResult result = mockMvc.perform(get("/api/users/all"))
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = result.getResponse().getContentAsString();
        List<MongoUser> resultUsers = Arrays.asList(new ObjectMapper().readValue(resultAsString, MongoUser[].class));
        assertEquals(users, resultUsers);
    }

    @Test
    @DirtiesContext
    void addOffer() throws Exception {

        String offerJson = """
                {
                "id": "1",
                "title": "title",
                "price": "price",
                "address": {
                "id": "3",
                "street": "street",
                "streetnumber": "streetnumber",
                "city": "city",
                "zip": 23,
                "country": "country"
                },
                "description": "description",
                "author": "author"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/offers/addoffer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(offerJson)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

}