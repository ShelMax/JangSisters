package kr.sofac.jangsisters.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.models.Post;
import kr.sofac.jangsisters.models.SimpleListCallback;

/**
 * Created by Sasha on 21.12.2017.
 */

public class GridViewPostAdapter extends RecyclerView.Adapter<GridViewPostAdapter.ViewHolder> {

    private List<Post> posts;
    private SimpleListCallback callback;

    public GridViewPostAdapter(List<Post> posts, SimpleListCallback callback) {
        this.posts = posts;
        this.callback = callback;
    }

    @Override
    public GridViewPostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_post, parent, false);
        return new GridViewPostAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GridViewPostAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView).load(posts.get(position * 2).getImageURL()).into(holder.first);
        holder.firstTitle.setText(posts.get(position * 2).getTitle());
        holder.itemView.setOnClickListener(view ->
                callback.itemClick(position * 2));
        if(position * 2 != posts.size() - 1){
            Glide.with(holder.itemView).load(posts.get(position * 2 + 1).getImageURL()).into(holder.second);
            holder.secondTitle.setText(posts.get(position * 2 + 1).getTitle());
            holder.itemView.setOnClickListener(view -> callback.itemClick(position * 2 + 1));
        }
        else{
            holder.secondLinear.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return (int)Math.round(((double)posts.size() / 2));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_grid_first) ImageView first;
        @BindView(R.id.item_grid_second) ImageView second;
        @BindView(R.id.item_grid_title_first) TextView firstTitle;
        @BindView(R.id.item_grid_title_second) TextView secondTitle;
        @BindView(R.id.item_grid_second_linear) LinearLayout secondLinear;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
