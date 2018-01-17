package kr.sofac.jangsisters.models;

/**
 * Created by Maxim on 17.01.2018.
 */

public class Version {

    String name;
    int value;

    public Version(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Version{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

}
