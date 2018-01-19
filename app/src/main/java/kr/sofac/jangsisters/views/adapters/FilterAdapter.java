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

import static kr.sofac.jangsisters.config.ServersConfig.BASE_URL;
import static kr.sofac.jangsisters.config.ServersConfig.PART_POST;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {

    private List<Category> categories;

    public FilterAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public FilterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_detail, parent, false);
        return new FilterAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FilterAdapter.ViewHolder holder, int position) {
        holder.setModel(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_category_image) ImageView image;
        @BindView(R.id.item_category_name) TextView name;
        View itemView;

        ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        void setModel(Category categoryItem) {
            GlideApp.with(itemView)
                    .load(BASE_URL + PART_POST + categoryItem.getId() + ".png")
                    .override(100, 100)
                    .apply(RequestOptions.centerCropTransform()
                            .placeholder(R.drawable.category)
                            .error(R.drawable.category))
                    .into(image);
            name.setText(categoryItem.getName());
        }

    }

}
