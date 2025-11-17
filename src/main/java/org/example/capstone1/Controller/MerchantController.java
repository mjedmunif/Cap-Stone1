package org.example.capstone1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1.Api.ApiResponse;
import org.example.capstone1.Model.Merchant;
import org.example.capstone1.Service.MerchantSystem;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantSystem merchantSystem;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchants(){
        if (merchantSystem.getMerchants().isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse("there is no merchants"));
        }
        return ResponseEntity.status(200).body(merchantSystem.getMerchants());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchants(@RequestBody @Valid Merchant merchant , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        merchantSystem.addNewMerchants(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("added merchant successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchants(@PathVariable String id , @RequestBody @Valid Merchant merchant , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean updated = merchantSystem.updateMerchants(id , merchant);
        if (updated){
            return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Not found"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchants(@PathVariable String id){
        boolean deleted = merchantSystem.deleteMerchants(id);
        if (deleted){
            return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Not found"));
    }
}
