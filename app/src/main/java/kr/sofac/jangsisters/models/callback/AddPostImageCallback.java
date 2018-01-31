package kr.sofac.jangsisters.models.callback;

public interface AddPostImageCallback {

    void delete(int imagePosition, int containerPosition);
    void addImage(int imagePosition, int containerPosition);

}
