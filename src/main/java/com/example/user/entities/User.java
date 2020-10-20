package com.example.user.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;

/**
 * User entity
 *
 * @author Ahmed Shakir
 * @version 1.0
 * @since 2020-10-15
 */
@Data // setters, getters, toString, equals, hashCode, RequiredArgsConstructor
@Builder
public class User {
    @Id
    private String id;
    //@Field("fname")
    private String firstname;
    private String lastname;
    private LocalDate birthday;
    private String mail;
    private String phone;
    @Indexed(unique = true)
    private String username;
    private String password;

}
