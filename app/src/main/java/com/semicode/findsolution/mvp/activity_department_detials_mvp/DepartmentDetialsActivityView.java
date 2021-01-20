package com.semicode.findsolution.mvp.activity_department_detials_mvp;


import com.semicode.findsolution.models.AllAdvisorModel;
import com.semicode.findsolution.models.AllSubCatogryModel;

public interface DepartmentDetialsActivityView {
    void onFinished();


    void onProgressShow();
    void onProgressHide();
    void onFailed(String msg);
    void onSuccess(AllSubCatogryModel allCategoryModel);


    void onLoad();
    void onFinishload();

    void getalladvisor(AllAdvisorModel body);
}
