package kr.sofac.jangsisters.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class SenderContainerDTO {

    @SerializedName("id")
    private Integer id;

    private Integer customer_id;
    private String password;

    @SerializedName("post_id")
    private Integer postID;

    private String body;

    private String email;
    private String googleCloudKey;
    private String name;
    private String code;
    private HashMap<Integer, Integer> filter;

    public SenderContainerDTO setID(Integer id) {
        this.id = id;
        return this;
    }

    public SenderContainerDTO setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
        return this;
    }

    public SenderContainerDTO setPassword(String password) {
        this.password = password;
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

    public SenderContainerDTO setBody(String body){
        this.body = body;
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
