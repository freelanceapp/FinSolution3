package com.semicode.findsolution.mvp.activity_sign_up_mvp;

import com.semicode.findsolution.models.UserModel;

import java.util.List;

public interface ActivitySignUpView {

    void onFailed(String msg);
    void onLoad();
    void onFinishload();
    void onSignupValid(UserModel userModel);
    void onFailed();
    void onServer();

    void onnotconnect(String msg);
}
