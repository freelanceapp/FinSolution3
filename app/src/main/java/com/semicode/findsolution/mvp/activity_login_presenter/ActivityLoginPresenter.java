package com.semicode.findsolution.mvp.activity_login_presenter;

import android.content.Context;

import com.semicode.findsolution.models.LoginModel;

public class ActivityLoginPresenter {
    private Context context;
    private ActivityLoginView view;
    private LoginModel model;

    public ActivityLoginPresenter(Context context, ActivityLoginView view) {
        this.context = context;
        this.view = view;
    }

    public void checkData(LoginModel loginModel){
        this.model = loginModel;
        if (model.isDataValid(context)){
            view.onLoginValid();
        }
    }
}
