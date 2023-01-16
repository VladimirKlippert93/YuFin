package de.neuefische.backend.service;

import de.neuefische.backend.models.MongoUser;
import de.neuefische.backend.models.UserDTO;
import de.neuefische.backend.security.MongoUserRepo;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    Argon2Service argon2Service = mock(Argon2Service.class);
    MongoUserRepo mongoUserRepo = mock(MongoUserRepo.class);
    UserService userService = new UserService(mongoUserRepo,argon2Service);

    @Test
    void addUser(){
        UserDTO userToAdd = new UserDTO(

                "username",
                "123",
                "email",
                Collections.emptyList()
        );
        MongoUser expected = new MongoUser(

                "username",
                "****",
                "email",
                Collections.emptyList()
        );

        when(mongoUserRepo.save(expected)).thenReturn(expected);
        when(argon2Service.encode("123")).thenReturn("****");

        MongoUser actual = userService.addUser(userToAdd);

        assertEquals(expected, actual);
        verify(mongoUserRepo).save(expected);
    }
}