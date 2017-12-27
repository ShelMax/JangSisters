package kr.sofac.jangsisters.activities;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Maxim on 15.12.2017.
 */

public class BaseActivity extends AppCompatActivity {

    public void showToast(String messageToast){
        Toast.makeText(this, messageToast, Toast.LENGTH_SHORT).show();
    }

}
