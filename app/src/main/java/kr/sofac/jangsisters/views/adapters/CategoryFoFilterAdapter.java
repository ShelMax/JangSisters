package kr.sofac.jangsisters.views.adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.models.Category;
import kr.sofac.jangsisters.models.GlideApp;

import static kr.sofac.jangsisters.config.ServerConfig.BASE_URL;
import static kr.sofac.jangsisters.config.ServerConfig.PART_CATEGORY_IMAGE;

public class CategoryFoFilterAdapter extends RecyclerView.Adapter<CategoryFoFilterAdapter.ViewHolder> {


    private List<Category> categories;
    private Boolean isDetailView;
    private CallbackFilter callbackFilter;

    public CategoryFoFilterAdapter(List<Category> categories) {
        this.categories = categories;
        this.isDetailView = isDetailView;
    }

    public CategoryFoFilterAdapter(List<Category> categories, CallbackFilter callbackFilter) {
        this.categories = categories;
        this.isDetailView = isDetailView;
        this.callbackFilter = callbackFilter;
    }

    public void setCallbackFilter(CallbackFilter callbackFilter) {
        this.callbackFilter = callbackFilter;
    }

    public interface CallbackFilter {
        void onClickFilterListener(View view, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_with_background, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setModel(categories.get(position), position);

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewCategoryIcon)
        ImageView image;
        @BindView(R.id.categoryTitle)
        TextView name;
        @BindView(R.id.cardViewCategoryItem)
        CardView cardViewCategoryItem;
        View itemView;

        ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        void setModel(Category categoryItem, int position) {
            itemView.setOnClickListener(view -> callbackFilter.onClickFilterListener(itemView, position));
            GlideApp.with(itemView)
                    .load(BASE_URL + PART_CATEGORY_IMAGE + categoryItem.getId() + ".png")
                    .override(80, 80)
                    .apply(RequestOptions.centerCropTransform()
                            .placeholder(R.drawable.category)
                            .error(R.drawable.category))
                    .into(image);
            name.setText(categoryItem.getName());
            if (categoryItem.isSelectedCategory())
                cardViewCategoryItem.setCardBackgroundColor(Color.GRAY);
            else
                cardViewCategoryItem.setCardBackgroundColor(Color.LTGRAY);

        }

    }


}
