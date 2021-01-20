package com.semicode.findsolution.mvp.activity_packge_mvp;


import com.semicode.findsolution.models.AllAdvisorModel;
import com.semicode.findsolution.models.AllPackgesModel;
import com.semicode.findsolution.models.AllSubCatogryModel;
import com.semicode.findsolution.models.UserModel;

public interface PackgeActivityView {
    void onFinished();


    void onProgressShow();
    void onProgressHide();
    void onFailed(String msg);


    void onLoad();
    void onFinishload();

    void getallpackges(AllPackgesModel body);
    void onSignupValid(UserModel userModel);
    void onFailed();
    void onServer();

    void onnotconnect(String msg);

    void profile(UserModel body);
}
