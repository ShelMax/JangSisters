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

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {

    private List<Category> categories;
    private Boolean isSubFilterView;
    private CallbackFilter callbackFilter;

    public interface CallbackFilter {
        void onClickFilterListener(View view, int position);
    }


    public FilterAdapter(List<Category> categories, Boolean isSubFilterView, CallbackFilter callbackFilter) {
        this.categories = categories;
        this.isSubFilterView = isSubFilterView;
        this.callbackFilter = callbackFilter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (isSubFilterView) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_filter, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isSubFilterView) {
            holder.setModelSubFilter(categories.get(position), position);
        } else {
            holder.setModel(categories.get(position), position);
        }

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.filter_title)
        TextView filterTitle;
        @BindView(R.id.imageView2)
        ImageView imageView2;
        View itemView;

        ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        void setModel(Category categoryItem, int position) {
            filterTitle.setText(categoryItem.getName());
            itemView.setOnClickListener(view -> callbackFilter.onClickFilterListener(itemView, position));
        }

        void setModelSubFilter(Category categoryItem, int position){
            GlideApp.with(itemView)
                    .load(BASE_URL + PART_CATEGORY_IMAGE + categoryItem.getId() + ".png")
                    .override(80, 80)
                    .apply(RequestOptions.centerCropTransform()
                            .placeholder(R.drawable.arrow_left_grey)
                            .error(R.drawable.arrow_left_grey))
                    .into(imageView2);
            filterTitle.setText(categoryItem.getName());
            itemView.setOnClickListener(view -> callbackFilter.onClickFilterListener(itemView, position));
        }

    }

}
