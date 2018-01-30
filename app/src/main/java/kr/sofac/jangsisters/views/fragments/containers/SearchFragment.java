package kr.sofac.jangsisters.views.fragments.containers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.config.EnumPreference;
import kr.sofac.jangsisters.models.TabManager;
import kr.sofac.jangsisters.utils.AppPreference;
import kr.sofac.jangsisters.views.fragments.BaseFragment;
import kr.sofac.jangsisters.views.fragments.viewElements.GridViewCategoryFragment;
import kr.sofac.jangsisters.views.fragments.viewElements.GridViewPostFragment;

public class SearchFragment extends BaseFragment {

    @BindView(R.id.panel) SlidingUpPanelLayout panel;
    @BindView(R.id.frame_search) FrameLayout frame;
    @BindView(R.id.search) Button searchButton;
    @BindView(R.id.frame_filters) FrameLayout frameFilters;

    Unbinder unbinder;
    private EditText search;
    private GridViewCategoryFragment gridViewCategoryFragment = new GridViewCategoryFragment();
    private GridViewPostFragment gridViewPostFragment = new GridViewPostFragment();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        initGridFragment();
        getFragmentManager().beginTransaction().add(R.id.frame_search, gridViewPostFragment).commit();
        getFragmentManager().beginTransaction().add(R.id.frame_filters, gridViewCategoryFragment).commit();


//        Bundle bundleCategoriesID = new Bundle();
//        bundleCategoriesID.putSerializable("listCategoryID", Arrays.gridViewCategoryFragment.getSelectedCategory());
//        gridViewPostFragment = new GridViewPostFragment();
//        gridViewPostFragment.setArguments(bundleCategoriesID);

        //TODO FINISH
        panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        panel.setTouchEnabled(false);
//        searchButton.setOnClickListener(v -> {
//            getFragmentManager().beginTransaction().add(R.id.frame_search, gridViewPostFragment).commit();
//            hideSlideUp();
//        });

        return view;
    }

    private void initGridFragment() {
        AppPreference appPreference = new AppPreference(getActivity());
        Bundle bundle = new Bundle();
        if(appPreference.isUserLogged()) {
            bundle.putInt(EnumPreference.USER_ID.toString(), appPreference.getUser().getId());
        }
        else {
            bundle.putInt(EnumPreference.USER_ID.toString(), 0);
        }
        bundle.putSerializable(EnumPreference.GRID_ACTION.toString(), EnumPreference.SEARCH);
        gridViewPostFragment.setArguments(bundle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(TabManager.getTabManager().getMenuByPosition(1), menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void onSetTextChanged(EditText search) {
        this.search = search;
        search.setOnFocusChangeListener((view, focused) -> {
            if (focused) {
                if (panel.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN) {
                    panel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    search.setInputType(InputType.TYPE_NULL);
                    frame.requestFocus();
                } else {
                    search.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            } else {
                if (panel.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    search.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });
    }

    public void hideSlideUp() {
        panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        search.setInputType(InputType.TYPE_CLASS_TEXT);
        frame.requestFocus();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
