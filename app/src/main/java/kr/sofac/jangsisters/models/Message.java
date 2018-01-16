package kr.sofac.jangsisters.models;

public class Message {

    private int userID;
    private String message;
    private String time;

    public Message(int userID, String message, String time) {
        this.userID = userID;
        this.message = message;
        this.time = time;
    }

    public int getUserID() {
        return userID;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }
}
