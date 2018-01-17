package kr.sofac.jangsisters.models;

import com.google.gson.annotations.SerializedName;

public class File {

    private int id;

    @SerializedName("item_id")
    private int itemID;

    private String name;
    private int position;

    public int getId() {
        return id;
    }

    public int getItemID() {
        return itemID;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}
