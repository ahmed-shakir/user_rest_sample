package com.example.user.services;

import com.example.user.entities.User;
import com.example.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * User service, business logic
 *
 * @author Ahmed Shakir
 * @version 1.0
 * @since 2020-10-15
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * C - Create
     * R - Read
     * U - Update
     * D - Delete
     */

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        /* Tillvägagångssätt 1
        var userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()) {
            throw new RuntimeException();
        }
        return userOptional.get();*/

        /* Tillvägagångssätt 2
        var userOptional = userRepository.findById(id);
        if(userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new RuntimeException();*/

        /* Tillvägagångssätt 3
        return userRepository.findById(id).orElseThrow(RuntimeException::new); // () -> new RuntimeException();
        */

        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, // 404 -> Not found
                        String.format("Could not find the user by id %s.", id)));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void update(String id, User user) {
        if(!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, // 404 -> Not found
                    String.format("Could not find the user by id %s.", id));
        }
        user.setId(id);
        userRepository.save(user);
    }

    public void delete(String id) {
        if(!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, // 404 -> Not found
                    String.format("Could not find the user by id %s.", id));
        }
        userRepository.deleteById(id);
    }
}