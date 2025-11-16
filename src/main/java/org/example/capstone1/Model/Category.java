package org.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    @NotEmpty(message = "id must be not empty")
    private String id;

    @NotEmpty(message = "name must be not empty")
    @Size(min = 3, message = "name must be more than 3 letters")
    private String name;

}
