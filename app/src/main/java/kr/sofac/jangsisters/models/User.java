package kr.sofac.jangsisters.models;

/**
 * Created by Maxim on 14.12.2017.
 */

public class User {

    private String id;
    private String email;
    private String password;
    private String name;
    private String visible;

    public User(String id, String email, String password, String name, String visible) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.visible = visible;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", visible='" + visible + '\'' +
                '}';
    }


//    private int id;
//    private String username;
//    private int balance;
//    private String userImage;
//
//    public User(int id, String username, int balance, String userImage) {
//        this.id = id;
//        this.username = username;
//        this.balance = balance;
//        this.userImage = userImage;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public int getBalance() {
//        return balance;
//    }
//
//    public String getUserImage() {
//        return userImage;
//    }

}
