package kr.sofac.jangsisters.models;

public interface ImageCallback {
    void delete(int imagePosition, int containerPosition);

    void addImage(int imagePosition, int containerPosition);
}
