package kr.sofac.jangsisters.models;

import com.google.gson.annotations.SerializedName;

public class User {

    private int id;
    private String email;
    private String name;
    private Integer visible;
    private String avatar;
    private int balance;

    @SerializedName("blog_name")
    private String blogName;

    @SerializedName("blog_description")
    private String blogDescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getBalance() {
        return balance;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getBlogName() {
        return blogName;
    }

    public String getBlogDescription() {
        return blogDescription;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", visible='" + visible + '\'' +
                '}';
    }


}
