package kr.sofac.jangsisters.views.fragments.viewElements;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.models.Category;
import kr.sofac.jangsisters.utils.AppPreference;
import kr.sofac.jangsisters.views.adapters.CategoryFoFilterAdapter;
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
            RecyclerView recyclerViewSubCategory = ingredientsView.findViewById(R.id.recyclerViewSubCategory);
            ((TextView) ingredientsView.findViewById(R.id.textViewGroupTitle)).setText(mainCategory.getName());
            recyclerViewSubCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

            CategoryFoFilterAdapter categoryAdapter = new CategoryFoFilterAdapter(mainCategory.getSubmenu(), true);
            categoryAdapter.setCallbackFilter((view, position) -> {
                mainCategory.getSubmenu().get(position).setSelectedCategory(true);
                for (Category categoryMy : mainCategory.getSubmenu()) {
                    if (mainCategory.getSubmenu().get(position).getId() != categoryMy.getId())
                        categoryMy.setSelectedCategory(false);
                }
                categoryAdapter.notifyDataSetChanged();
            });

            recyclerViewSubCategory.setAdapter(categoryAdapter);
            containerLinerLayout.addView(ingredientsView);
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void getChoiceIngridients() {

    }


}
