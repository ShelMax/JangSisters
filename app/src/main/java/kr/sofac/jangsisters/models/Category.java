package kr.sofac.jangsisters.models;

import java.util.List;

public class Category {

    private int id;
    private int parent_id;
    private String name;
    private int position;
    private int visible;
    private List<Category> submenu;
    private boolean isSelectedCategory = false;

    public Category(int id, int parent_id, String name, int position, int visible, List<Category> submenu) {
        this.id = id;
        this.parent_id = parent_id;
        this.name = name;
        this.position = position;
        this.visible = visible;
        this.submenu = submenu;
    }

    public boolean isSelectedCategory() {
        return isSelectedCategory;
    }

    public void setSelectedCategory(boolean selectedCategory) {
        isSelectedCategory = selectedCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public List<Category> getSubmenu() {
        return submenu;
    }

    public void setSubmenu(List<Category> submenu) {
        this.submenu = submenu;
    }

    @Override
    public String toString() {
        return "\n\nCategory{" +
                "id=" + id +
                ", parent_id=" + parent_id +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", visible=" + visible +
                ", submenu=" + submenu +
                '}';
    }
}
