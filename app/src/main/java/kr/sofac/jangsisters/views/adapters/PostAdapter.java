package kr.sofac.jangsisters.views.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.config.ServersConfig;
import kr.sofac.jangsisters.models.Post;
import kr.sofac.jangsisters.models.PostCallback;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> items;
    private PostCallback callback;
    private Context context;

    public PostAdapter(List<Post> items, Context context, PostCallback callback) {
        this.items = items;
        this.callback = callback;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(holder.itemView).load(ServersConfig.BASE_URL + ServersConfig.PART_POST_IMAGE + items.get(position).getPostImage())
                .apply(new RequestOptions().placeholder(R.drawable.background))
                .into(holder.image);
        Glide.with(holder.itemView).load(ServersConfig.BASE_URL + ServersConfig.PART_AVATAR + items.get(position).getAuthorImg())
                .apply(RequestOptions.circleCropTransform())
                .apply(new RequestOptions().placeholder(R.drawable.avatar_holder))
                .into(holder.authorImage);

        holder.author.setText(items.get(position).getAuthorName());
        holder.title.setText(items.get(position).getTitle());
        holder.date.setText(items.get(position).getDate());
        holder.comments.setText(String.valueOf(items.get(position).getCommentsCount()));
        holder.likes.setText(String.valueOf(items.get(position).getLikesCount()));
        holder.description.setText(items.get(position).getDescription());

        holder.itemView.setOnClickListener(view -> callback.postClick(position));
        holder.ingredients.setOnClickListener(view -> callback.ingredientsClick(position));
        holder.authorImage.setOnClickListener(view -> callback.userClick(position));

        holder.categories.setAdapter(new CategoryAdapter(items.get(position).getCategories()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.categories.setLayoutManager(layoutManager);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_post_image) ImageView image;
        @BindView(R.id.item_post_author_image) ImageView authorImage;
        @BindView(R.id.item_post_author) TextView author;
        @BindView(R.id.item_post_title) TextView title;
        @BindView(R.id.item_post_date) TextView date;
        @BindView(R.id.item_post_comments) TextView comments;
        @BindView(R.id.item_post_likes) TextView likes;
        @BindView(R.id.item_post_description) TextView description;
        @BindView(R.id.item_post_ingredients) ImageView ingredients;
        @BindView(R.id.item_post_categories) RecyclerView categories;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
