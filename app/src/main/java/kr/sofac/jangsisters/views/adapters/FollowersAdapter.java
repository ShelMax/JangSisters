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
import kr.sofac.jangsisters.models.GlideApp;
import kr.sofac.jangsisters.models.SimpleListCallback;
import kr.sofac.jangsisters.models.User;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.ViewHolder> {

    private List<User> followers;
    private SimpleListCallback callback;

    public FollowersAdapter(List<User> followers, SimpleListCallback callback) {
        this.followers = followers;
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_followers, parent, false);
        return new FollowersAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GlideApp.with(holder.itemView).load(ServerConfig.BASE_URL + ServerConfig.PART_AVATAR +
                followers.get(position).getAvatar())
                .override(100, 100)
                .apply(new RequestOptions().placeholder(R.drawable.avatar_holder))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.image);
        holder.username.setText(followers.get(position).getName());
        holder.itemView.setOnClickListener(v -> callback.itemClick(position));
    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.image) ImageView image;
        @BindView(R.id.username) TextView username;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
