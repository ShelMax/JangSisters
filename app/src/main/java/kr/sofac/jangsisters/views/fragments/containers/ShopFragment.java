package kr.sofac.jangsisters.views.fragments.containers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.config.Server;
import kr.sofac.jangsisters.models.TabManager;
import kr.sofac.jangsisters.views.fragments.BaseFragment;

public class ShopFragment extends BaseFragment {

    @BindView(R.id.shop_webview) WebView webView;
    @BindView(R.id.shop_refresh) SwipeRefreshLayout refresh;

    private List<String> pages;
    private int currentPage = -1;
    private boolean fromNavigation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        ButterKnife.bind(this, view);
        pages = new ArrayList<>();
        webView.loadUrl(Server.SHOP_URL);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                refresh.setRefreshing(true);
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(view.getTitle());
                if(!fromNavigation) {
                    pages.add(view.getUrl());
                    currentPage++;
                }
                fromNavigation = false;
                refresh.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(TabManager.getTabManager().getMenuByPosition(0), menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_toolbar_shop_back:
                backClick();
                break;
            case R.id.menu_toolbar_shop_forward:
                forwardClick();
                break;
            case R.id.menu_toolbar_shop_refresh:
                refreshClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void backClick(){
        if(currentPage != 0) {
            fromNavigation = true;
            currentPage--;
            webView.loadUrl(pages.get(currentPage));
        }
    }

    public void forwardClick(){
        if(currentPage != pages.size() - 1) {
            fromNavigation = true;
            currentPage++;
            webView.loadUrl(pages.get(currentPage));
        }
    }

    public void refreshClick(){
        if(currentPage != -1) {
            fromNavigation = true;
            webView.loadUrl(pages.get(currentPage));
        }
    }

    public void homeClick() {
        fromNavigation = true;
        webView.loadUrl(Server.SHOP_URL);
    }

}
