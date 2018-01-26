package kr.sofac.jangsisters.views.fragments.viewElements;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.models.Category;
import kr.sofac.jangsisters.utils.AppPreference;
import kr.sofac.jangsisters.views.fragments.BaseFragment;

public class GridViewCategoryFragment extends BaseFragment {

    @BindView(R.id.containerLinerLayout)
    LinearLayout containerLinerLayout;
    Unbinder unbinder;
    private AppPreference appPreference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        appPreference = new AppPreference(getActivity());
        View view = inflater.inflate(R.layout.fragment_grid_view_category, container, false);
        unbinder = ButterKnife.bind(this, view);

        fillMainsCategories(appPreference.getCategories());
        return view;

    }


    public void fillMainsCategories(List<Category> listAllCategory) {
        for (Category mainCategory : listAllCategory) {
            View ingredientsView = getLayoutInflater().inflate(R.layout.item_group_category, null);
            ((TextView) ingredientsView.findViewById(R.id.textViewGroupTitle)).setText(mainCategory.getName());
            for (Category subCategory : mainCategory.getSubmenu()) {
                View itemView = getLayoutInflater().inflate(R.layout.item_category_with_background, null);
                ((TextView) itemView.findViewById(R.id.categoryTitle)).setText(subCategory.getName());
                ((GridLayout) ingredientsView.findViewById(R.id.gridView)).addView(itemView);
            }
            containerLinerLayout.addView(ingredientsView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getChoiceIngridients(){

    }


}
