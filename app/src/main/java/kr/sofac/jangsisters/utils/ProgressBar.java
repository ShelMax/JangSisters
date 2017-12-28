package kr.sofac.jangsisters.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import kr.sofac.jangsisters.R;

public class ProgressBar {

    private ProgressDialog pd;

    public ProgressBar(Context context) {
        this.pd = new ProgressDialog(context, R.style.ProgressBarTheme);
        pd.setCancelable(false);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void showView() {
        pd.show();
    }

    public void dismissView() {
        pd.dismiss();
    }

}
