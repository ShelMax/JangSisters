package kr.sofac.jangsisters.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.models.Category;
import kr.sofac.jangsisters.models.GlideApp;

import static kr.sofac.jangsisters.config.ServersConfig.BASE_URL;
import static kr.sofac.jangsisters.config.ServersConfig.PART_CATEGORY_IMAGE;

public class GridCategoryAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<Category> listAllCategory;


    public GridCategoryAdapter(Context context, List<Category> listAllCategory) {
        this.mContext = context;
        this.listAllCategory = listAllCategory;
    }


    @Override
    public int getCount() {
        return listAllCategory.size();
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(R.layout.item_category_with_background, null);

        final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageViewCategoryIcon);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.categoryTitle);
        nameTextView.setText(listAllCategory.get(position).getName());

        GlideApp.with(convertView)
                .load(BASE_URL + PART_CATEGORY_IMAGE + listAllCategory.get(position) + ".png")
                .override(80, 80)
                .apply(RequestOptions.centerCropTransform()
                        .placeholder(R.drawable.arrow_left_grey)
                        .error(R.drawable.arrow_left_grey))
                .into(imageView);

        return convertView;
    }

}

