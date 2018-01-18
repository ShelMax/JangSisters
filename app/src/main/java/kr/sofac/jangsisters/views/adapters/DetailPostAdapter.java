package kr.sofac.jangsisters.views.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.models.File;
import kr.sofac.jangsisters.models.PostElement;

public class DetailPostAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_TEXT = 1;
    private static final int VIEW_TYPE_IMAGE = 2;
    private static final int VIEW_TYPE_VIDEO = 3;

    private List<PostElement> postElements;

    public DetailPostAdapter(List<PostElement> postElements) {
        this.postElements = postElements;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_TEXT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_post_text, parent, false);
            return new TextHolder(view);
        } else if (viewType == VIEW_TYPE_IMAGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_post_image, parent, false);
            return new ImageHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_post_video, parent, false);
            return new VideoHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_TEXT) {
            ((TextHolder) holder).bind(postElements.get(position));
        } else if (holder.getItemViewType() == VIEW_TYPE_IMAGE) {
            ((ImageHolder) holder).bind(postElements.get(position));
        } else {
            ((VideoHolder) holder).bind(postElements.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (postElements.get(position).getType().equals("text")) {
            return VIEW_TYPE_TEXT;
        } else if (postElements.get(position).getType().equals("image")) {
            return VIEW_TYPE_IMAGE;
        }
        return VIEW_TYPE_VIDEO;
    }

    @Override
    public int getItemCount() {
        return postElements.size();
    }

    class VideoHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.video_recycler)
        RecyclerView videoRecycler;

        VideoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(PostElement element) {
            videoRecycler.setAdapter(new VideoAdapter(element.getFiles()));
            LinearLayoutManager manager = new LinearLayoutManager(itemView.getContext());
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            videoRecycler.setLayoutManager(manager);
        }

        class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoAdapterHolder> {

            private List<File> videos;

            VideoAdapter(List<File> videos) {
                this.videos = videos;
            }

            @Override
            public VideoAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_post_video_item, parent, false);
                return new VideoAdapterHolder(v);
            }

            @Override
            public void onBindViewHolder(VideoAdapterHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return videos.size();
            }

            class VideoAdapterHolder extends RecyclerView.ViewHolder {

                VideoAdapterHolder(View itemView) {
                    super(itemView);
                    ButterKnife.bind(this, itemView);
                }
            }
        }
    }

    class ImageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_recycler)
        RecyclerView imagesRecycler;

        ImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(PostElement element) {
            imagesRecycler.setAdapter(new ImageAdapter(element.getFiles()));
            LinearLayoutManager manager = new LinearLayoutManager(itemView.getContext());
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            imagesRecycler.setLayoutManager(manager);
        }

        class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageAdapterHolder> {

            private List<File> images;

            ImageAdapter(List<File> images) {
                this.images = images;
            }

            @Override
            public ImageAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_post_image_item, parent, false);
                return new ImageAdapterHolder(v);
            }

            @Override
            public void onBindViewHolder(ImageAdapterHolder holder, int position) {
                Glide.with(holder.itemView)
                        .load(images.get(position))
                        .into(holder.image);
            }

            @Override
            public int getItemCount() {
                return images.size();
            }

            class ImageAdapterHolder extends RecyclerView.ViewHolder {

                @BindView(R.id.image)
                ImageView image;

                ImageAdapterHolder(View itemView) {
                    super(itemView);
                    ButterKnife.bind(this, itemView);
                }
            }
        }
    }

    class TextHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text)
        TextView text;

        TextHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(PostElement element) {
            //text.setText(element.getText());
        }
    }

}
