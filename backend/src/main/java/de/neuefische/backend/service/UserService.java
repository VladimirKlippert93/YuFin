package de.neuefische.backend.service;

import de.neuefische.backend.models.MongoUser;
import de.neuefische.backend.models.UserDTO;
import de.neuefische.backend.security.MongoUserRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService implements UserDetailsService {

    private final MongoUserRepo mongoUserRepo;
    private final Argon2Service argon2Service;


    public UserService(MongoUserRepo mongoUserRepo, Argon2Service argon2Service) {
        this.mongoUserRepo = mongoUserRepo;
        this.argon2Service = argon2Service;
    }

    public MongoUser addUser (UserDTO user){
        MongoUser newMongoUser = new MongoUser(user.username(), argon2Service.encode(user.password()),user.email(), new ArrayList<>());
        mongoUserRepo.save(newMongoUser);

        return new MongoUser(
                newMongoUser.username(),
                "****",
                newMongoUser.email(),
                newMongoUser.offerList()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        MongoUser mongoUser = mongoUserRepo.findByUsername(name)
                .orElseThrow(()->new UsernameNotFoundException("User not found!"));
        return new User(mongoUser.username(), mongoUser.password(), List.of());
    }
}
