package com.semicode.findsolution.mvp.activity_packge_mvp;

import android.content.Context;
import android.util.Log;

import com.semicode.findsolution.R;
import com.semicode.findsolution.models.AllPackgesModel;
import com.semicode.findsolution.models.AllPackgesModel;
import com.semicode.findsolution.models.AllSubCatogryModel;
import com.semicode.findsolution.models.SignUpAdvisorModel;
import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.preferences.Preferences;
import com.semicode.findsolution.remote.Api;
import com.semicode.findsolution.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPackgePresenter {
    private UserModel userModel;
    private Preferences preferences;
    private PackgeActivityView view;
    private Context context;

    public ActivityPackgePresenter(PackgeActivityView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void backPress() {

        view.onFinished();


    }


    public void getpackge()
    {
        view.onProgressShow();

        Api.getService(Tags.base_url)
                .getpakges()
                .enqueue(new Callback<AllPackgesModel>() {
                    @Override
                    public void onResponse(Call<AllPackgesModel> call, Response<AllPackgesModel> response) {
                        view.onProgressHide();
                        if (response.isSuccessful() && response.body() != null) {
                            view.getallpackges(response.body());
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
                    public void onFailure(Call<AllPackgesModel> call, Throwable t) {
                        try {
                            view.onProgressHide();
                            view.onFailed(context.getString(R.string.something));
                            Log.e("Error", t.getMessage());
                        } catch (Exception e) {

                        }
                    }
                });
    }

    public void sign_up_without_image(SignUpAdvisorModel signUpModel) {

        view.onLoad();
        Api.getService(Tags.base_url)
                .advisorregister(signUpModel.getPhone_code(), signUpModel.getPhone(), signUpModel.getName(), "android",signUpModel.getCategory_id()+"",signUpModel.getSub_category_id()+"",signUpModel.getCurrent_package_id(),signUpModel.getWork_title(),signUpModel.getMore_details(),signUpModel.getContact_number(),signUpModel.getWhatsapp_number(),signUpModel.getAddress(),signUpModel.getLatitude()+"",signUpModel.getLongitude()+"")
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        view.onFinishload();
                        if (response.isSuccessful() && response.body() != null) {
                            //  Log.e("eeeeee", response.body().getUser().getName());
                            if(response.body().getStatus()==200){
                                view.onSignupValid(response.body());}
                            else if(response.body().getStatus()==402){
                                view.onFailed(context.getResources().getString(R.string.user_found));
                            }                        } else {
                            try {
                                Log.e("mmmmmmmmmmssss", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            if (response.code() == 500) {
                                view.onServer();
                            } else {

                                view.onFailed(response.message() + "");
                                //  Toast.makeText(VerificationCodeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            view.onFinishload();
                            if (t.getMessage() != null) {
                                Log.e("msg_category_error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onnotconnect(t.getMessage().toLowerCase());
                                    //  Toast.makeText(VerificationCodeActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    view.onFailed();
                                    // Toast.makeText(VerificationCodeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });


    }
    public void getprofile(int id) {

        view.onLoad();
        Api.getService(Tags.base_url)
                .getprofile(id)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        view.onFinishload();
                        if (response.isSuccessful() && response.body() != null) {
                            //  Log.e("eeeeee", response.body().getUser().getName());
                            if(response.body().getStatus()==200){
                                view.profile(response.body());}
                            else if(response.body().getStatus()==402){
                                view.onFailed(context.getResources().getString(R.string.user_found));
                            }                        } else {
                            try {
                                Log.e("mmmmmmmmmmssss", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            if (response.code() == 500) {
                                view.onServer();
                            } else {

                                view.onFailed(response.message() + "");
                                //  Toast.makeText(VerificationCodeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            view.onFinishload();
                            if (t.getMessage() != null) {
                                Log.e("msg_category_error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onnotconnect(t.getMessage().toLowerCase());
                                    //  Toast.makeText(VerificationCodeActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    view.onFailed();
                                    // Toast.makeText(VerificationCodeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });


    }

}
