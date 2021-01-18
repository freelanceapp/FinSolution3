package com.semicode.findsolution.mvp.activity_products_sell_mvp;

import android.content.Context;
import android.util.Log;


import com.semicode.findsolution.R;
import com.semicode.findsolution.models.AllAdvisorModel;
import com.semicode.findsolution.models.AllSubCatogryModel;
import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.preferences.Preferences;
import com.semicode.findsolution.remote.Api;
import com.semicode.findsolution.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDepartmentDetialsPresenter {
    private UserModel userModel;
    private Preferences preferences;
    private DepartmentDetialsActivityView view;
    private Context context;

    public ActivityDepartmentDetialsPresenter(DepartmentDetialsActivityView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void backPress() {

        view.onFinished();


    }

    public void getcategories(UserModel userModel,int catid)
    {
        // Log.e("tjtjtj",userModel.getIs_confirmed());
        view.onLoad();

        Api.getService(Tags.base_url)
                .getsubcategories(catid+"")
                .enqueue(new Callback<AllSubCatogryModel>() {
                    @Override
                    public void onResponse(Call<AllSubCatogryModel> call, Response<AllSubCatogryModel> response) {
                        view.onFinishload();
                        if (response.isSuccessful() && response.body() != null) {
                            view.onSuccess(response.body());
                        } else {
                            view.onFinishload();
                            view.onFailed(context.getString(R.string.something));
                            try {
                                Log.e("error_codess",response.code()+ response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<AllSubCatogryModel> call, Throwable t) {
                        try {
                            view.onFinishload();
                            view.onFailed(context.getString(R.string.something));
                            Log.e("Error", t.getMessage());
                        } catch (Exception e) {

                        }
                    }
                });
    }
    public void getproducts(int catid,int subcat)
    {
        view.onProgressShow();

        Api.getService(Tags.base_url)
                .getsubcategoryData(catid+"",subcat+"")
                .enqueue(new Callback<AllAdvisorModel>() {
                    @Override
                    public void onResponse(Call<AllAdvisorModel> call, Response<AllAdvisorModel> response) {
                        view.onProgressHide();
                        if (response.isSuccessful() && response.body() != null) {
                            view.getalladvisor(response.body());
                        } else {
                            view.onProgressHide();
                            view.onFailed(context.getString(R.string.something));
                            try {
                                Log.e("error_code",response.code()+  response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<AllAdvisorModel> call, Throwable t) {
                        try {
                            view.onProgressHide();
                            view.onFailed(context.getString(R.string.something));
                            Log.e("Error", t.getMessage());
                        } catch (Exception e) {

                        }
                    }
                });
    }

}
