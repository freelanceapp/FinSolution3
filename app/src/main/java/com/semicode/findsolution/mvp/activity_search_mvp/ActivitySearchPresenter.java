package com.semicode.findsolution.mvp.activity_search_mvp;

import android.content.Context;
import android.util.Log;


import com.semicode.findsolution.R;
import com.semicode.findsolution.models.AllAdvisorModel;
import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.preferences.Preferences;
import com.semicode.findsolution.remote.Api;
import com.semicode.findsolution.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySearchPresenter {
    private UserModel userModel;
    private Preferences preferences;
    private SearchActivityView view;
    private Context context;

    public ActivitySearchPresenter(SearchActivityView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void backPress() {

        view.onFinished();


    }



    public void getdoctors(String name)
    {
        view.onProgressShow();

        Api.getService(Tags.base_url)
                .search(name)
                .enqueue(new Callback<AllAdvisorModel>() {
                    @Override
                    public void onResponse(Call<AllAdvisorModel> call, Response<AllAdvisorModel> response) {
                        view.onProgressHide();
                        if (response.isSuccessful() && response.body() != null) {
                            view.searchsuc(response.body());
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
