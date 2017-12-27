package kr.sofac.jangsisters.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import kr.sofac.jangsisters.models.User;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.USER_SERVICE;
import static kr.sofac.jangsisters.config.EnumPreference.APP_PREFERENCES;
import static kr.sofac.jangsisters.config.EnumPreference.GOOGLE_CLOUD_PREFERENCE;
import static kr.sofac.jangsisters.config.EnumPreference.IS_AUTHORIZATION;


/**
 * Created by Maxim on 02.09.2017.
 */

public class AppPreference {

    private SharedPreferences preferences;
    private GsonBuilder builder = new GsonBuilder();
    private Gson gson = builder.create();
    private SharedPreferences.Editor editor;

    public AppPreference(Context context) {
        preferences = context.getSharedPreferences(USER_SERVICE, MODE_PRIVATE);
        editor = preferences.edit();
    }

    public Boolean getAuthorization() {
        return preferences.getBoolean(IS_AUTHORIZATION.toString(), false);
    }

    public void setAuthorization(Boolean isAuthorization) {
        editor.putBoolean(IS_AUTHORIZATION.toString(), isAuthorization);
        editor.apply();
        editor.commit();
    }

    public String getGoogleKey() {
        return preferences.getString(GOOGLE_CLOUD_PREFERENCE.toString(), "");
    }

    public void setGoogleKey(String googleKey) {
        editor.putString(GOOGLE_CLOUD_PREFERENCE.toString(), googleKey);
        editor.apply();
        editor.commit();
    }

    public User getUser() {
        return gson.fromJson(preferences.getString(APP_PREFERENCES.toString(), ""), new TypeToken<User>(){}.getType());
    }

    public void setUser(User user) {
        editor.putString(APP_PREFERENCES.toString(), gson.toJson(user));
        editor.apply();
        editor.commit();
    }

}
