package kr.sofac.jangsisters;

import android.app.Application;
import android.util.Log;

public class JangSisters extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("TAG", "YEP");
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
//        // Normal app init code...
    }
}
