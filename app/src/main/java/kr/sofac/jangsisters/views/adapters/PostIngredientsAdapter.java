package kr.sofac.jangsisters.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import kr.sofac.jangsisters.R;

public class PostIngredientsAdapter extends BaseAdapter {

    private String[] ingredients;
    private LayoutInflater inflater;

    public PostIngredientsAdapter(String ingredients, Context context) {
        this.ingredients = ingredients.split(";");
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (ingredients[0].isEmpty())
            return 0;
        return ingredients.length;
    }

    @Override
    public Object getItem(int i) {
        return ingredients[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        convertView = inflater.inflate(R.layout.item_post_ingredient, null);
        TextView name = convertView.findViewById(R.id.item_post_ingredient_name);
        name.setText(ingredients[i]);
        return convertView;
    }
}
