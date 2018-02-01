package kr.sofac.jangsisters.models;

import com.google.gson.annotations.SerializedName;

public class Ingredient {

    private int id;
    private String name;

    @SerializedName("shop_id")
    private int shopID;

    public Ingredient() {

    }

    public Ingredient(String name) {
        this.name = name;
        this.shopID = -1;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getShopID() {
        return shopID;
    }
}
