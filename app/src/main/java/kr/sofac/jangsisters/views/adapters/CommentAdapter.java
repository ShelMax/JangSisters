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
import kr.sofac.jangsisters.config.ServerConfig;
import kr.sofac.jangsisters.models.Comment;
import kr.sofac.jangsisters.models.GlideApp;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private List<Comment> comments;

    public CommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        GlideApp.with(holder.itemView).load(ServerConfig.BASE_URL + ServerConfig.PART_AVATAR
                + comments.get(position).getUserImage())
                .override(50, 50)
                .apply(RequestOptions.circleCropTransform())
                .apply(new RequestOptions().placeholder(R.drawable.avatar_holder))
                .into(holder.userImage);
        holder.username.setText(comments.get(position).getUsername());
        holder.date.setText(comments.get(position).getDate());
        holder.comment.setText(comments.get(position).getCommentText());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.username) TextView username;
        @BindView(R.id.date) TextView date;
        @BindView(R.id.comment) TextView comment;
        @BindView(R.id.user_image) ImageView userImage;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
