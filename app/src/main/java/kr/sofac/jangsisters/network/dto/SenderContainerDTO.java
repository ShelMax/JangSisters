package kr.sofac.jangsisters.network.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;

public class SenderContainerDTO implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("user_id")
    private Integer userID;

    private Integer page;

    @SerializedName("subscribe_id")
    private Integer subscribeID;

    @SerializedName("current_user")
    private Integer currentUser;

    @SerializedName("customer_id")
    private Integer customerID;

    private String password;

    @SerializedName("post_id")
    private Integer postID;

    @SerializedName("blog_name")
    private String blogName;

    private String emailKakao;

    @SerializedName("blog_description")
    private String blogDescription;

    private String email;
    private String googleCloudKey;
    private String name;

    private String authID;
    private String type;

    private String code;
    private String title;
    private String description;

    @SerializedName("body")
    private String stringBody;

    private String search;

    private HashMap<Integer, Integer> category;

    public SenderContainerDTO setBlogName(String blogName) {
        this.blogName = blogName;
        return this;
    }

    public SenderContainerDTO setAuthID(String authID) {
        this.authID = authID;
        return this;
    }

    public SenderContainerDTO setKakaoEmail(String kakaoEmail) {
        this.emailKakao = kakaoEmail;
        return this;
    }

    public SenderContainerDTO setType(String type) {
        this.type = type;
        return this;
    }

    public SenderContainerDTO setPage(Integer page) {
        this.page = page;
        return this;
    }

    public SenderContainerDTO setBlogDescription(String blogDescription) {
        this.blogDescription = blogDescription;
        return this;
    }

    public SenderContainerDTO setID(int id) {
        this.id = id;
        return this;
    }

    public SenderContainerDTO setCurrentUser(int currentUser) {
        this.currentUser = currentUser;
        return this;
    }

    public SenderContainerDTO setCustomerID(int customerID) {
        this.customerID = customerID;
        return this;
    }

    public SenderContainerDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public SenderContainerDTO setSearch(String search){
        this.search = search;
        return this;
    }

    public SenderContainerDTO setStringBody(String stringBody) {
        this.stringBody = stringBody;
        return this;
    }

    public SenderContainerDTO setPostID(int postID) {
        this.postID = postID;
        return this;
    }

    public SenderContainerDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public SenderContainerDTO setGoogleCloudKey(String googleCloudKey) {
        this.googleCloudKey = googleCloudKey;
        return this;
    }

    public SenderContainerDTO setName(String name) {
        this.name = name;
        return this;
    }

    public SenderContainerDTO setCode(String code) {
        this.code = code;
        return this;
    }

    public SenderContainerDTO setUserID(int userID) {
        this.userID = userID;
        return this;
    }

    public SenderContainerDTO setSubscribeID(int subscribeID) {
        this.subscribeID = subscribeID;
        return this;
    }

    public SenderContainerDTO setCategory(HashMap<Integer, Integer> category) {
        this.category = category;
        return this;
    }

    public SenderContainerDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public SenderContainerDTO setDescription(String description) {
        this.description = description;
        return this;
    }


    public SenderContainerDTO setCategories(HashMap<Integer, Integer> category) {
        this.category = category;
        return this;
    }

    @Override
    public String toString() {
        return "SenderContainerDTO{" +
                "password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", googleCloudKey='" + googleCloudKey + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
