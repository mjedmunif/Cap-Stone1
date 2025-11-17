package org.example.capstone1.Service;

import org.example.capstone1.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantSystem {

    ArrayList<Merchant> merchants = new ArrayList<>();

    public ArrayList<Merchant> getMerchants(){
        return merchants;
    }

    public void addNewMerchants(Merchant merchant){
        merchants.add(merchant);
        }

        public boolean updateMerchants(String id , Merchant merchant) {
            for (int i = 0; i < merchants.size(); i++) {
                if (merchants.get(i).getId().equalsIgnoreCase(id)) {
                    merchants.set(i, merchant);
                    return true;
                }
            }
            return false;
        }

        public boolean deleteMerchants(String id) {
            for (int i = 0; i < merchants.size(); i++) {
                if (merchants.get(i).getId().equalsIgnoreCase(id)) {
                    merchants.remove(i);
                    return true;
                }
            }
            return false;
        }

}
