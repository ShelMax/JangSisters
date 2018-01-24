package kr.sofac.jangsisters.views.adapters;

import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.models.BasePostElement;
import kr.sofac.jangsisters.models.ImageCallback;
import kr.sofac.jangsisters.models.ImageContainerCallback;
import kr.sofac.jangsisters.models.SimpleListCallback;

public class AddPostAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_TEXT = 1;
    private static final int VIEW_TYPE_IMAGE = 2;
    private static final int VIEW_TYPE_VIDEO = 3;
    private ImageContainerCallback imageContainerCallback;
    private ImageCallback imageCallback;
    private List<EditText> editTexts = new ArrayList<>();

    private SimpleListCallback textCallback;

    private List<BasePostElement> elements;

    public AddPostAdapter(List<BasePostElement> elements, SimpleListCallback textCallback,
                          ImageCallback imageCallback, ImageContainerCallback imageContainerCallback) {
        this.elements = elements;
        this.textCallback = textCallback;
        this.imageCallback = imageCallback;
        this.imageContainerCallback = imageContainerCallback;
    }

    public void saveItems() {
        int j = 0;
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getType().equals("text")) {
                elements.get(i).setText(editTexts.get(j).getText().toString());
                j++;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_TEXT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_post_text, parent, false);
                return new TextHolder(view);
            case VIEW_TYPE_IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_post_image, parent, false);
                return new ImageHolder(view);
            case VIEW_TYPE_VIDEO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_post_video, parent, false);
                return new VideoHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_TEXT:
                ((TextHolder) holder).bind(elements.get(position));
                break;
            case VIEW_TYPE_IMAGE:
                ((ImageHolder) holder).bind(elements.get(position));
                break;
            case VIEW_TYPE_VIDEO:
                ((VideoHolder) holder).bind(elements.get(position));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (elements.get(position).getType().equals("text")) {
            return VIEW_TYPE_TEXT;
        } else if (elements.get(position).getType().equals("image")) {
            return VIEW_TYPE_IMAGE;
        }
        return VIEW_TYPE_VIDEO;
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    class TextHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text)
        EditText text;
        @BindView(R.id.close)
        ImageView close;

        TextHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(BasePostElement element) {
            editTexts.add(text);
            close.setOnClickListener(v -> {
                editTexts.remove(text);
                textCallback.itemClick(getLayoutPosition());
            });
        }
    }

    class ImageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_recycler)
        RecyclerView recyclerView;
        @BindView(R.id.close)
        ImageView delete;
        @BindView(R.id.add_image)
        ImageView addImage;

        ImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(BasePostElement element) {
            recyclerView.setAdapter(new ImageAdapter(element.getUris()));
            LinearLayoutManager manager = new LinearLayoutManager(itemView.getContext());
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(manager);
            delete.setOnClickListener(v -> imageContainerCallback.delete(getLayoutPosition()));
            addImage.setOnClickListener(v -> imageContainerCallback.addImage(getLayoutPosition()));
        }

        class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

            private List<Uri> uris;

            ImageAdapter(List<Uri> uris) {
                this.uris = uris;
            }

            @Override
            public ImageAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_post_image_item, parent, false);
                return new ImageViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ImageAdapter.ImageViewHolder holder, int position) {
                Glide.with(itemView)
                        .load(uris.get(position))
                        .apply(RequestOptions.centerCropTransform())
                        .into(holder.image);
            }

            @Override
            public int getItemCount() {
                return uris.size();
            }

            class ImageViewHolder extends RecyclerView.ViewHolder {

                @BindView(R.id.image)
                ImageView image;
                @BindView(R.id.close)
                ImageView delete;
                @BindView(R.id.add_image)
                ImageView addImage;

                ImageViewHolder(View itemView) {
                    super(itemView);
                    ButterKnife.bind(this, itemView);
                    delete.setOnClickListener(v -> imageCallback.delete(getLayoutPosition(), ImageHolder.this.getLayoutPosition()));
                    addImage.setOnClickListener(v -> imageCallback.addImage(getLayoutPosition(), ImageHolder.this.getLayoutPosition()));
                }
            }
        }
    }

    class VideoHolder extends RecyclerView.ViewHolder {


        VideoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(BasePostElement element) {

        }
    }
}
