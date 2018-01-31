package kr.sofac.jangsisters.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.models.Category;
import kr.sofac.jangsisters.models.Version;
import kr.sofac.jangsisters.network.Connection;

public class LaunchActivity extends BaseActivity {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);
        requestCheckVersionCategories();
    }

    public void requestCheckVersionCategories() {
        new Connection<Version>().getCorrectVersion((isSuccess, answerServerResponse) -> {
            if (isSuccess) {
                if (isCorrectVersion(answerServerResponse.getDataTransferObject())) {
                    startMainActivity();
                } else {
                    new Connection<List<Category>>().getListCategories((isSuccess1, answerServerResponse1) -> {
                        if (isSuccess1) {
                            appPreference.saveCategories(answerServerResponse1.getDataTransferObject());
                            appPreference.setVersionCategories(answerServerResponse.getDataTransferObject().getValue());
                            startMainActivity();
                        } else {
                            showToast(getString(R.string.connection_error));
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            } else {
                showToast(getString(R.string.connection_error));
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    public boolean isCorrectVersion(Version versionFromServer) {
        return versionFromServer.getValue() == appPreference.getVersionCategories();
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finishAffinity();
    }
}
