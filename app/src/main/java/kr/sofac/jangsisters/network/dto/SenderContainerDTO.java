package kr.sofac.jangsisters.network.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class SenderContainerDTO implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("customer_id")
    private Integer customerID;
    private String password;

    @SerializedName("post_id")
    private Integer postID;

    private String email;
    private String googleCloudKey;
    private String name;
    private String code;
    private String title;
    private String description;

    @SerializedName("body")
    private String stringBody;

    private List<Integer> categories;

    private HashMap<Integer, Integer> filter;

    public SenderContainerDTO setID(Integer id) {
        this.id = id;
        return this;
    }

    public SenderContainerDTO setCustomerID(Integer customerID) {
        this.customerID = customerID;
        return this;
    }

    public SenderContainerDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public SenderContainerDTO setStringBody(String stringBody) {
        this.stringBody = stringBody;
        return this;
    }

    public SenderContainerDTO setPostID(Integer postID) {
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

    public SenderContainerDTO setFilter(HashMap<Integer, Integer> filter) {
        this.filter = filter;
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


    public SenderContainerDTO setCategories(List<Integer> categories) {
        this.categories = categories;
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
