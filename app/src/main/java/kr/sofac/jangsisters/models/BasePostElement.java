package kr.sofac.jangsisters.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class BasePostElement implements Parcelable{

    protected String text;
    protected int position;
    protected String type;
    private List<Uri> uris;

    BasePostElement() {
    }

    public BasePostElement(String text, int position, String type, List<Uri> uris) {
        this.text = text;
        this.position = position;
        this.type = type;
        this.uris = uris;
    }

    public List<Uri> getUris() {
        return uris;
    }

    public void setUris(List<Uri> uris) {
        this.uris = uris;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public int getPosition() {
        return position;
    }

    public String getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeInt(position);
        dest.writeString(type);
        dest.writeList(uris);
    }

    public static final Parcelable.Creator<BasePostElement> CREATOR =
            new Parcelable.Creator<BasePostElement>() {
        public BasePostElement createFromParcel(Parcel in) {
            return new BasePostElement(in);
        }

        public BasePostElement[] newArray(int size) {
            return new BasePostElement[size];
        }
    };

    private BasePostElement(Parcel in) {
        text = in.readString();
        position = in.readInt();
        type = in.readString();
        uris = new ArrayList<>();
        uris = in.readArrayList(Uri.class.getClassLoader());
    }
}
