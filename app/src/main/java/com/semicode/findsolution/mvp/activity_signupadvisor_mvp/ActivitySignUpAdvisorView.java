package com.semicode.findsolution.mvp.activity_signupadvisor_mvp;

import com.semicode.findsolution.models.PlaceGeocodeData;
import com.semicode.findsolution.models.UserModel;

public interface ActivitySignUpAdvisorView {

    void onFailed(String msg);
    void onLoad();
    void onFinishload();
    void onSignupValid(UserModel userModel);
    void onFailed();
    void onServer();

    void onnotconnect(String msg);

    void onaddress(PlaceGeocodeData body);
}
