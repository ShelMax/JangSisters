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

    public AppPreference(Context context) {
        preferences = context.getSharedPreferences(USER_SERVICE, MODE_PRIVATE);
    }

    public Boolean getAuthorization() {
        return preferences.getBoolean(IS_AUTHORIZATION.toString(), false);
    }

    public void setAuthorization(Boolean isAuthorization) {
        SharedPreferences.Editor editorUser = preferences.edit();
        editorUser.putBoolean(IS_AUTHORIZATION.toString(), isAuthorization);
        editorUser.apply();
    }

    public String getGoogleKey() {
        return preferences.getString(GOOGLE_CLOUD_PREFERENCE.toString(), "");
    }

    public void setGoogleKey(String googleKey) {
        SharedPreferences.Editor editorUser = preferences.edit();
        editorUser.putString(GOOGLE_CLOUD_PREFERENCE.toString(), googleKey);
        editorUser.apply();
    }

    public User getUser() {
        return gson.fromJson(preferences.getString(APP_PREFERENCES.toString(), ""), new TypeToken<User>(){}.getType());
    }

    public void setUser(User user) {
        SharedPreferences.Editor editorUser = preferences.edit();
        editorUser.putString(APP_PREFERENCES.toString(), gson.toJson(user));
        editorUser.apply();
    }

}
