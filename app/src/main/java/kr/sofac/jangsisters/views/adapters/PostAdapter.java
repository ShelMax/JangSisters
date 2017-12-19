package kr.sofac.jangsisters.views.adapters;

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
import kr.sofac.jangsisters.models.Post;
import kr.sofac.jangsisters.models.PostCallback;

/**
 * Created by Maxim on 29.11.2017.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> items;
    private PostCallback callback;

    public PostAdapter(List<Post> items, PostCallback callback) {
        this.items = items;
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(holder.itemView).load(items.get(position).getImageURL()).into(holder.image);
        Glide.with(holder.itemView).load(items.get(position).getAuthorURL())
                .apply(RequestOptions.circleCropTransform()).into(holder.authorImage);
        holder.author.setText(items.get(position).getAuthor());
        holder.title.setText(items.get(position).getTitle());
        holder.date.setText(items.get(position).getDate());
        holder.comments.setText(String.valueOf(items.get(position).getComments()));
        holder.likes.setText(String.valueOf(items.get(position).getLikes()));
        holder.description.setText(items.get(position).getDescription());
        holder.itemView.setOnClickListener(view -> callback.postClick(position));
        holder.ingredients.setOnClickListener(view -> callback.ingredientsClick(position));
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

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
