package org.example.capstone1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1.Api.ApiResponse;
import org.example.capstone1.Model.Category;
import org.example.capstone1.Service.CategorySystem;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategorySystem categorySystem;

    @GetMapping("/get")
    public ResponseEntity<?> getCategories(){
        if (categorySystem.getCategories().isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse("there is no categories"));
        }
        return ResponseEntity.status(200).body(categorySystem.getCategories());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewCategory(@RequestBody @Valid Category category , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        categorySystem.addCategory(category);
        return ResponseEntity.status(200).body(new ApiResponse("added category successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id , @RequestBody @Valid Category category , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean updateCategory = categorySystem.updateCategory(id , category);

        if (updateCategory){
            return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id){
        boolean deleted = categorySystem.deleteCategory(id);
        if (deleted){
            return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("not found"));
    }
}
