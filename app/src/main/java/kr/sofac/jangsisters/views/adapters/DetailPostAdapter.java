package kr.sofac.jangsisters.views.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.config.ServerConfig;
import kr.sofac.jangsisters.models.Category;
import kr.sofac.jangsisters.models.File;
import kr.sofac.jangsisters.models.GlideApp;
import kr.sofac.jangsisters.models.Post;
import kr.sofac.jangsisters.models.PostElement;
import kr.sofac.jangsisters.models.callback.VideoCallback;
import kr.sofac.jangsisters.utils.BitmapUtil;

public class DetailPostAdapter extends RecyclerView.Adapter {

    //TODO сервис

    private static final int VIEW_TYPE_TEXT = 1;
    private static final int VIEW_TYPE_IMAGE = 2;
    private static final int VIEW_TYPE_VIDEO = 3;
    private static final int VIEW_TYPE_CATEGORIES = 4;

    private VideoCallback videoCallback;


    private List<PostElement> postElements;
    private List<Category> postCategories;

    public DetailPostAdapter(Post post, VideoCallback videoCallback) {
        this.postElements = post.getBody();
        this.postCategories = post.getCategories();
        this.videoCallback = videoCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_TEXT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_post_text, parent, false);
                return new TextHolder(view);
            case VIEW_TYPE_IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_post_image, parent, false);
                return new ImageHolder(view);
            case VIEW_TYPE_VIDEO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_post_video, parent, false);
                return new VideoHolder(view);
            case VIEW_TYPE_CATEGORIES:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_post_categories, parent, false);
                return new CategoriesHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_TEXT:
                ((TextHolder) holder).bind(postElements.get(position - 1));
                break;
            case VIEW_TYPE_IMAGE:
                ((ImageHolder) holder).bind(postElements.get(position - 1));
                break;
            case VIEW_TYPE_VIDEO:
                ((VideoHolder) holder).bind(postElements.get(position - 1));
                break;
            case VIEW_TYPE_CATEGORIES:
                ((CategoriesHolder) holder).bind(postCategories);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return VIEW_TYPE_CATEGORIES;

        if (postElements.get(position - 1).getType().equals("text")) {
            return VIEW_TYPE_TEXT;
        } else if (postElements.get(position - 1).getType().equals("image")) {
            return VIEW_TYPE_IMAGE;
        }
        return VIEW_TYPE_VIDEO;
    }

    @Override
    public int getItemCount() {
        return postElements.size() + 1;
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
                holder.bind(position);
            }

            @Override
            public int getItemCount() {
                return videos.size();
            }

            class VideoAdapterHolder extends RecyclerView.ViewHolder {

                @BindView(R.id.video_background) ImageView videoBackground;

                VideoAdapterHolder(View itemView) {
                    super(itemView);
                    ButterKnife.bind(this, itemView);
                }

                public void bind(int position) {
                    //TODO show animation
                    new Thread(() -> {
                        Bitmap bitmap = BitmapUtil.getVideoThumbnailByUrl(ServerConfig.BASE_URL +
                                ServerConfig.PART_VIDEO + videos.get(position).getName());
                        itemView.post(() -> GlideApp.with(itemView)
                                .load(bitmap)
                                .apply(new RequestOptions().placeholder(R.drawable.placeholder_image))
                                .apply(RequestOptions.centerCropTransform())
                                .into(videoBackground));
                    }).start();
                    itemView.setOnClickListener(v -> videoCallback.videoClick(getLayoutPosition(), position));
                }
            }
        }
    }

    class ImageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_recycler) RecyclerView imagesRecycler;

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
                GlideApp.with(holder.itemView)
                        .load(ServerConfig.BASE_URL + ServerConfig.PART_POST + images.get(position).getName())
                        .apply(new RequestOptions().placeholder(R.drawable.placeholder_image))
                        .apply(RequestOptions.centerCropTransform())
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
            text.setText(element.getText());
        }
    }

    class CategoriesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.post_detailed_categories_list)
        RecyclerView categoriesList;
        @BindView(R.id.post_detailed_category_left)
        ImageButton left;
        @BindView(R.id.post_detailed_category_right)
        ImageButton right;

        CategoriesHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(List<Category> categories) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            categoriesList.setAdapter(new CategoryAdapter(categories, true));
            categoriesList.setLayoutManager(layoutManager);

            left.setOnClickListener(v -> {
                if (layoutManager.findFirstCompletelyVisibleItemPosition() != 0)
                    layoutManager.scrollToPosition(layoutManager.findFirstCompletelyVisibleItemPosition() - 1);
            });

            right.setOnClickListener(v -> {
                if (layoutManager.findLastCompletelyVisibleItemPosition() != categories.size())
                    layoutManager.scrollToPosition(layoutManager.findLastCompletelyVisibleItemPosition() + 1);
            });
        }

    }

}
