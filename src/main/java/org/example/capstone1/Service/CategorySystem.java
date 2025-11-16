package org.example.capstone1.Service;

import org.example.capstone1.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategorySystem {

    ArrayList<Category> categories = new ArrayList<>();

    public ArrayList<Category> getCategories(){
        return categories;
    }

    public void addCategory(Category category){
        categories.add(category);
    }

    public boolean updateCategory(String id , Category category){
        for (int i = 0 ; i < categories.size() ; i++){
            if (categories.get(i).getId().equalsIgnoreCase(id)){
                categories.set(i ,category);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCategory(String id){
        for (int i = 0 ; i < categories.size() ; i++){
            if (categories.get(i).getId().equalsIgnoreCase(id)){
                categories.remove(i);
                return true;
            }
        }
        return false;
    }
}
