package kr.sofac.jangsisters.models;

public interface VideoCallback {
    void delete(int videoPosition, int containerPosition);
    void addVideo(int videoPosition, int containerPosition);
}
