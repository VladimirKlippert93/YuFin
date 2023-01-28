package de.neuefische.backend.security;

import de.neuefische.backend.models.MongoUser;
import de.neuefische.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private MongoUserRepo mongoUserRepo;
    @InjectMocks
    private UserService userService;

    @Test
    void loadUserByUsernameShouldReturnUserDetails() {
        MongoUser mongoUser = new MongoUser("username", "hashedpassword", "email", null);
        when(mongoUserRepo.findByUsername("username")).thenReturn(Optional.of(mongoUser));

        UserDetails userDetails = userService.loadUserByUsername("username");

        assertEquals("username", userDetails.getUsername());
        assertEquals("hashedpassword", userDetails.getPassword());
    }

    @Test
    void loadUserByUsernameShouldThrowExceptionIfUserNotFound() {
        when(mongoUserRepo.findByUsername("username")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("username"));
    }
}