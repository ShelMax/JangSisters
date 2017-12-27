package kr.sofac.jangsisters.views.fragments.containers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.models.Constants;
import kr.sofac.jangsisters.views.fragments.BaseFragment;
import kr.sofac.jangsisters.views.fragments.viewElements.GridViewPostFragment;

public class SearchFragment extends BaseFragment {

    @BindView(R.id.panel) SlidingUpPanelLayout panel;
    @BindView(R.id.frame_search) FrameLayout frame;
    @BindView(R.id.search) Button searchButton;
    private EditText search;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        getFragmentManager().beginTransaction()
                .add(R.id.frame_search, new GridViewPostFragment())
                .commit();
        panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        panel.setTouchEnabled(false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(Constants.toolbarMenus().get(1), menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    public void onSetTextChanged(EditText search){
        this.search = search;
        search.setOnFocusChangeListener((view, focused) -> {
            if(focused) {
                if(panel.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN) {
                    panel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    search.setInputType(InputType.TYPE_NULL);
                    frame.requestFocus();
                }
                else {
                    search.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
            else {
                if(panel.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED){
                    search.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });
    }

    public void hideSlideUp(){
        panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        search.setInputType(InputType.TYPE_CLASS_TEXT);
        frame.requestFocus();
    }
}
