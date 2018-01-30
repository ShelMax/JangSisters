package kr.sofac.jangsisters.models;

import com.google.gson.annotations.SerializedName;

public class Ingredient {

    private int id;
    private String name;
    @SerializedName("shopID")
    private int shop_id;

    public Ingredient() {
    }

    public Ingredient(String name) {
        this.name = name;
        this.shop_id = -1;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getShopID() {
        return shop_id;
    }
}
