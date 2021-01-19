package com.semicode.findsolution.mvp.fragment_contactus_mvp;


import com.semicode.findsolution.models.SettingModel;

public interface FragmentContactusView {

    void onFailed(String msg);
    void onLoad();
    void onFinishload();
    void onContactVaild();
    void onFailed();
    void onServer();

    void onnotconnect(String msg);

    void ViewSocial(String link);

    void onsetting(SettingModel body);
}
