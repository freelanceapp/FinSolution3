package com.semicode.findsolution.mvp.fragment_home_mvp;

import com.semicode.findsolution.models.AllCatogryModel;
import com.semicode.findsolution.models.Slider_Model;

import java.util.List;

public interface HomeFragmentView {
    void onProgressShow();
    void onProgressHide();
    void onFailed(String msg);
    void onProgressSliderShow();
    void onProgressSliderHide();
    void onSliderSuccess(List<Slider_Model.Data> sliderModelList);

    void onSuccess(AllCatogryModel body);
}
