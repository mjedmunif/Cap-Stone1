package org.example.capstone1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1.Api.ApiResponse;
import org.example.capstone1.Model.User;
import org.example.capstone1.Service.UserSystem;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserSystem userSystem;


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
}
