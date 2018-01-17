package kr.sofac.jangsisters.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import kr.sofac.jangsisters.R;

public class LaunchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        new Handler().postDelayed(() -> {startActivity(intent);
        finishAffinity();
        },500);
    }

    public void requestCheckVersionCategories(){

    }
}
