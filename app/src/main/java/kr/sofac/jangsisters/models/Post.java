package kr.sofac.jangsisters.models;

import java.util.List;

public class Post {

    private int id;
    private String imageURL;
    private String authorURL;
    private String author;
    private int likes;
    private int comments;
    private String date;
    private String title;
    private String description;
    private List<Category> categories;

    public Post(int id, String imageURL, String authorURL, String postAuthor, int postLikes,
                int postComments, String date, String postTitle, String postDescription,
                List<Category> postCategories) {
        this.id = id;
        this.imageURL = imageURL;
        this.authorURL = authorURL;
        this.author = postAuthor;
        this.likes = postLikes;
        this.comments = postComments;
        this.date = date;
        this.title = postTitle;
        this.description = postDescription;
        this.categories = postCategories;

    }

    public String getDate() {
        return date;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getAuthor() {
        return author;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public String getAuthorURL() {
        return authorURL;
    }

    public int getId() {
        return id;
    }
}
