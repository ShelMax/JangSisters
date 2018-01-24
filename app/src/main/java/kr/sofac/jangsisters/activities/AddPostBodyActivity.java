package kr.sofac.jangsisters.activities;

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
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.config.EnumPreference;
import kr.sofac.jangsisters.models.BasePostElement;
import kr.sofac.jangsisters.models.ImageCallback;
import kr.sofac.jangsisters.models.ImageContainerCallback;
import kr.sofac.jangsisters.network.Connection;
import kr.sofac.jangsisters.network.dto.AddPostDTO;
import kr.sofac.jangsisters.network.dto.SenderContainerDTO;
import kr.sofac.jangsisters.views.adapters.AddPostAdapter;

public class AddPostBodyActivity extends BaseActivity {

    private static final int SELECT_PICTURE_ADD_CONTAINER = 1;
    private static final int SELECT_PICTURE_TO_CONTAINER = 2;
    private static final int SELECT_PICTURE_REPLACE_IN_CONTAINER = 3;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    private List<BasePostElement> elements = new ArrayList<>();
    private AddPostAdapter adapter;
    private LinearLayoutManager manager;

    private int containerPosition;

    private AddPostDTO postDTO;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageLoaded(data.getData(), requestCode);
        }
    }

    private void imageLoaded(Uri uri, int requestCode) {
        if (requestCode == SELECT_PICTURE_ADD_CONTAINER) {
            List<Uri> list = new ArrayList<>();
            list.add(uri);
            elements.add(new BasePostElement("", elements.size(), "image", list));
            adapter.notifyItemInserted(elements.size());
            manager.scrollToPosition(elements.size() - 1);
        } else if (requestCode == SELECT_PICTURE_TO_CONTAINER) {
            elements.get(containerPosition).getUris().add(uri);
            adapter.notifyItemChanged(containerPosition);
        } else if (requestCode == SELECT_PICTURE_REPLACE_IN_CONTAINER) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_body);
        ButterKnife.bind(this);
        initToolbar();
        postDTO = (AddPostDTO) getIntent().getSerializableExtra(EnumPreference.POST.toString());
        adapter = new AddPostAdapter(elements, this::deleteContainer,
                new ImageCallback() {
                    @Override
                    public void delete(int imagePosition, int containerPosition) {
                        deleteImage(imagePosition, containerPosition);
                    }

                    @Override
                    public void addImage(int imagePosition, int containerPosition) {
                        replaceImageInContainer(imagePosition, containerPosition);
                    }
                },
                new ImageContainerCallback() {
                    @Override
                    public void delete(int position) {
                        deleteContainer(position);
                    }

                    @Override
                    public void addImage(int position) {
                        addImageToContainer(position);
                    }
                });
        recyclerView.setAdapter(adapter);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
    }

    private void replaceImageInContainer(int imagePosition, int containerPosition) {

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
            progressBar.showView();
            List<Uri> uris = new ArrayList<>();
            uris.add(getIntent().getParcelableExtra(EnumPreference.URI.toString()));
            for (int i = 0; i < elements.size(); i++) {
                uris.addAll(elements.get(i).getUris());
            }
            adapter.saveItems();
            postDTO.setElementsBody(elements);
            new Connection<SenderContainerDTO>().addPost(this, postDTO,
                    uris, (isSuccess, answerServerResponse) -> {
                        if (isSuccess) {

                        } else {

                        }
                        progressBar.dismissView();
                    });
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
