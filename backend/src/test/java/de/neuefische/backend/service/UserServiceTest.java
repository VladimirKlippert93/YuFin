package de.neuefische.backend.service;

import de.neuefische.backend.models.MongoUser;
import de.neuefische.backend.models.UserDTO;
import de.neuefische.backend.security.MongoUserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void loadUserByUsernameShouldThrowExceptionIfUserNotFound() {
        when(mongoUserRepo.findByUsername("username")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("username"));
    }

    @Test
    void loadUserByUsernameShouldReturnUserDetails() {
        MongoUser mongoUser = new MongoUser("username", "hashedpassword", "email", null);
        when(mongoUserRepo.findByUsername("username")).thenReturn(Optional.of(mongoUser));

        UserDetails userDetails = userService.loadUserByUsername("username");

        assertEquals("username", userDetails.getUsername());
        assertEquals("hashedpassword", userDetails.getPassword());
    }

    @Test
    void getAllUsersShouldReturnAllUsers() {
        MongoUser user1 = new MongoUser("username1", "hashedpassword1", "email1", null);
        MongoUser user2 = new MongoUser("username2", "hashedpassword2", "email2", null);
        when(mongoUserRepo.findAll()).thenReturn(List.of(user1, user2));

        List<MongoUser> allUsers = userService.getAllUsers();

        assertEquals(2, allUsers.size());
        assertEquals("username1", allUsers.get(0).username());
        assertEquals("hashedpassword1", allUsers.get(0).password());
        assertEquals("email1", allUsers.get(0).email());
        assertEquals("username2", allUsers.get(1).username());
        assertEquals("hashedpassword2", allUsers.get(1).password());
        assertEquals("email2", allUsers.get(1).email());
        verify(mongoUserRepo, times(1)).findAll();
    }

    @Test
    void testGetUserByLogin() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        String username = "testUser";
        MongoUser expectedUser = new MongoUser(username, "", "", Collections.emptyList());
        Optional<MongoUser> userOptional = Optional.of(expectedUser);
        when(mongoUserRepo.findByUsername(username)).thenReturn(userOptional);
        when(authentication.getName()).thenReturn(username);
        SecurityContextHolder.setContext(securityContext);

        MongoUser actualUser = userService.getUserByLogin();

        assertThat(actualUser).isEqualTo(expectedUser);
    }
}