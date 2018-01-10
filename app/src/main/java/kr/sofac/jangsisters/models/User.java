package kr.sofac.jangsisters.models;

public class User {

    private int id;
    private String email;
    private String name;
    private Integer visible;
    private String avatar;
    private int balance;

    public User(int id, String email, String name, int visible) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.visible = visible;
    }

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
