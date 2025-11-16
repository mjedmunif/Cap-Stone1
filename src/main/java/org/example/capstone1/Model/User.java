package org.example.capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "id must be not empty")
    private String id;

    @NotEmpty(message = "username must be not empty")
    @Size(min = 5 , message = "username must be more than 5")
    private String username;

    @NotEmpty(message = "email must be not empty")
    private String email;

    @NotEmpty(message = "role must be not empty")
    @Pattern(regexp = "^admin|customer$" , message = "role must be choice between customer and admin")
    private String role;

    @NotEmpty(message = "password must be not empty")
    private String password;

}
