package kr.sofac.jangsisters.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

import kr.sofac.jangsisters.utils.AppPreference;
import kr.sofac.jangsisters.utils.ProgressBar;

public class BaseActivity extends AppCompatActivity {

    //TODO отступы у новостей

    public AppPreference appPreference;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);
        progressBar = new ProgressBar(this);
        appPreference = new AppPreference(this);
    }

    public void showToast(String messageToast){
        Toast.makeText(this, messageToast, Toast.LENGTH_SHORT).show();
    }

}
