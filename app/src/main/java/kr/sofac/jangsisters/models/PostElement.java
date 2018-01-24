package kr.sofac.jangsisters.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostElement extends BasePostElement {

    private int id;

    @SerializedName("post_id")
    private int postID;

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
