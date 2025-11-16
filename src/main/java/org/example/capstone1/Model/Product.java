package org.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @NotEmpty(message = "id must be not empty")
    private String id;

    @Size(min = 3 , message = "name must be more than 3")
    @NotEmpty(message = "Name must be not empty")
    private String name;

    @NotNull(message = "price must be not empty")
    @Positive(message = "price must be positive number")
    private int price;

    @NotEmpty
    private String categoryId;

//            todo check categoryId


}
