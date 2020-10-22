package com.example.user.services;

import com.example.user.entities.User;
import com.example.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User service, business logic
 *
 * @author Ahmed Shakir
 * @version 1.0
 * @since 2020-10-15
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    //private Logger log = LoggerFactory.getLogger(UserService.class);
    //@Autowired
    //private UserRepository userRepository;
    private final UserRepository userRepository;

    /* Alternativ 1 - Fås automatiskt med lombok @RequiredArgsConstructor
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    */
    /* Alternativ 2
    @Autowired
    private UserRepository userRepository;
    */

    /**
     * C - Create
     * R - Read
     * U - Update
     * D - Delete
     */

    @Cacheable(value = "userCache")
    public List<User> findAll(String name, boolean sortOnBirthday) {
        log.info("Request to find all users");
        log.warn("Fresh data...");
        //return userRepository.findAll();
        var users = userRepository.findAll();
        if(name != null) {
            users = users.stream()
                    .filter(user -> user.getFirstname().startsWith(name) ||
                            user.getLastname().startsWith(name))
                    .collect(Collectors.toList());
        }
        if(sortOnBirthday) {
            //users.sort((user1, user2) -> user1.getBirthday().compareTo(user2.getBirthday()));
            users.sort(Comparator.comparing(User::getBirthday));
        }
        return users;
    }

    @Cacheable(value = "userCache", key = "#id")
    public User findById(String id) {
        log.warn("Fresh data...");
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

    @CachePut(value = "userCache", key = "#result.id")
    public User save(User user) {
        return userRepository.save(user);
    }

    @CachePut(value = "userCache", key = "#id")
    public void update(String id, User user) {
        if(!userRepository.existsById(id)) {
            log.error(String.format("Could not find the user by id %s.", id));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, // 404 -> Not found
                    String.format("Could not find the user by id %s.", id));
        }
        user.setId(id);

        /*user.setId(id);
        user.setPhone("tel:" + user.getPhone());*/

        /*user = User.builder()
                .id(id)
                .phone("tel:" + user.getPhone())
                .build();*/

        userRepository.save(user);
    }

    @CacheEvict(value = "userCache", key = "#id")
    public void delete(String id) {
        if(!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, // 404 -> Not found
                    String.format("Could not find the user by id %s.", id));
        }
        userRepository.deleteById(id);
    }
}