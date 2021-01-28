package com.semicode.findsolution.mvp.activity_sign_up_mvp;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.semicode.findsolution.R;

import com.semicode.findsolution.models.SignUpModel;
import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.remote.Api;
import com.semicode.findsolution.share.Common;
import com.semicode.findsolution.tags.Tags;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySignUpPresenter {
    private Context context;
    private ActivitySignUpView view;


    public ActivitySignUpPresenter(Context context, ActivitySignUpView view) {
        this.context = context;
        this.view = view;


    }



    public void checkData(SignUpModel signUpModel) {
        if (signUpModel.isDataValid(context)) {
            if (signUpModel.getImageUrl().isEmpty()) {
                sign_up_without_image(signUpModel);
            } else {
                sign_up_with_image(signUpModel);

            }
        }
    }



    private void sign_up_with_image(SignUpModel signUpModel) {
        RequestBody name_part = Common.getRequestBodyText(signUpModel.getName());
        RequestBody phone_code_part = Common.getRequestBodyText(signUpModel.getPhone_code());
        RequestBody phone_part = Common.getRequestBodyText(signUpModel.getPhone());


        RequestBody soft_part = Common.getRequestBodyText("android");



        MultipartBody.Part image_form_part = Common.getMultiPart(context, Uri.parse(signUpModel.getImageUrl()), "logo");
        view.onLoad();
        Api.getService(Tags.base_url)
                .signUpWithImage(phone_code_part, phone_part, name_part,  soft_part, image_form_part)
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
                            }
                        } else {
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

    private void sign_up_without_image(SignUpModel signUpModel) {

        view.onLoad();
        Api.getService(Tags.base_url)
                .signUpWithoutImage(signUpModel.getPhone_code(), signUpModel.getPhone(), signUpModel.getName(), "android")
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


}
