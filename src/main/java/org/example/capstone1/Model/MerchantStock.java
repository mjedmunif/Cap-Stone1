package org.example.capstone1.Model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty
    private String id;

    @NotNull(message = "stock must be not empty")
    @Min(value = 10 , message = "have to be more than 10 at start")
    private int stock;

    @NotEmpty(message = "product id must be not empty")
    private String productId;

    @NotEmpty(message = "merchant must be not empty")
    private String merchantId;
//    todo check ( ProductId ) and ( MerchantID )
}
