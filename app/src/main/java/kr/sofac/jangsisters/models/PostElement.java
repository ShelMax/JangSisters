package kr.sofac.jangsisters.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostElement {

    private int id;

    @SerializedName("post_id")
    private int postID;

    private String text;
    private int position;
    private String type;
    private List<File> files;

    public int getId() {
        return id;
    }

    public int getPostID() {
        return postID;
    }

    public String getText() {
        return text;
    }

    public int getPosition() {
        return position;
    }

    public String getType() {
        return type;
    }

    public List<File> getFiles() {
        return files;
    }
}
