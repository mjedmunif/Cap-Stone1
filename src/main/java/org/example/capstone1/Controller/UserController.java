package org.example.capstone1.Controller;

import org.example.capstone1.Model.MerchantStock;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1.Api.ApiResponse;
import org.example.capstone1.Model.User;
import org.example.capstone1.Service.MerchantStockSystem;
import org.example.capstone1.Service.UserSystem;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserSystem userSystem;
    private final MerchantStockSystem merchantStockSystem;


    @GetMapping("/get")
    public ResponseEntity<?> getUsers(){
        if (userSystem.getUser().isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse("there is no users"));
        }
        return ResponseEntity.status(200).body(userSystem.getUser());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewUser(@RequestBody @Valid User user , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        userSystem.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("added user successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id , @RequestBody @Valid User user , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean updateUser = userSystem.updateUser(id , user);

        if (updateUser){
            return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        boolean deleted = userSystem.deleteUser(id);
        if (deleted){
            return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("not found"));
    }

    @GetMapping("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> buy (@PathVariable String userId , @PathVariable String productId , @PathVariable String merchantId){
        int checkFlag = userSystem.purchased(userId, productId, merchantId);

        if (checkFlag == 2){
            return ResponseEntity.status(404).body(new ApiResponse("product not found"));
        }
        if (checkFlag == 4){
            return ResponseEntity.status(404).body(new ApiResponse("merchant not found"));
        }
        if (checkFlag == 6){
            return ResponseEntity.status(404).body(new ApiResponse("user not found"));
        }
        if (checkFlag == 8){
            return ResponseEntity.status(404).body(new ApiResponse("not relation between product and merchant"));
        }
        if (checkFlag == 10){
            return ResponseEntity.status(404).body(new ApiResponse("sold out"));
        }
        if (checkFlag == 12){
            return ResponseEntity.status(404).body(new ApiResponse("your balance less than cost the product"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("done"));
    }

    @PostMapping("/return/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> returnProduct(@PathVariable String userId, @PathVariable String productId, @PathVariable String merchantId, @RequestBody String reason) {

        int response = userSystem.returnProduct(userId, productId, merchantId, reason);
        if (response == 1) {
            return ResponseEntity.status(400).body(new ApiResponse("not accepted"));
        }
        if (response == 2){
            return ResponseEntity.status(400).body(new ApiResponse("user not found"));
        }
        if (response == 3){
            return ResponseEntity.status(400).body(new ApiResponse("product not found"));
        }
        if (response == 4){
            return ResponseEntity.status(400).body(new ApiResponse("no relation between product and merchant"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("accepted"));
    }


    @GetMapping("/substitute/{productId}/{merchantId}")
    public ResponseEntity<?> getSubstitutes(@PathVariable String productId, @PathVariable String merchantId) {

        ApiResponse response = userSystem.checkSubstitute(productId, merchantId);

        if (response.getMessage().equals("no substitute products found")) {
            return ResponseEntity.status(404).body(response);
        }

        return ResponseEntity.status(200).body(response);
    }
}
