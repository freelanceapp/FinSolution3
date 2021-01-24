package com.semicode.findsolution.mvp.fragment_profile_mvp;


import com.semicode.findsolution.models.SettingModel;
import com.semicode.findsolution.models.UserModel;

public interface FragmentProfileView {

    void onFailed(String msg);
    void onLoad();
    void onFinishload();
    void onFailed();
    void onServer();

    void onnotconnect(String msg);


    void onprofile(UserModel body);
}
