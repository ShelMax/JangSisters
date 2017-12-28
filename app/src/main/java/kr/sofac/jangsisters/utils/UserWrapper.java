package kr.sofac.jangsisters.utils;

import java.util.ArrayList;
import java.util.List;

import kr.sofac.jangsisters.models.User;

public class UserWrapper {

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "qqq@aaa.com", "Sancho",
                "1"));
        users.add(new User(2, "aaa@aaa.com", "Boris",
                "1"));
        users.add(new User(3, "sss@aaa.com", "Alex",
                "1"));
        users.add(new User(4, "ddd@aaa.com", "Maria",
                "1"));
        users.add(new User(5, "bbb@aaa.com", "Steve",
                "1"));
        users.add(new User(6, "rrr@aaa.com", "Nikolas",
                "1"));
        users.add(new User(7, "vvv@aaa.com", "Xavier",
                "1"));
        return users;
    }

    public static User getUserByID(int id) {
        for(int i =0; i< getAllUsers().size();i++){
            if(getAllUsers().get(i).getId() == id)
                return getAllUsers().get(i);
        }
        return null;
    }
}
