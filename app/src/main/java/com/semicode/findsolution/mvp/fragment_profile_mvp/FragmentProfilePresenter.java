package com.semicode.findsolution.mvp.fragment_profile_mvp;

import android.content.Context;
import android.util.Log;

import com.semicode.findsolution.R;
import com.semicode.findsolution.models.ContactUsModel;
import com.semicode.findsolution.models.SettingModel;
import com.semicode.findsolution.models.SingleUserModel;
import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.remote.Api;
import com.semicode.findsolution.tags.Tags;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentProfilePresenter {
    private Context context;
    private FragmentProfileView view;


    public FragmentProfilePresenter(Context context, FragmentProfileView view) {
        this.context = context;
        this.view = view;

    }





    public void getprofile(SingleUserModel singleUserModel) {
        view.onLoad();

        Api.getService(Tags.base_url).getprofile(singleUserModel.getId())
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        view.onFinishload();
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if(response.body().getStatus()==200){
                                view.onprofile(response.body());}
                                else {
                                    view.onFailed(context.getResources().getString(R.string.failed));
                                }

                            }
                        } else {
                            // Log.e("xxxxx", settingModel.getSettings().getAbout_app_link() + "----");


                            view.onFinishload();
                            try {
                                Log.e("error_code", response.code() + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            view.onFinishload();

                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {

                                    view.onFailed(context.getResources().getString(R.string.something));
                                    // Toast.makeText(AboutAppActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else if (t.getMessage().toLowerCase().contains("socket") || t.getMessage().toLowerCase().contains("canceled")) {
                                } else {
                                    view.onFailed(context.getResources().getString(R.string.failed));

                                    //Toast.makeText(AboutAppActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }


                        } catch (Exception e) {

                        }
                    }
                });
    }


}
