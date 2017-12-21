package kr.sofac.jangsisters.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.models.Post;
import kr.sofac.jangsisters.utils.PostWrapper;
import kr.sofac.jangsisters.views.adapters.CategoryAdapter;

public class PostDetailedActivity extends BaseActivity {

    //TODO Toolbar (back, title, dots)

    @BindView(R.id.post_detailed_image) ImageView postImage;
    @BindView(R.id.post_detailed_author_image) ImageView authorImage;
    @BindView(R.id.post_detailed_categories_list) RecyclerView categoriesList;
    @BindView(R.id.post_detailed_title) TextView title;
    @BindView(R.id.post_detailed_date) TextView date;
    @BindView(R.id.post_detailed_author) TextView author;
    @BindView(R.id.post_detailed_toolbar) Toolbar toolbar;

    private Post post;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detailed);
        ButterKnife.bind(this);
        getPost();
        initToolbar();

        Glide.with(this).load(post.getImageURL()).into(postImage);
        Glide.with(this).load(post.getAuthorURL()).apply(RequestOptions.circleCropTransform()).into(authorImage);
        title.setText(post.getTitle());
        date.setText(post.getDate());
        author.setText(post.getAuthor());
        initCategories();
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
        categoriesList.setAdapter(new CategoryAdapter(post.getCategories()));
        categoriesList.setLayoutManager(layoutManager);
    }

    private void getPost() {
        post = PostWrapper.getPostById(getIntent().getExtras().getInt(getString(R.string.intent_postID)));
    }

    @OnClick(R.id.post_detailed_category_left)
    public void onLeftClick(){
        if(layoutManager.findFirstCompletelyVisibleItemPosition()!=0)
            layoutManager.scrollToPosition(layoutManager.findFirstCompletelyVisibleItemPosition() - 1);
    }

    @OnClick(R.id.post_detailed_category_right)
    public void onRightClick(){
        if(layoutManager.findLastCompletelyVisibleItemPosition()!=post.getCategories().size())
            layoutManager.scrollToPosition(layoutManager.findLastCompletelyVisibleItemPosition() + 1);
    }

}
