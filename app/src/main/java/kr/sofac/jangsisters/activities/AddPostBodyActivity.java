package kr.sofac.jangsisters.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.sofac.jangsisters.AddPostService;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.config.KeyTransferObj;
import kr.sofac.jangsisters.models.BasePostElement;
import kr.sofac.jangsisters.models.callback.AddPostImageCallback;
import kr.sofac.jangsisters.models.callback.AddPostImageContainerCallback;
import kr.sofac.jangsisters.models.callback.AddPostVideoCallback;
import kr.sofac.jangsisters.models.callback.AddPostVideoContainerCallback;
import kr.sofac.jangsisters.network.dto.AddPostDTO;
import kr.sofac.jangsisters.views.adapters.AddPostAdapter;

public class AddPostBodyActivity extends BaseActivity {

    private static final int SELECT_PICTURE_ADD_CONTAINER = 1;
    private static final int SELECT_PICTURE_TO_CONTAINER = 2;
    private static final int SELECT_PICTURE_REPLACE_IN_CONTAINER = 3;
    private static final int SELECT_VIDEO_ADD_CONTAINER = 4;
    private static final int SELECT_VIDEO_TO_CONTAINER = 5;
    private static final int SELECT_VIDEO_REPLACE_IN_CONTAINER = 6;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recycler) RecyclerView recyclerView;

    private List<BasePostElement> elements = new ArrayList<>();
    private AddPostAdapter adapter;
    private LinearLayoutManager manager;

    private int containerPosition;
    private int contentPosition;

    private AddPostDTO postDTO;
    private AlertDialog.Builder builder;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.backward_start, R.anim.backward_finish);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            contentLoaded(data.getData(), requestCode);
        }
    }

    private void contentLoaded(Uri uri, int requestCode) {
        switch (requestCode){
            case SELECT_PICTURE_ADD_CONTAINER:
                List<Uri> imageList = new ArrayList<>();
                imageList.add(uri);
                elements.add(new BasePostElement("", elements.size(), "image", imageList));
                adapter.notifyItemInserted(elements.size());
                manager.scrollToPosition(elements.size() - 1);
                break;
            case SELECT_PICTURE_TO_CONTAINER:
                elements.get(containerPosition).getUris().add(uri);
                adapter.notifyItemChanged(containerPosition);
                break;
            case SELECT_PICTURE_REPLACE_IN_CONTAINER:
                elements.get(containerPosition).getUris().set(contentPosition, uri);
                adapter.notifyItemChanged(containerPosition);
                break;
            case SELECT_VIDEO_ADD_CONTAINER:
                List<Uri> videoList = new ArrayList<>();
                videoList.add(uri);
                elements.add(new BasePostElement("", elements.size(), "video", videoList));
                adapter.notifyItemInserted(elements.size());
                manager.scrollToPosition(elements.size() - 1);
                break;
            case SELECT_VIDEO_TO_CONTAINER:
                elements.get(containerPosition).getUris().add(uri);
                adapter.notifyItemChanged(containerPosition);
                break;
            case SELECT_VIDEO_REPLACE_IN_CONTAINER:
                elements.get(containerPosition).getUris().set(contentPosition, uri);
                adapter.notifyItemChanged(containerPosition);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_body);
        ButterKnife.bind(this);
        initToolbar();
        postDTO = getIntent().getParcelableExtra(KeyTransferObj.POST.toString());
        adapter = new AddPostAdapter(this, elements, this::deleteContainer,
                new AddPostImageCallback() {
                    @Override
                    public void delete(int imagePosition, int containerPosition) {
                        deleteImage(imagePosition, containerPosition);
                    }

                    @Override
                    public void addImage(int imagePosition, int containerPosition) {
                        replaceImageInContainer(imagePosition, containerPosition);
                    }
                },
                new AddPostImageContainerCallback() {
                    @Override
                    public void delete(int position) {
                        deleteContainer(position);
                    }

                    @Override
                    public void addImage(int position) {
                        addImageToContainer(position);
                    }

                    },
                new AddPostVideoCallback() {
                    @Override
                    public void delete(int videoPosition, int containerPosition) {
                        deleteVideo(videoPosition, containerPosition);
                    }

                    @Override
                    public void addVideo(int videoPosition, int containerPosition) {
                        replaceVideoInContainer(videoPosition, containerPosition);
                    }
                },
                new AddPostVideoContainerCallback() {
                    @Override
                    public void delete(int position) {
                        deleteContainer(position);
                    }

                    @Override
                    public void addVideo(int position) {
                        addVideoToContainer(position);
                    }
        });
        recyclerView.setAdapter(adapter);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        initDialog();
    }

    private void initDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Important")
                .setMessage("Only videos in mp4 format are supported.\n" +
                        "Max file size is 40 MB.")
                .setPositiveButton("Ok", (dialog, which) -> {
                    addVideo(SELECT_VIDEO_ADD_CONTAINER);
                }).create();
    }

    private void addVideoToContainer(int position) {
        containerPosition = position;
        addVideo(SELECT_VIDEO_TO_CONTAINER);
    }

    private void replaceVideoInContainer(int videoPosition, int containerPos) {
        contentPosition = videoPosition;
        contentPosition = containerPos;
        addVideo(SELECT_VIDEO_REPLACE_IN_CONTAINER);
    }

    private void deleteVideo(int videoPosition, int containerPosition) {
        elements.get(containerPosition).getUris().remove(videoPosition);
        adapter.notifyItemChanged(containerPosition);
    }

    private void replaceImageInContainer(int imagePosition, int containerPos) {
        contentPosition = imagePosition;
        contentPosition = containerPos;
        addVideo(SELECT_PICTURE_REPLACE_IN_CONTAINER);
    }

    private void addImageToContainer(int position) {
        containerPosition = position;
        addImage(SELECT_PICTURE_TO_CONTAINER);
    }

    private void deleteImage(int imagePosition, int containerPosition) {
        elements.get(containerPosition).getUris().remove(imagePosition);
        adapter.notifyItemChanged(containerPosition);
    }

    private void deleteContainer(int position) {
        elements.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @OnClick(R.id.add_image)
    public void addImage() {
        addImage(SELECT_PICTURE_ADD_CONTAINER);
    }

    private void addImage(int requestType) {
        Intent intent = new Intent();
        intent.setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), requestType);
    }

    @OnClick(R.id.add_text)
    public void addText() {
        elements.add(new BasePostElement("", elements.size(), "text", new ArrayList<>()));
        adapter.notifyItemInserted(elements.size());
        manager.scrollToPosition(elements.size() - 1);
    }

    @OnClick(R.id.add_video)
    public void addVideo() {
        builder.show();
    }

    //TODO isAuth поменять, где user == null

    // TODO вынести пермишны в BaseActivity

    private void addVideo(int requestType){
        Intent intent = new Intent();
        intent.setType("video/*").setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select video"), requestType);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar_add_post_body, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_toolbar_add_post_done) {
            if(!elements.isEmpty()) {
                adapter.saveItems();
                postDTO.setElementsBody(elements);
                startService(new Intent(this, AddPostService.class)
                .putExtra(KeyTransferObj.POST.toString(), postDTO)
                .putExtra(KeyTransferObj.AVATAR_URI.toString(), (Uri)getIntent().getParcelableExtra(KeyTransferObj.URI.toString())));
                startActivity(new Intent(AddPostBodyActivity.this, MainActivity.class));
            }
            else {
                showToast("You should add at least 1 element");
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void initToolbar() {
        toolbar.setTitle("Adding body");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_left_white);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}
