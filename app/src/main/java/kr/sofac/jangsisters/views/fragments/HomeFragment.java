package kr.sofac.jangsisters.views.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.models.Post;
import kr.sofac.jangsisters.views.adapters.PostAdapter;


public class HomeFragment extends BaseFragment {

    @BindView(R.id.home_recycler) RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        List<Post> items = new ArrayList<>();
        items.add(new Post("https://18008579627329362eda2218-rg2mjh9f0tf5llf.netdna-ssl.com/wp-content/uploads/2017/05/keto-meal-chicken-avocado-300x200.jpg",
                "http://i.dailymail.co.uk/i/pix/2017/04/20/13/3F6B966D00000578-4428630-image-m-80_1492690622006.jpg",
                "Mark", 2,15, "10 october 2018","How to eat hamburger", "Blablabla fgf gfgjfkgjfkg fjg kfjg fkgj fjggjkfgjkfgjkdfjgksd fgjdkfgjsdklfgjklsdfjgkls",
                new ArrayList<>()));
        items.add(new Post("https://18008579627329362eda2218-rg2mjh9f0tf5llf.netdna-ssl.com/wp-content/uploads/2017/05/keto-meal-chicken-avocado-300x200.jpg",
                "http://i.dailymail.co.uk/i/pix/2017/04/20/13/3F6B966D00000578-4428630-image-m-80_1492690622006.jpg",
                "Mark1", 22,151,"11 october 2018", "How to eat sushi", "Blablabla fgf gfgjfkgjfkg fjg kfjg fkgj fjggjkfgjkfgjkdfjgksd fgjdkfgjsdklfgjklsdfjgkls",
                new ArrayList<>()));
        items.add(new Post("https://18008579627329362eda2218-rg2mjh9f0tf5llf.netdna-ssl.com/wp-content/uploads/2017/05/keto-meal-chicken-avocado-300x200.jpg",
                "http://i.dailymail.co.uk/i/pix/2017/04/20/13/3F6B966D00000578-4428630-image-m-80_1492690622006.jpg",
                "Mark2", 21,152, "12 october 2018","How to eat pasta", "Blablabla fgf gfgjfkgjfkg fjg kfjg fkgj fjggjkfgjkfgjkdfjgksd fgjdkfgjsdklfgjklsdfjgkls",
                new ArrayList<>()));
        PostAdapter adapter = new PostAdapter(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }



}
