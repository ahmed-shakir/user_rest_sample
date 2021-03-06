package com.example.user.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * User entity
 *
 * @author Ahmed Shakir
 * @version 1.0
 * @since 2020-10-15
 */
@Data // setters, getters, toString, equals, hashCode, RequiredArgsConstructor
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 8480595839967663206L;

    @Id
    private String id;
    //@Field("fname")
    @NotEmpty(message = "Firstname can not be empty")
    @Size(min = 3, max = 10, message = "Firstname length invalid")
    private String firstname;
    @NotEmpty(message = "Lastname can not be empty")
    @Size(min = 3, max = 10 , message = "Firstname length invalid")
    private String lastname;
    @Past(message = "Birthday can not be present or in the future")
    @NotNull(message = "Birthday can not be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate birthday;
    @Email(message = "E-mail address invalid")
    private String mail;
    @Pattern(regexp = "([0-9]){2,4}-([0-9]){5,8}", message = "Phone number invalid")
    private String phone;
    @Size(min = 4, max = 10, message = "Username length invalid")
    @NotBlank(message = "Username must contain a value")
    @Field("username")
    @Indexed(unique = true)
    private String username;
    @Size(min = 4, max = 10, message = "Password length invalid")
    @NotBlank(message = "Password must contain a value")
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private List<String> acl;
    @DBRef
    private Pet pet;
}
