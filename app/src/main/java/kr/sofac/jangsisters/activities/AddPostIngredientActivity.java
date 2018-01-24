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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.config.EnumPreference;
import kr.sofac.jangsisters.models.Ingredient;
import kr.sofac.jangsisters.network.Connection;
import kr.sofac.jangsisters.network.dto.AddPostDTO;
import kr.sofac.jangsisters.views.adapters.IngredientsAdapter;
import kr.sofac.jangsisters.views.adapters.IngredientsPanelAdapter;

public class AddPostIngredientActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ingredients_list)
    RecyclerView ingredientsList;
    @BindView(R.id.added_ingredients_list)
    RecyclerView addedIngredientsList;
    @BindView(R.id.add_ingredient)
    Button addIngredient;
    @BindView(R.id.add_own_ingredient)
    TextView addOwnIngredient;
    @BindView(R.id.panel)
    SlidingUpPanelLayout panel;
    @BindView(R.id.empty)
    ImageView empty;

    private IngredientsAdapter adapter;
    private IngredientsPanelAdapter panelAdapter;

    private AddPostDTO postDTO;

    //Список недобавленных ингридиентов
    private List<Ingredient> ingredients = new ArrayList<>();
    //Список только что добавленных ингридиентов
    private List<Ingredient> ingredientsToAdd = new ArrayList<>();
    // Список добавленных ингридиентов
    private List<Ingredient> ingredientsAdded = new ArrayList<>();

    @Override
    public void onBackPressed() {
        if (panel.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED)
            panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_ingredient);
        ButterKnife.bind(this);
        panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        panel.setTouchEnabled(false);
        initToolbar();
        loadIngredients();
        postDTO = (AddPostDTO) getIntent().getSerializableExtra(EnumPreference.POST.toString());
    }

    private void loadIngredients() {
        progressBar.showView();
        new Connection<List<Ingredient>>().getIngredients((isSuccess, answerServerResponse) -> {
            if (isSuccess) {
                ingredients = answerServerResponse.getDataTransferObject();
                panelAdapter = new IngredientsPanelAdapter(ingredients, (position, isChecked) -> {
                    if (isChecked)
                        ingredientsToAdd.add(ingredients.get(position));
                    else
                        ingredientsToAdd.remove(ingredients.get(position));
                });
                adapter = new IngredientsAdapter(ingredientsAdded, position -> {
                    ingredients.add(ingredientsAdded.get(position));
                    panelAdapter.notifyDataSetChanged();
                    ingredientsAdded.remove(position);
                    updateList();
                });
                ingredientsList.setAdapter(panelAdapter);
                ingredientsList.setLayoutManager(new LinearLayoutManager(this));
                addedIngredientsList.setAdapter(adapter);
                addedIngredientsList.setLayoutManager(new LinearLayoutManager(this));
            } else {
                //todo handle error
            }
            progressBar.dismissView();
        });

    }

    @OnClick(R.id.add_ingredient)
    public void addIngredient() {
        updateList();
        updatePanel();
        panel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    @OnClick(R.id.close)
    public void close() {
        panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        ingredientsToAdd.clear();
    }

    @OnClick(R.id.done)
    public void done() {
        panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        updateList();
        updatePanel();
    }

    private void updateList() {
        ingredientsAdded.addAll(ingredientsToAdd);
        adapter.notifyDataSetChanged();
        if (ingredientsAdded.isEmpty()) {
            addedIngredientsList.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        } else {
            addedIngredientsList.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
        }
    }

    private void updatePanel() {
        ingredients.removeAll(ingredientsToAdd);
        panelAdapter.notifyDataSetChanged();
        ingredientsToAdd.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar_add_post_next, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_toolbar_add_post_next) {
            if (ingredientsAdded.isEmpty()) {
                showToast("You must add at least 1 ingredient");
            } else {
                List<Integer> temp = new ArrayList<>();
                for (int i = 0; i < ingredientsAdded.size(); i++)
                    temp.add(ingredientsAdded.get(i).getShop_id());
                postDTO.setShopIngredients(temp);
                //TODO add own ingredients
                startActivity(new Intent(AddPostIngredientActivity.this, AddPostBodyActivity.class)
                        .putExtra(EnumPreference.POST.toString(), postDTO)
                        .putExtra(EnumPreference.URI.toString(), (Uri) getIntent().getParcelableExtra(EnumPreference.URI.toString())));
                overridePendingTransition(R.anim.forward_start, R.anim.forward_finish);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        toolbar.setTitle("Adding ingredients");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_left_white);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}