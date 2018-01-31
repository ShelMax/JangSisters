package kr.sofac.jangsisters.models.callback;

public interface AddPostVideoCallback {

    void delete(int videoPosition, int containerPosition);
    void addVideo(int videoPosition, int containerPosition);

}
