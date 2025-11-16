package org.example.capstone1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1.Api.ApiResponse;
import org.example.capstone1.Model.Product;
import org.example.capstone1.Service.ProductSystem;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductSystem productSystem;

    @GetMapping("/get")
    public ResponseEntity<?> getProducts(){
        if (productSystem.getProducts().isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse("there is no products"));
        }
        return ResponseEntity.status(200).body(productSystem.getProducts());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewProducts(@RequestBody @Valid Product product , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean checked = productSystem.addProduct(product);
        if (checked){
            return ResponseEntity.status(200).body(new ApiResponse("added product successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("invalid category id"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id , @RequestBody @Valid Product product , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdated = productSystem.updateProduct(id, product);
        if (isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        boolean deleted = productSystem.deleteProduct(id);
        if (deleted){
            return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("updated successfully"));
    }
}
