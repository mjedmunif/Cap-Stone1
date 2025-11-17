package org.example.capstone1.Service;

import lombok.Getter;
import org.example.capstone1.Model.Merchant;
import org.example.capstone1.Model.MerchantStock;
import org.example.capstone1.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantStockSystem {

    private final MerchantSystem merchantSystem;
    private final ProductSystem productSystem;
    @Getter
    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    public MerchantStockSystem(MerchantSystem merchantSystem, ProductSystem productSystem) {
        this.merchantSystem = merchantSystem;
        this.productSystem = productSystem;
    }

    //    todo check ( ProductId ) and ( MerchantID )
    public int addNewMerchantStock(MerchantStock merchantStock){
        boolean merchantFound = false;
        boolean productFound = false;
        for (Merchant m : merchantSystem.merchants) {
            if (m.getId().equalsIgnoreCase(merchantStock.getMerchantId())) {
                merchantFound = true;
                break;
            }
        }
        if (!merchantFound) {
            return 2;
        }
            for (Product p : productSystem.products) {
                if (p.getId().equalsIgnoreCase(merchantStock.getProductId())) {
                  productFound = true;
                  break;
                }
            }
            if (!productFound) {
                return 4;
            }
            merchantStocks.add(merchantStock);
            return 1;
    }

    public boolean updateMerchantStock(String id , MerchantStock merchantStock){
        for (int i = 0 ; i < merchantStocks.size() ; i++){
            if (merchantStocks.get(i).getId().equalsIgnoreCase(id)){
                merchantStocks.set(i , merchantStock);
                return true;
            }
        }
            return false;
    }

    public boolean deleteMerchantStock(String id){
        for (int i = 0 ; i < merchantStocks.size() ; i++){
            if (merchantStocks.get(i).getId().equalsIgnoreCase(id)){
            merchantStocks.remove(i);
            return true;
            }
        }
        return false;
    }

    public boolean addStocksByMerchant(String productId , String merchantId , int num) {
        for (MerchantStock m : merchantStocks) {
            if (m.getProductId().equalsIgnoreCase(productId) && m.getMerchantId().equalsIgnoreCase(merchantId)) {
                m.setStock(m.getStock() + num);
                return true;
            }
        }
        return false;
    }
}
