package com.letspost.restfulwebservices.user;

import com.letspost.restfulwebservices.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {
    private UserJpaRepository repository;
    public UserResource(UserJpaRepository repository){
        this.repository = repository;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return repository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Integer id) {
        User user = repository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("id: " + id + " Not Available");
        }
        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        User savedUser = repository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    @PutMapping("/users/{id}")
    public void updateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        repository.save(user);
    }
}
