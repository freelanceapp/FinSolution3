package com.semicode.findsolution.mvp.activity_splash_mvp;

import android.content.Context;
import android.os.Handler;

import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.preferences.Preferences;

public class SplashPresenter {
    private Context context;
    private SplashView view;
    private Preferences preferences;
    private UserModel userModel;
//    private UserSettingsModel userSettingsModel;

    public SplashPresenter(Context context, SplashView view) {
        this.context = context;
        this.view = view;
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(context);
//        userSettingsModel = preferences.getUserSettings(context);
        delaySplash();
    }

    private void delaySplash(){
        new Handler().postDelayed(()->{

//            if (userSettingsModel!=null&&userSettingsModel.isLanguageSelected()){
                view.onNavigateToHomeActivity();
//            }else {
//                view.onNavigateToLanguageActivity();
//
//            }



        },2000);
    }
}
