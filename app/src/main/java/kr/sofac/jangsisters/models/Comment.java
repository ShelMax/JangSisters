package kr.sofac.jangsisters.models;

import com.google.gson.annotations.SerializedName;

public class Comment {

    private int id;

    @SerializedName("customer_id")
    private int userID;

    @SerializedName("post_id")
    private int postID;

    private String date;

    @SerializedName("body")
    private String commentText;

    @SerializedName("customer_name")
    private String username;

    @SerializedName("avatar")
    private String userImage;

    public int getId() {
        return id;
    }

    public int getUserID() {
        return userID;
    }

    public int getPostID() {
        return postID;
    }

    public String getDate() {
        return date;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getUsername() {
        return username;
    }

    public String getUserImage() {
        return userImage;
    }
}
