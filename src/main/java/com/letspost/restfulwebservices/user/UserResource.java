package com.letspost.restfulwebservices.user;

import com.letspost.restfulwebservices.exception.UserNotFoundException;
import com.letspost.restfulwebservices.post.Post;
import com.letspost.restfulwebservices.post.PostRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {
    private UserRepository userRepository;
    private PostRepository postRepository;

    public UserResource(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("id: " + id + " Not Available");
        }
        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            throw new UserNotFoundException("id: " + id + " Not Available");
        userRepository.deleteById(id);
    }

    @PutMapping("/users/{id}")
    public void updateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        userRepository.save(user);
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrievePostsByUser(@PathVariable Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            throw new UserNotFoundException("id: " + id + " Not Available");
        return user.getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Object> createPosts(@PathVariable Integer id, @RequestBody Post post) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            throw new UserNotFoundException("id: " + id + " Not Available");
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
