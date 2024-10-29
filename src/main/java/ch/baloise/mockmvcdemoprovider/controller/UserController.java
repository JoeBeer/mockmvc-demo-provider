package ch.baloise.mockmvcdemoprovider.controller;

import ch.baloise.mockmvcdemoprovider.model.User;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/users")
@Getter
public class UserController {

    // In-memory data store for simplicity
    private ConcurrentHashMap<String, User> userStore = new ConcurrentHashMap<>();

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user = userStore.get(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable String userId, @RequestBody User user) {
        if (userStore.containsKey(userId)) {
            user = user.toBuilder().id(userId).build(); // Ensure the ID matches the path variable
            userStore.put(userId, user);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // for test purposes
    public void createUser(String userId, User user) {
        userStore.put(userId, user);
    }
}
