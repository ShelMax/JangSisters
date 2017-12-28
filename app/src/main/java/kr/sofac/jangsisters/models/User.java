package kr.sofac.jangsisters.models;

public class User {

    private int id;
    private String email;
    private String name;
    private String visible;
    private String userImage;
    private int balance;

    public User(int id, String email, String name, String visible) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.visible = visible;
        this.userImage = "http://i.dailymail.co.uk/i/pix/2017/04/20/13/3F6B966D00000578-4428630-image-m-80_1492690622006.jpg";
        this.balance = 5000;
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
                ", name='" + name + '\'' +
                ", visible='" + visible + '\'' +
                '}';
    }

    public int getBalance() {
        return balance;
    }

    public String getUserImage() {
        return userImage;
    }

}
