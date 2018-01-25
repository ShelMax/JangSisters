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
import kr.sofac.jangsisters.models.IngredientsCallback;

public class IngredientsAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_INGREDIENT = 1;
    private static final int VIEW_TYPE_OWN_HEADER = 2;
    private static final int VIEW_TYPE_HEADER = 3;

    private List<Ingredient> ingredients;
    private IngredientsCallback callback;

    public IngredientsAdapter(List<Ingredient> ingredients, IngredientsCallback callback) {
        this.ingredients = ingredients;
        this.callback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_INGREDIENT) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
            return new ViewHolder(v);
        } else if (viewType == VIEW_TYPE_OWN_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient_own_header, parent, false);
            return new OwnHeaderViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient_header, parent, false);
            return new HeaderViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_OWN_HEADER)
            ((HeaderViewHolder) holder).bind();
        if (holder.getItemViewType() == VIEW_TYPE_INGREDIENT) {
            ((ViewHolder) holder).bind();
        } else {
            ((OwnHeaderViewHolder) holder).bind();
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (ingredients.get(position) == null)
            return VIEW_TYPE_OWN_HEADER;
        if (ingredients.get(position).getId() == 0)
            return VIEW_TYPE_HEADER;
        else
            return VIEW_TYPE_INGREDIENT;
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind() {
            checkBox.setVisibility(View.GONE);
            Glide.with(itemView)
                    .load("https://image.freepik.com/free-photo/food-background-food-concept-with-various-tasty-fresh-ingredients-for-cooking-italian-food-ingredients-view-from-above-with-copy-space_1220-1365.jpg")
                    .apply(RequestOptions.circleCropTransform())
                    .into(image);
            title.setText(ingredients.get(getLayoutPosition()).getName());
            close.setOnClickListener(v -> callback.ingredientClick(getLayoutPosition()));
        }
    }

    class OwnHeaderViewHolder extends RecyclerView.ViewHolder {
        OwnHeaderViewHolder(View itemView) {
            super(itemView);
        }

        public void bind() {

        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        HeaderViewHolder(View itemView) {
            super(itemView);
        }

        public void bind() {

        }
    }
}
