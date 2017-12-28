package kr.sofac.jangsisters.models;

public class Ingredient {

    private int id;
    private String name;
    private int shop_id;

    public Ingredient(int id, String name, int shop_id) {
        this.id = id;
        this.name = name;
        this.shop_id = shop_id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getShop_id() {
        return shop_id;
    }
}
