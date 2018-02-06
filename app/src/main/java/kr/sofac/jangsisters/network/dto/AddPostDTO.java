package kr.sofac.jangsisters.network.dto;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import kr.sofac.jangsisters.models.BasePostElement;

public class AddPostDTO implements Parcelable{

    @SerializedName("customer_id")
    private Integer customerID;

    private String title;

    private String description;

    @SerializedName("ingredients")
    private String ownIngredients;

    @SerializedName("shop_ingredients")
    private List<Integer>  shopIngredients;

    private List<Integer> categories;

    @SerializedName("body")
    private List<BasePostElement> elementsBody;

    public AddPostDTO() {
    }

    public AddPostDTO setCustomerID(Integer customerID) {
        this.customerID = customerID;
        return this;
    }

    public AddPostDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<BasePostElement> getElements(){
        return this.elementsBody;
    }

    public AddPostDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public AddPostDTO setOwnIngredients(String ownIngredients) {
        this.ownIngredients = ownIngredients;
        return this;
    }

    public AddPostDTO setShopIngredients(List<Integer> shopIngredients) {
        this.shopIngredients = shopIngredients;
        return this;
    }

    public AddPostDTO setCategories(List<Integer> categories) {
        this.categories = categories;
        return this;
    }

    public AddPostDTO setElementsBody(List<BasePostElement> elementsBody) {
        this.elementsBody = elementsBody;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(customerID);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(ownIngredients);
        dest.writeList(shopIngredients);
        dest.writeList(categories);
        dest.writeList(elementsBody);
    }

    public static final Parcelable.Creator<AddPostDTO> CREATOR =
            new Parcelable.Creator<AddPostDTO>() {

        @Override
        public AddPostDTO createFromParcel(Parcel source) {
            return new AddPostDTO(source);
        }

        @Override
        public AddPostDTO[] newArray(int size) {
            return new AddPostDTO[size];
        }
    };

    private AddPostDTO(Parcel in) {
        customerID = in.readInt();
        title = in.readString();
        description = in.readString();
        ownIngredients = in.readString();
        shopIngredients = new ArrayList<>();
        shopIngredients = in.readArrayList(Integer.class.getClassLoader());
        categories = new ArrayList<>();
        categories = in.readArrayList(Integer.class.getClassLoader());
        elementsBody = new ArrayList<>();
        elementsBody = in.readArrayList(BasePostElement.class.getClassLoader());
    }
}
