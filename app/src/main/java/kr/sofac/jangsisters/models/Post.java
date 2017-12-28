package kr.sofac.jangsisters.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post {

    private int id;

    @SerializedName("customer_id")
    private int authorID;

    @SerializedName("name")
    private String postTitle;

    @SerializedName("body")
    private String description;
    private String ingredients;
    private String date;

    @SerializedName("customer_name")
    private String authorName;

    @SerializedName("likesCount")
    private int likesCount;

    @SerializedName("commentsCount")
    private int commentsCount;

    @SerializedName("postImage")
    private String postImage;
    private List<Category> categories;

    @SerializedName("shopIngredients")
    private List<Ingredient> shopIngredients;

    public Post(int id, int authorID, String name, String description, String ingredients, String date,
                String authorName, int likesCount, int commentsCount, String postImage,
                List<Category> categories, List<Ingredient> shopIngredients) {
        this.id = id;
        this.authorID = authorID;
        this.postTitle = name;
        this.description = description;
        this.ingredients = ingredients;
        this.date = date;
        this.authorName = authorName;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
        this.postImage = "http://192.168.88.63/data/files/post/" + postImage;
        this.categories = categories;
        this.shopIngredients = shopIngredients;
    }

    public int getId() {
        return id;
    }

    public int getAuthorID() {
        return authorID;
    }

    public String getName() {
        return postTitle;
    }

    public String getDescription() {
        return description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getDate() {
        return date;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public String getPostImage() {
        return postImage;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Ingredient> getShopIngredients() {
        return shopIngredients;
    }
}
