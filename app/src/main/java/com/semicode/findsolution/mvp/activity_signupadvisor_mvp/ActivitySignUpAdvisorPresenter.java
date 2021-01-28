package com.semicode.findsolution.mvp.activity_signupadvisor_mvp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.semicode.findsolution.R;
import com.semicode.findsolution.models.AllCatogryModel;
import com.semicode.findsolution.models.AllSubCatogryModel;
import com.semicode.findsolution.models.PlaceGeocodeData;
import com.semicode.findsolution.models.SignUpAdvisorModel;
import com.semicode.findsolution.models.SignUpModel;
import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.remote.Api;
import com.semicode.findsolution.share.Common;
import com.semicode.findsolution.tags.Tags;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySignUpAdvisorPresenter {
    private Context context;
    private ActivitySignUpAdvisorView view;


    public ActivitySignUpAdvisorPresenter(Context context, ActivitySignUpAdvisorView view) {
        this.context = context;
        this.view = view;


    }



    public void checkData(SignUpAdvisorModel signUpModel) {


        if (signUpModel.isDataValid(context)) {
            if (signUpModel.getImageUrl().isEmpty()) {
               // sign_up_without_image(signUpModel);
                view.choosepackges();
            } else {
                //sign_up_with_image(signUpModel);
//
            }
        }
    }

    public void checkData2(SignUpAdvisorModel signUpModel) {
        if (signUpModel.isDataValid(context)) {
            if (signUpModel.getImageUrl().isEmpty()) {
                // sign_up_without_image(signUpModel);
                view.profile();
            } else {
                //sign_up_with_image(signUpModel);
//
            }
        }
    }

    public void getsubcategories(int catid)
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
    public void getcategories()
    {
        // Log.e("tjtjtj",userModel.getIs_confirmed());
        view.onLoad();

        Api.getService(Tags.base_url)
                .getcategories()
                .enqueue(new Callback<AllCatogryModel>() {
                    @Override
                    public void onResponse(Call<AllCatogryModel> call, Response<AllCatogryModel> response) {
                        view.onFinishload();
                        if (response.isSuccessful() && response.body() != null) {
                            view.onSuccesscategory(response.body());
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
                    public void onFailure(Call<AllCatogryModel> call, Throwable t) {
                        try {
                            view.onFinishload();
                            view.onFailed(context.getString(R.string.something));
                            Log.e("Error", t.getMessage());
                        } catch (Exception e) {

                        }
                    }
                });
    }

//    private void sign_up_with_image(SignUpModel signUpModel) {
//        RequestBody name_part = Common.getRequestBodyText(signUpModel.getName());
//        RequestBody phone_code_part = Common.getRequestBodyText(signUpModel.getPhone_code());
//        RequestBody phone_part = Common.getRequestBodyText(signUpModel.getPhone());
//
//
//        RequestBody soft_part = Common.getRequestBodyText("android");
//
//
//
//        MultipartBody.Part image_form_part = Common.getMultiPart(context, Uri.parse(signUpModel.getImageUrl()), "logo");
//        view.onLoad();
//        Api.getService(Tags.base_url)
//                .signup(phone_code_part, phone_part, name_part,  soft_part, image_form_part)
//                .enqueue(new Callback<UserModel>() {
//                    @Override
//                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
//                        view.onFinishload();
//                        if (response.isSuccessful() && response.body() != null) {
//                            //  Log.e("eeeeee", response.body().getUser().getName());
//                            if(response.body().getStatus()==200){
//                            view.onSignupValid(response.body());}
//                            else if(response.body().getStatus()==402){
//                                view.onFailed(context.getResources().getString(R.string.user_found));
//                            }
//                        } else {
//                            try {
//                                Log.e("mmmmmmmmmmssss", response.errorBody().string());
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//
//                            if (response.code() == 500) {
//                                view.onServer();
//                            } else {
//
//                                    view.onFailed(response.message() + "");
//                                                               //  Toast.makeText(VerificationCodeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<UserModel> call, Throwable t) {
//                        try {
//                            view.onFinishload();
//                            if (t.getMessage() != null) {
//                                Log.e("msg_category_error", t.getMessage() + "__");
//
//                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
//                                    view.onnotconnect(t.getMessage().toLowerCase());
//                                    //  Toast.makeText(VerificationCodeActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
//                                } else {
//                                    view.onFailed();
//                                    // Toast.makeText(VerificationCodeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        } catch (Exception e) {
//                            Log.e("Error", e.getMessage() + "__");
//                        }
//                    }
//                });
//    }


    public void getGeoData(final double lat, double lng, String lang) {
        view.onLoad();
        String location = lat + "," + lng;
        Api.getService("https://maps.googleapis.com/maps/api/")
                .getGeoData(location, lang, context.getResources().getString(R.string.google_api_key))
                .enqueue(new Callback<PlaceGeocodeData>() {
                    @Override
                    public void onResponse(Call<PlaceGeocodeData> call, Response<PlaceGeocodeData> response) {
                        view.onFinishload();
                        if (response.isSuccessful() && response.body() != null) {

                            if (response.body().getResults().size() > 0) {
                                view.onaddress(response.body());
                            }
                        } else {

                            try {
                                Log.e("error_code", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<PlaceGeocodeData> call, Throwable t) {
                        try {
                            view.onFinishload();
                            view.onFailed(t.getMessage());
                        } catch (Exception e) {

                        }
                    }
                });
    }

}
