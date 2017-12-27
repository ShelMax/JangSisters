package kr.sofac.jangsisters.utils;

import java.util.ArrayList;
import java.util.List;

import kr.sofac.jangsisters.models.User;

/**
 * Created by Sanch on 26.12.2017.
 */

public class UserWrapper {

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "Sancho", 1999,
                "http://i.dailymail.co.uk/i/pix/2017/04/20/13/3F6B966D00000578-4428630-image-m-80_1492690622006.jpg"));
        users.add(new User(2, "Alex", 1999,
                "https://pixabay.com/get/ea3db90e28f01c22d9584518a33219c8b66ae3d018b5124691f0c370/man-388104_1920.jpg"));
        users.add(new User(3, "Chris", 1999,
                "http://engineering.nyu.edu/files/imagecache/profile_full/pictures/picture-6274.jpg"));
        users.add(new User(4, "Jesus", 1999,
                "https://www.ctvnews.ca/polopoly_fs/1.3405570.1494360357!/httpImage/image.jpg_gen/derivatives/portrait_300/image.jpg"));
        users.add(new User(5, "Mark", 1999,
                "https://www.wilsoncenter.org/sites/default/files/styles/450x550-scale-crop/public/18457363298_3ac27e78fb_o.jpg"));
        users.add(new User(6, "Oleksandr", 1999,
                "https://pbs.twimg.com/profile_images/1717956431/BP-headshot-fb-profile-photo_400x400.jpg"));
        users.add(new User(7, "Zinaida", 1999,
                "http://cdn.playbuzz.com/cdn/8903d858-fabb-4e76-b60a-254719a7e593/44203791-c3dc-4ec8-8d0a-9542826f8f5f.jpg"));
        users.add(new User(8, "Maria", 1999,
                "https://a.scpr.org/i/51111f09226d3229ff758e306dd8265f/177312-small.jpg"));
        users.add(new User(9, "Eskobar", 1999,
                "http://i.dailymail.co.uk/i/pix/2017/04/20/13/3F6B966D00000578-4428630-image-m-80_1492690622006.jpg"));
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
