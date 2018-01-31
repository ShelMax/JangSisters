package kr.sofac.jangsisters.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.models.Ingredient;
import kr.sofac.jangsisters.models.callback.IngredientsPanelCallback;

public class IngredientsPanelAdapter extends RecyclerView.Adapter<IngredientsPanelAdapter.ViewHolder> {

    private List<Ingredient> ingredients;
    private IngredientsPanelCallback callback;

    public IngredientsPanelAdapter(List<Ingredient> ingredients, IngredientsPanelCallback callback) {
        this.ingredients = ingredients;
        this.callback = callback;
    }

    @Override
    public IngredientsPanelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(IngredientsPanelAdapter.ViewHolder holder, int position) {
        holder.close.setVisibility(View.GONE);
        holder.addedFromShop.setVisibility(View.GONE);
        Glide.with(holder.itemView)
                .load("https://image.freepik.com/free-photo/food-background-food-concept-with-various-tasty-fresh-ingredients-for-cooking-italian-food-ingredients-view-from-above-with-copy-space_1220-1365.jpg")
                .apply(RequestOptions.circleCropTransform())
                .into(holder.image);
        holder.title.setText(ingredients.get(position).getName());
        holder.checkBox.setOnClickListener(v ->
                callback.ingredientClick(position, holder.checkBox.isChecked())
        );
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.text)
        TextView title;
        @BindView(R.id.checkbox)
        CheckBox checkBox;
        @BindView(R.id.close)
        ImageView close;
        @BindView(R.id.added_from_shop)
        ImageView addedFromShop;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
