package com.example.user.services;

import com.example.user.entities.User;
import com.example.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <description>
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
        return userRepository.findById(id).orElseThrow(RuntimeException::new); // () -> new RuntimeException();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void update(String id, User user) {
        if(!userRepository.existsById(id)) {
            throw new RuntimeException();
        }
        user.setId(id);
        userRepository.save(user);
    }

    public void delete(String id) {
        if(!userRepository.existsById(id)) {
            throw new RuntimeException();
        }
        userRepository.deleteById(id);
    }
}
