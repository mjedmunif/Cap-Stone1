package org.example.capstone1.Service;


import org.example.capstone1.Api.ApiResponse;
import org.example.capstone1.Model.Merchant;
import org.example.capstone1.Model.MerchantStock;
import org.example.capstone1.Model.Product;
import org.example.capstone1.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserSystem {

    private final ProductSystem productSystem;
    private final MerchantSystem merchantSystem;
    private final MerchantStockSystem merchantStockSystem;
    ArrayList<User> users = new ArrayList<>();

    public UserSystem(ProductSystem productSystem, MerchantSystem merchantSystem, MerchantStockSystem merchantStockSystem) {
        this.productSystem = productSystem;
        this.merchantSystem = merchantSystem;
        this.merchantStockSystem = merchantStockSystem;
    }

    public ArrayList<User> getUser(){
            return users;
        }

        public void addUser(User user){
            users.add(user);
        }

        public boolean updateUser(String id , User user){
            for (int i = 0 ; i < users.size() ; i++){
                if (users.get(i).getId().equalsIgnoreCase(id)){
                    users.set(i ,user);
                    return true;
                }
            }
            return false;
        }

        public boolean deleteUser(String id){
            for (int i = 0 ; i < users.size() ; i++){
                if (users.get(i).getId().equalsIgnoreCase(id)){
                    users.remove(i);
                    return true;
                }
            }
            return false;
        }


        public int purchased(String userId , String productId , String merchantId){
                Product product = null;
                Merchant merchant = null;
                User user = null;
                MerchantStock stockRecord = null;
                for (User u : users){
                    if (u.getId().equalsIgnoreCase(userId)){
                        user = u;
                        break;
                    }
                }
                if (user == null){
                    return 6;
                }
            for (Product p : productSystem.products) {
                if (p.getId().equalsIgnoreCase(productId)) {
                    product = p;
                    break;
                }
            }
                if (product == null){
                    return 2;
                }

            for (Merchant m : merchantSystem.merchants){
                if (m.getId().equalsIgnoreCase(merchantId)){
                    merchant = m;
                    break;
                }
            }
            if (merchant == null){
                return 4;
            }

            for (MerchantStock ms : merchantStockSystem.merchantStocks){
                if (ms.getProductId().equalsIgnoreCase(productId) && ms.getMerchantId().equalsIgnoreCase(merchantId)){
                    stockRecord = ms;
                    break;
                }
            }
            if (stockRecord == null){
                return 8;
            }

            if (stockRecord.getStock() <= 0) return 10;

            if (user.getBalance() < product.getPrice()){
                return 12;
            }
            stockRecord.setStock(stockRecord.getStock() - 1);
            user.setBalance(user.getBalance() - product.getPrice());
            return 0;
        }

    public int returnProduct(String userId, String productId, String merchantId, String reason) {

        ArrayList<String> allowedReasons = new ArrayList<>();
        allowedReasons.add("damaged");
        allowedReasons.add("not working");
        allowedReasons.add("wrong color");

        if (!allowedReasons.contains(reason.toLowerCase())) {
            return 1; //not accsepted
        }

        User user = null;
        for (User u : users) {
            if (u.getId().equalsIgnoreCase(userId)) {
                user = u;
                break;
            }
        }
        if (user == null) return 2; // user not found

        Product product = null;
        for (Product p : productSystem.products) {
            if (p.getId().equalsIgnoreCase(productId)) {
                product = p;
                break;
            }
        }
        if (product == null) return 3; //product not found

        MerchantStock stockRecord = null;
        for (MerchantStock ms : merchantStockSystem.getMerchantStocks()) {
            if (ms.getProductId().equalsIgnoreCase(productId)
                    && ms.getMerchantId().equalsIgnoreCase(merchantId)) {
                stockRecord = ms;
                break;
            }
        }
        if (stockRecord == null){
            return 4;
        }

        stockRecord.setStock(stockRecord.getStock() + 1);
        user.setBalance(user.getBalance() + product.getPrice());
        return 0;
    }

    public ApiResponse checkSubstitute(String productId, String merchantId) {
        for (MerchantStock ms : merchantStockSystem.getMerchantStocks()) {
            if (ms.getProductId().equalsIgnoreCase(productId)
                    && ms.getMerchantId().equalsIgnoreCase(merchantId)) {

                if (ms.getStock() > 0) {
                    return new ApiResponse("product is available, stock: " + ms.getStock());
                }
            }
        }
        String alternatives = "";
        for (MerchantStock ms : merchantStockSystem.getMerchantStocks()) {

            if (ms.getProductId().equalsIgnoreCase(productId)
                    && !ms.getMerchantId().equalsIgnoreCase(merchantId)) {

                alternatives += "merchant: " + ms.getMerchantId() + ", stock: " + ms.getStock() + " , ";
            }
        }
        if (alternatives.isEmpty()) {
            return new ApiResponse("no substitute products found");
        }


        alternatives = alternatives.substring(0, alternatives.length() - 2);

        return new ApiResponse("alternatives: " + alternatives);
    }

}
