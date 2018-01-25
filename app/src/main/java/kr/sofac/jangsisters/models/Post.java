package kr.sofac.jangsisters.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Post implements Serializable {

    private int id;

    @SerializedName("customer_id")
    private int authorID;

    private String title;

    @SerializedName("avatar")
    private String authorImg;

    private String description;

    private String ingredients;

    private String date;

    private List<Category> categories;

    @SerializedName("customer_name")
    private String authorName;

    @SerializedName("count_likes")
    private int likesCount;

    @SerializedName("count_comments")
    private int commentsCount;

    @SerializedName("post_image")
    private String postImage;

    @SerializedName("shop_ingredients")
    private List<Ingredient> shopIngredients;

    @SerializedName("is_likes")
    private int isLiked;

    @SerializedName("is_bookmark")
    private int isBookmarked;

    private List<PostElement> body;


    public int getId() {
        return id;
    }

    public int getAuthorID() {
        return authorID;
    }

    public String getTitle() {
        return title;
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

    public int isLiked() {
        return isLiked;
    }

    public String getAuthorImg() {
        return authorImg;
    }

    public List<PostElement> getBody() {
        return body;
    }

    public int isBookmarked() {
        return isBookmarked;
    }

    //-//

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setShopIngredients(List<Ingredient> shopIngredients) {
        this.shopIngredients = shopIngredients;
    }

    public void setBody(List<PostElement> body) {
        this.body = body;
    }
}
