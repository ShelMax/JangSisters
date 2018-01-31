package kr.sofac.jangsisters.views.adapters;

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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> categories;
    private Boolean isDetailView;
    private CategoryAdapter.CallbackFilter callbackFilter;

    public CategoryAdapter(List<Category> categories, Boolean isDetailView) {
        this.categories = categories;
        this.isDetailView = isDetailView;
    }

    public CategoryAdapter(List<Category> categories, Boolean isDetailView, CallbackFilter callbackFilter) {
        this.categories = categories;
        this.isDetailView = isDetailView;
        this.callbackFilter = callbackFilter;
    }

    public interface CallbackFilter {
        void onClickFilterListener(View view, int position);
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (isDetailView) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_detail, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        }
        return new CategoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        holder.setModel(categories.get(position), position);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_category_image) ImageView image;
        @BindView(R.id.item_category_name) TextView name;

        ViewHolder(View itemView) {
            super(itemView);
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
        }
    }

}
