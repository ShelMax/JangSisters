package kr.sofac.jangsisters.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.config.EnumPreference;
import kr.sofac.jangsisters.models.Comment;
import kr.sofac.jangsisters.models.GlideApp;
import kr.sofac.jangsisters.models.Post;
import kr.sofac.jangsisters.network.Connection;
import kr.sofac.jangsisters.network.api.type.ServerResponse;
import kr.sofac.jangsisters.network.dto.SenderContainerDTO;
import kr.sofac.jangsisters.views.adapters.CategoryAdapter;
import kr.sofac.jangsisters.views.adapters.CommentAdapter;
import kr.sofac.jangsisters.views.adapters.DetailPostAdapter;
import kr.sofac.jangsisters.views.adapters.PostIngredientsAdapter;

import static kr.sofac.jangsisters.config.ServersConfig.BASE_URL;
import static kr.sofac.jangsisters.config.ServersConfig.PART_AVATAR;
import static kr.sofac.jangsisters.config.ServersConfig.PART_POST;

public class DetailPostActivity extends BaseActivity {

    @BindView(R.id.post_detailed_image) ImageView postImage;
    @BindView(R.id.post_detailed_author_image) ImageView authorImage;
    @BindView(R.id.post_detailed_categories_list) RecyclerView categoriesList;
    @BindView(R.id.post_detailed_date) TextView date;
    @BindView(R.id.post_detailed_author) TextView author;
    @BindView(R.id.post_detailed_toolbar) Toolbar toolbar;
    @BindView(R.id.textViewContent)
    RecyclerView textViewContent;

    @BindView(R.id.like) Button like;
    @BindView(R.id.comment) Button comment;

    @BindView(R.id.panel) SlidingUpPanelLayout panel;
    @BindView(R.id.comments_list) RecyclerView commentsList;
    @BindView(R.id.new_comment) EditText commentText;

    private ListView listView;
    private ImageButton imageButtonClose;

    private Post post;
    private LinearLayoutManager layoutManager;
    private int userID;
    private int postID;
    private CommentAdapter commentAdapter;
    private DetailPostAdapter adapter;

    private boolean wasLiked;
    private boolean isLiked;

    AlertDialog dialog;

    @Override
    public void onBackPressed() {
        if (panel.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detailed);
        ButterKnife.bind(this);

        panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        panel.setTouchEnabled(false);

        if(appPreference.getUser() != null)
            userID = appPreference.getUser().getId();

        postID = getIntent().getIntExtra(EnumPreference.POST_ID.toString(), 1);
        loadPost();
    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View ingredientsView = getLayoutInflater().inflate(R.layout.dialog_post_ingredients, null);
        builder.setView(ingredientsView);
        dialog = builder.create();
        listView = ingredientsView.findViewById(R.id.post_ingredients_list);
        imageButtonClose = ingredientsView.findViewById(R.id.imageButtonClosePopup);
        imageButtonClose.setOnClickListener(view -> dialog.hide());
        listView.setAdapter(new PostIngredientsAdapter(post.getIngredients(), this));
    }

    private void loadPost() {
        progressBar.showView();
        new Connection<Post>().getPost(
                new SenderContainerDTO().setID(postID).setCustomer_id(userID), (isSuccess, answerServerResponse) -> {
                    if (isSuccess) {
                        post = answerServerResponse.getDataTransferObject();
                        fillUpHeader();
                        initToolbar();
                        initCategories();
                        fillUpContentPost();
                        initDialog();
                    } else {
                        showToast("Can't open this post, sorry!");
                        finish();
                    }
                    progressBar.dismissView();
                });
    }

    private void loadComments() {
        progressBar.showView();
        new Connection<List<Comment>>().getPostComments(postID, (isSuccess, answerServerResponse) -> {
            if(isSuccess){
                commentAdapter = new CommentAdapter(answerServerResponse.getDataTransferObject());
                commentsList.setAdapter(commentAdapter);
                commentsList.setLayoutManager(new LinearLayoutManager(this));
            }
            else{
                //todo handle error
            }
            progressBar.dismissView();
        });
    }

    private void fillUpHeader() {
        GlideApp.with(this)
                .load(BASE_URL + PART_POST + post.getPostImage())
                .apply(RequestOptions.centerCropTransform().placeholder(R.drawable.background_holder).error(R.drawable.background_holder))
                .into(postImage);
        GlideApp.with(this)
                .load(BASE_URL + PART_AVATAR + post.getAuthorImg())
                .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.avatar_holder).error(R.drawable.avatar_holder).circleCrop())
                .into(authorImage);

        authorImage.setOnClickListener(v -> {
            boolean isLogged = appPreference.getUser() != null;
            Intent intent = new Intent(DetailPostActivity.this, UserActivity.class);
            intent.putExtra(EnumPreference.USER_ID.toString(), post.getAuthorID());
            if (isLogged)
                intent.putExtra(EnumPreference.MY_PROFILE.toString(),
                        appPreference.getUser().getId() == post.getAuthorID());
            else
                intent.putExtra(EnumPreference.MY_PROFILE.toString(),
                        false);
            startActivity(intent);
        });
        date.setText(post.getDate());
        author.setText(post.getAuthorName());
        isLiked = post.isLiked() > 0;
        wasLiked = post.isLiked() > 0;
        if(isLiked){
            like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart_full, 0, 0, 0);
        }
        if(post.getLikesCount() != 0) {
            like.setText(String.valueOf(post.getLikesCount()));
        }
        if(post.getCommentsCount() != 0){
            comment.setText(String.valueOf(post.getCommentsCount()));
        }
    }

    @OnClick(R.id.comment_add)
    public void addComment(){
        if (userID == 0) {
            showToast("You must login first");
            return;
        }
        if(!commentText.getText().toString().isEmpty()){
            progressBar.showView();
            new Connection<Comment>().addCommentToPost(new SenderContainerDTO()
                            .setCustomer_id(userID)
                            .setPostID(postID)
                            .setBody(commentText.getText().toString()), (isSuccess, answerServerResponse) -> {
                        if(isSuccess){
                            commentText.setText("");
                            loadComments();
                        }
                        else{
                            //todo handle error
                        }
                        progressBar.dismissView();
                    }
            );
        }
    }

    @OnClick(R.id.like)
    public void like(){
        if (userID == 0) {
            showToast("You must login first");
            return;
        }
        new Connection<ServerResponse>().likePost(new SenderContainerDTO()
                .setCustomer_id(userID)
                .setPostID(postID), (isSuccess, answerServerResponse) -> {
            if(isSuccess){
                isLiked = !isLiked;
                if(isLiked) {
                    //TODO after server update
                    //like.setText();
                    like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart_full, 0, 0, 0);
                    like.setText(String.valueOf(post.getLikesCount()));
                    if (!wasLiked)
                        like.setText(String.valueOf(post.getLikesCount() + 1));
                }
                else{
                    like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart, 0,0,0);
                    like.setText(String.valueOf(post.getLikesCount()));
                    if (wasLiked)
                        like.setText(String.valueOf(post.getLikesCount() - 1));
                }
            }
            else{
                showToast("Couldn't like this post, sorry");
            }
        });
    }

    @OnClick(R.id.comment)
    public void comment(){
        panel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        loadComments();
    }

    @OnClick(R.id.cart)
    public void cart(){
        dialog.show();
    }

    @OnClick(R.id.bookmark)
    public void bookmark(){

    }

    @OnClick(R.id.comment_add_image)
    public void addCommentImage(){

    }

    @OnClick(R.id.close)
    public void closeComments(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(commentText.getWindowToken(), 0);
        new Handler().postDelayed(() -> panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN), 300);
    }

    @OnClick(R.id.post_detailed_category_left)
    public void onLeftClick() {
        if (layoutManager.findFirstCompletelyVisibleItemPosition() != 0)
            layoutManager.scrollToPosition(layoutManager.findFirstCompletelyVisibleItemPosition() - 1);
    }

    @OnClick(R.id.post_detailed_category_right)
    public void onRightClick() {
        if (layoutManager.findLastCompletelyVisibleItemPosition() != post.getCategories().size())
            layoutManager.scrollToPosition(layoutManager.findLastCompletelyVisibleItemPosition() + 1);
    }

    private void fillUpContentPost() {
        LinearLayoutManager managerImages = new LinearLayoutManager(this);
        LinearLayoutManager managerVideos = new LinearLayoutManager(this);
        managerImages.setOrientation(LinearLayoutManager.HORIZONTAL);
        managerVideos.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapter = new DetailPostAdapter(post.getBody());
        textViewContent.setAdapter(adapter);
        textViewContent.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initToolbar() {
        toolbar.setTitle(post.getTitle());
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.arrow_left_white);
        setSupportActionBar(toolbar);
    }

    private void initCategories() {
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoriesList.setAdapter(new CategoryAdapter(post.getCategories(),true));
        categoriesList.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
