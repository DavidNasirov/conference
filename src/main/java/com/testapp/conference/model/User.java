package com.testapp.conference.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long userId;

    @NotBlank(message = "First Name cannot be null")
    @Size(min = 4, max = 255, message = "Minimum firstName length: 4 characters")
    private String firstName;

    @NotBlank(message = "Last Name cannot be null")
    @Size(min = 4, max = 255, message = "Minimum lastName length: 4 characters")
    private String lastName;

    @NotBlank(message = "Password cannot be null")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 255, message = "Minimum password length: 8 characters")
    private String password;

    @Column(unique = true)
    @Email
    private String email;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    List<UserRole> userRoles;

}
