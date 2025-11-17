package org.example.capstone1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1.Api.ApiResponse;
import org.example.capstone1.Model.MerchantStock;
import org.example.capstone1.Service.MerchantStockSystem;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchantStock")
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockSystem merchantStockSystem;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchantStock(){
        if (merchantStockSystem.getMerchantStocks().isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse("there is no merchants stock"));
        }
        return ResponseEntity.status(200).body(merchantStockSystem.getMerchantStocks());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchantStock(@RequestBody @Valid MerchantStock merchantStock , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        int checked = merchantStockSystem.addNewMerchantStock(merchantStock);
        if (checked == 2){
            return ResponseEntity.status(404).body(new ApiResponse("id merchant not found"));
        }
        if (checked == 4){
            return ResponseEntity.status(404).body(new ApiResponse("id product not found"));
        }
        merchantStockSystem.addNewMerchantStock(merchantStock);
        return ResponseEntity.status(200).body(new ApiResponse("added merchant stock successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMarchantStock(@PathVariable String id , @RequestBody @Valid MerchantStock merchantStock , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdate = merchantStockSystem.updateMerchantStock(id , merchantStock);
        if (isUpdate){
            return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable String id){
        boolean deleted = merchantStockSystem.deleteMerchantStock(id);
        if (deleted){
            return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("not found"));
    }


    @PostMapping("/addStock/{productId}/{merchantId}/{num}")
    public ResponseEntity<?> addStockByMerchant(@PathVariable String productId , @PathVariable String merchantId , @PathVariable int num){
        boolean checked = merchantStockSystem.addStocksByMerchant(productId , merchantId , num);
        if (checked){
            return ResponseEntity.status(200).body(new ApiResponse("added stock " + num + " successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("product id or merchant id invalid"));
    }
}
