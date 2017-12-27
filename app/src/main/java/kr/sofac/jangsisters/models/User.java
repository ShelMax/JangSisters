package kr.sofac.jangsisters.models;

import java.util.List;

/**
 * Created by Maxim on 14.12.2017.
 */

public class User {

    private int id;
    private String username;
    private int balance;
    private String userImage;

    public User(int id, String username, int balance, String userImage) {
        this.id = id;
        this.username = username;
        this.balance = balance;
        this.userImage = userImage;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getBalance() {
        return balance;
    }

    public String getUserImage() {
        return userImage;
    }
}
