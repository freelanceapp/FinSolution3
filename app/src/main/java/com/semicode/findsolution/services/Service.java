package com.semicode.findsolution.services;


import com.semicode.findsolution.models.AllAdvisorModel;
import com.semicode.findsolution.models.AllCatogryModel;
import com.semicode.findsolution.models.AllSubCatogryModel;
import com.semicode.findsolution.models.Slider_Model;
import com.semicode.findsolution.models.UserModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Service {


    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> login(@Field("phone_code") String phone_code,
                          @Field("phone") String phone

    );


    @FormUrlEncoded
    @POST("api/ClientRegister")
    Call<UserModel> signup(@Field("phone_code") String phone_code,
                           @Field("phone") String phone,
                           @Field("name") String name,
                           @Field("software_type") String software_type


    );

    @Multipart
    @POST("api/ClientRegister")
    Call<UserModel> signup(@Part("phone_code") RequestBody phone_code,
                           @Part("phone") RequestBody phone,
                           @Part("name") RequestBody name,
                           @Part("software_type") RequestBody software_type,
                           @Part MultipartBody.Part image


    );

    @GET("api/sliders")
    Call<Slider_Model> get_slider();

    @GET("api/allCategories")
    Call<AllCatogryModel> getcategories();

    @FormUrlEncoded
    @POST("api/getSubCategoriesByCategory")
    Call<AllSubCatogryModel> getsubcategories(
            @Field("category_id") String category_id


    );

    @FormUrlEncoded
    @POST("api/subCategoryFilter")
    Call<AllAdvisorModel> getsubcategoryData(
            @Field("category_id") String category_id,
            @Field("sub_category_id") String sub_category_id


    );

}