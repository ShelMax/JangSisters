package kr.sofac.jangsisters.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kr.sofac.jangsisters.models.Category;
import kr.sofac.jangsisters.models.Ingredient;
import kr.sofac.jangsisters.models.Post;

/**
 * Created by Sasha on 19.12.2017.
 */

public class PostWrapper {

    private static List<Post> posts = fillPosts();

    private static List<Post> fillPosts() {
        List<Post> posts = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        categories.add(new Category());
        categories.add(new Category());
        categories.add(new Category());
        categories.add(new Category());
        categories.add(new Category());
        categories.add(new Category());
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Rice"));
        ingredients.add(new Ingredient("Meat"));
        ingredients.add(new Ingredient("Water"));
        ingredients.add(new Ingredient("Something"));
        ingredients.add(new Ingredient("Roasted bacon"));
        posts.add(new Post(1,"https://18008579627329362eda2218-rg2mjh9f0tf5llf.netdna-ssl.com/wp-content/uploads/2017/05/keto-meal-chicken-avocado-300x200.jpg",
                "http://i.dailymail.co.uk/i/pix/2017/04/20/13/3F6B966D00000578-4428630-image-m-80_1492690622006.jpg",
                "Mark", 2,15, "10 october 2018","How to eat hamburger", "Spoke as as other again ye. Hard on to roof he drew. So sell side ye in mr evil. Longer waited mr of nature seemed. Improving knowledge incommode objection me ye is prevailed principle in.",
                categories, ingredients));
        posts.add(new Post(2,"http://sp00kje.nl/wp-content/uploads/2017/07/585be1aa1600002400bdf2a6.jpeg",
                "http://i.dailymail.co.uk/i/pix/2017/04/20/13/3F6B966D00000578-4428630-image-m-80_1492690622006.jpg",
                "Mark1", 22,151,"11 october 2018", "How to eat sushi", "Spoke as as other again ye. Hard on to roof he drew. So sell side ye in mr evil. Longer waited mr of nature seemed. Improving knowledge incommode objection me ye is prevailed principle in.",
                categories, ingredients));
        posts.add(new Post(3,"https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/1200px-Good_Food_Display_-_NCI_Visuals_Online.jpg",
                "http://i.dailymail.co.uk/i/pix/2017/04/20/13/3F6B966D00000578-4428630-image-m-80_1492690622006.jpg",
                "Mark2", 21,152, "12 october 2018","How to eat pasta", "Blablabla fgf gfgjfkgjfkg fjg kfjg fkgj fjggjkfgjkfgjkdfjgksd fgjdkfgjsdklfgjklsdfjgkls",
                categories, ingredients));

        posts.add(new Post(4, "https://www.thelocal.it/userdata/images/article/69523836b0191608c41d640feead8da2be5462038d3409e1e3900fad039c7fc8.jpg",
                "http://i.dailymail.co.uk/i/pix/2017/04/20/13/3F6B966D00000578-4428630-image-m-80_1492690622006.jpg",
                "Sanya", 21, 177, "11 October 2011", "Sushi are the best",
                "Some description", categories, ingredients));

        posts.add(new Post(5, "https://www.refrigeratedfrozenfood.com/ext/resources/NEW_RD_Website/DefaultImages/default-pasta.jpg",
                "http://i.dailymail.co.uk/i/pix/2017/04/20/13/3F6B966D00000578-4428630-image-m-80_1492690622006.jpg",
                "Sanya", 21, 177, "11 October 2011", "Sushi are the best",
                "Some description", categories, ingredients));
        return posts;
    }

    public static Post getRandomPost(){
        return posts.get(new Random().nextInt(posts.size()));
    }

    public static Post getPostById(int id){
        for(int i=0;i<posts.size();i++){
            if(posts.get(i).getId() == id)
                return posts.get(i);
        }
        return null;
    }

    public static List<Post> getAllPosts(){
        return posts;
    }
}
