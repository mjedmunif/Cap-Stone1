package org.example.capstone1.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.capstone1.Api.ApiResponse;
import org.example.capstone1.Model.Category;
import org.example.capstone1.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductSystem {

    private final CategorySystem categorySystem;

    @Getter
    ArrayList<Product> products = new ArrayList<>();

    public ProductSystem(CategorySystem categorySystem) {
        this.categorySystem = categorySystem;
    }

    public boolean addProduct(Product product){
        for (Category c : categorySystem.categories){
           if (c.getId().equalsIgnoreCase(product.getCategoryId())){
               products.add(product);
               return true;
           }
        }
        return false;
    }

    public boolean updateProduct(String id , Product product){
        for (int i = 0 ; i < products.size() ; i++){
            if (products.get(i).getId().equalsIgnoreCase(id)){
                products.set(i , product);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String id){
        for (int i = 0 ; i < products.size() ; i++){
            if (products.get(i).getId().equalsIgnoreCase(id)){
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Product> getSortedProducts() {
        ArrayList<Product> sorted = new ArrayList<>(products);
        for (int i = 0; i < sorted.size(); i++) {
            for (int j = i + 1; j < sorted.size(); j++) {
                Product p1 = sorted.get(i);
                Product p2 = sorted.get(j);
                if (p1.getPrice() > p2.getPrice()) {
                    sorted.set(i, p2);
                    sorted.set(j, p1);
                }
            }
        }
        return sorted;
    }
    
}