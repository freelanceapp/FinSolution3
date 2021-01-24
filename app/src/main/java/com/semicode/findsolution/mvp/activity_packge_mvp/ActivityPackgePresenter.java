package com.semicode.findsolution.mvp.activity_packge_mvp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.semicode.findsolution.R;
import com.semicode.findsolution.models.AllPackgesModel;
import com.semicode.findsolution.models.AllPackgesModel;
import com.semicode.findsolution.models.AllSubCatogryModel;
import com.semicode.findsolution.models.SignUpAdvisorModel;
import com.semicode.findsolution.models.SignUpModel;
import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.preferences.Preferences;
import com.semicode.findsolution.remote.Api;
import com.semicode.findsolution.share.Common;
import com.semicode.findsolution.tags.Tags;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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


    public void getpackge() {
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
                                Log.e("error_code", response.code() + response.errorBody().string());
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
                .advisorregister(signUpModel.getPhone_code(), signUpModel.getPhone(), signUpModel.getName(), "android", signUpModel.getCategory_id() + "", signUpModel.getSub_category_id() + "", signUpModel.getCurrent_package_id(), signUpModel.getWork_title(), signUpModel.getMore_details(), signUpModel.getContact_number(), signUpModel.getWhatsapp_number(), signUpModel.getAddress(), signUpModel.getLatitude() + "", signUpModel.getLongitude() + "")
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        view.onFinishload();
                        if (response.isSuccessful() && response.body() != null) {
                            //  Log.e("eeeeee", response.body().getUser().getName());
                            if (response.body().getStatus() == 200) {
                                view.onSignupValid(response.body());
                            } else if (response.body().getStatus() == 402) {
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

    private void sign_up_with_image(SignUpAdvisorModel signUpModel) {
        RequestBody name_part = Common.getRequestBodyText(signUpModel.getName());
        RequestBody phone_code_part = Common.getRequestBodyText(signUpModel.getPhone_code());
        RequestBody phone_part = Common.getRequestBodyText(signUpModel.getPhone());
        RequestBody cat_part = Common.getRequestBodyText(signUpModel.getCategory_id() + "");
        RequestBody sub_cat_part = Common.getRequestBodyText(signUpModel.getSub_category_id() + "");
        RequestBody packge_part = Common.getRequestBodyText(signUpModel.getCurrent_package_id() + "");
        RequestBody work_part = Common.getRequestBodyText(signUpModel.getWork_title() + "");
        RequestBody detials_part = Common.getRequestBodyText(signUpModel.getMore_details() + "");
        RequestBody contact_part = Common.getRequestBodyText(signUpModel.getContact_number() + "");
        RequestBody whats_part = Common.getRequestBodyText(signUpModel.getWhatsapp_number() + "");
        RequestBody address_part = Common.getRequestBodyText(signUpModel.getAddress() + "");
        RequestBody lat_part = Common.getRequestBodyText(signUpModel.getLatitude() + "");
        RequestBody long_part = Common.getRequestBodyText(signUpModel.getLongitude() + "");


        RequestBody soft_part = Common.getRequestBodyText("android");


        MultipartBody.Part image_form_part = Common.getMultiPart(context, Uri.parse(signUpModel.getImageUrl()), "logo");
        view.onLoad();
        Api.getService(Tags.base_url)
                .signup(phone_code_part, phone_part, name_part, soft_part, cat_part, sub_cat_part, packge_part, work_part, detials_part, contact_part, whats_part, address_part, lat_part, long_part, image_form_part)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        view.onFinishload();
                        if (response.isSuccessful() && response.body() != null) {
                            //  Log.e("eeeeee", response.body().getUser().getName());
                            if (response.body().getStatus() == 200) {
                                view.onSignupValid(response.body());
                            } else if (response.body().getStatus() == 402) {
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
                            if (response.body().getStatus() == 200) {
                                view.profile(response.body());
                            } else if (response.body().getStatus() == 402) {
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

    public void checkdata(SignUpAdvisorModel signUpAdvisorModel) {
        if (signUpAdvisorModel.getImageUrl() != null) {
            sign_up_with_image(signUpAdvisorModel);
        } else {
            sign_up_without_image(signUpAdvisorModel);
        }
    }
}
