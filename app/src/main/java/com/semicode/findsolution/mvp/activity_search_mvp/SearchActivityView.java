package com.semicode.findsolution.mvp.activity_search_mvp;


import com.semicode.findsolution.models.AllAdvisorModel;

public interface SearchActivityView {
    void onFinished();
    void onProgressShow();
    void onProgressHide();
    void onFailed(String msg);


    void searchsuc(AllAdvisorModel body);
}
