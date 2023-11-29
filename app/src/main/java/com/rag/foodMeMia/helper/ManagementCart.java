package com.rag.foodMeMia.helper;

import android.content.Context;
import android.widget.Toast;

import com.rag.foodMeMia.domain.FoodDomain;
import com.rag.foodMeMia.domain.FoodDomainRetrieval;
import com.rag.foodMeMia.interfaces.ChangeNumberItemsListener;

import java.util.ArrayList;
import java.util.List;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;


    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);

    }

    public void insertFood(FoodDomainRetrieval item) {
        List<FoodDomainRetrieval> listFood = getListCart();
        boolean existAlready = false;
        int n = 0;

        for (int k = 0; k < listFood.size(); k++) {
            if (listFood.get(k).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = k;
                break;
            }
        }

        if (existAlready) {
            listFood.get(n).setNumberInCart(item.getNumberInCart());

        } else {
            listFood.add(item);
        }

        tinyDB.putListObject("CardList", (ArrayList<FoodDomainRetrieval>) listFood);
        Toast.makeText(context, "Added to your cart", Toast.LENGTH_SHORT).show();

    }

    public ArrayList<FoodDomainRetrieval> getListCart() {
        return tinyDB.getListObject("CardList");
    }

    public void plusNumberFood(
            ArrayList<FoodDomainRetrieval> listFood
            , int position
            , ChangeNumberItemsListener changeNumberItemsListener) {
        listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CardList", listFood);
        changeNumberItemsListener.changed();

    }

    public void minusNumberFood(ArrayList<FoodDomainRetrieval> listFood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listFood.get(position).getNumberInCart() == 1) {
            listFood.remove(position);
        } else {
            listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart() - 1);
        }
        tinyDB.putListObject("CardList", listFood);
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee() {
        ArrayList<FoodDomainRetrieval> listFood2 = getListCart();
        double fee = 0;

        for (int i = 0; i < listFood2.size(); i++) {
            fee = fee + (listFood2.get(i).getPrice() * listFood2.get(i).getNumberInCart());

        }
        return fee;
    }
}
