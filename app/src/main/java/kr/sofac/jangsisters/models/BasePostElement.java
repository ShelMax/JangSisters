package kr.sofac.jangsisters.models;

import android.net.Uri;

import java.util.List;

public class BasePostElement {

    protected String text;
    protected int position;
    protected String type;
    private transient List<Uri> uris;

    BasePostElement() {
    }

    public BasePostElement(String text, int position, String type, List<Uri> uris) {
        this.text = text;
        this.position = position;
        this.type = type;
        this.uris = uris;
    }

    public List<Uri> getUris() {
        return uris;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setType(String type) {
        this.type = type;
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
}
