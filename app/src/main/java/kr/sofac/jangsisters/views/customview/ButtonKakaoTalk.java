package kr.sofac.jangsisters.views.customview;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import kr.sofac.jangsisters.R;

/**
 * Created by Maxim on 26.12.2017.
 */

public class ButtonKakaoTalk extends AppCompatButton {

    public ButtonKakaoTalk(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackground(getResources().getDrawable(R.drawable.custom_button_kakao));
        setTextColor(getResources().getColor(R.color.colorKakaoButtonText));
//        setMinHeight(getResources().getDimensionPixelSize(R.dimen.min_height_custom_button));
    }

}
