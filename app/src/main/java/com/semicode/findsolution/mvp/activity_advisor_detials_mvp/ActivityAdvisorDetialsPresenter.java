package com.semicode.findsolution.mvp.activity_advisor_detials_mvp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;


import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.preferences.Preferences;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAdvisorDetialsPresenter {
    private UserModel userModel;
    private Preferences preferences;
    private AdvisorDetialsActivityView view;
    private Context context;

    public ActivityAdvisorDetialsPresenter(AdvisorDetialsActivityView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void backPress() {

        view.onFinished();


    }



}
