package kr.sofac.jangsisters.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.models.Ingredient;

public class PostIngredientsAdapter extends BaseAdapter {

    private List<Ingredient> ingredients;
    private LayoutInflater inflater;

    public PostIngredientsAdapter(String ownIngredients, List<Ingredient> shopIngredients, Context context) {
        ingredients = new ArrayList<>();
        ingredients.addAll(shopIngredients);
        if(!ownIngredients.isEmpty()) {
            String[] own = ownIngredients.split(";");
            for (int i = 0; i < own.length; i++)
                ingredients.add(new Ingredient(own[i]));
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int i) {
        return ingredients.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        convertView = inflater.inflate(R.layout.item_post_ingredient, null);
        TextView name = convertView.findViewById(R.id.item_post_ingredient_name);
        name.setText(ingredients.get(i).getName());
        return convertView;
    }
}
