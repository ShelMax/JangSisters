package kr.sofac.jangsisters.network.dto;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import kr.sofac.jangsisters.models.BasePostElement;

public class AddPostDTO implements Serializable {

    private Integer customer_id;

    private String title;
    private String description;

    @SerializedName("ingredients")
    private String ownIngredients;

    @SerializedName("shop_ingredients")
    private List<Integer> shopIngredients;

    private List<Integer> categories;

    @SerializedName("body")
    private List<BasePostElement> elementsBody;

    public AddPostDTO setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
        return this;
    }

    public AddPostDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public AddPostDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public AddPostDTO setOwnIngredients(String ownIngredients) {
        this.ownIngredients = ownIngredients;
        return this;
    }

    public AddPostDTO setShopIngredients(List<Integer> shopIngredients) {
        this.shopIngredients = shopIngredients;
        return this;
    }

    public AddPostDTO setCategories(List<Integer> categories) {
        this.categories = categories;
        return this;
    }

    public AddPostDTO setElementsBody(List<BasePostElement> elementsBody) {
        this.elementsBody = elementsBody;
        return this;
    }
}
