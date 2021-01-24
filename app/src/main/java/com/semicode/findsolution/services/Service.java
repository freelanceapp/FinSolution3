package com.semicode.findsolution.services;


import com.semicode.findsolution.models.AllAdvisorModel;
import com.semicode.findsolution.models.AllCatogryModel;
import com.semicode.findsolution.models.AllPackgesModel;
import com.semicode.findsolution.models.AllSubCatogryModel;
import com.semicode.findsolution.models.PlaceGeocodeData;
import com.semicode.findsolution.models.SettingModel;
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

    @GET("geocode/json")
    Call<PlaceGeocodeData> getGeoData(@Query(value = "latlng") String latlng,
                                      @Query(value = "language") String language,
                                      @Query(value = "key") String key);

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

    @GET("api/app/info")
    Call<SettingModel> getSetting();

    @FormUrlEncoded
    @POST("api/contactUs")
    Call<ResponseBody> contactUs(@Field("name") String name,
                                 @Field("email") String email,
                                 @Field("subject") String subject,
                                 @Field("message") String message


    );


    @GET("api/allRegisterPackages")
    Call<AllPackgesModel> getpakges(


    );

    @FormUrlEncoded
    @POST("api/adviserRegister")
    Call<UserModel> advisorregister(@Field("phone_code") String phone_code,
                                    @Field("phone") String phone,
                                    @Field("name") String name,
                                    @Field("software_type") String software_type,
                                    @Field("category_id") String category_id,
                                    @Field("sub_category_id") String sub_category_id,
                                    @Field("current_package_id") String current_package_id,
                                    @Field("work_title") String work_title,
                                    @Field("more_details") String more_details,
                                    @Field("contact_number") String contact_number,
                                    @Field("whatsapp_number") String whatsapp_number,
                                    @Field("address") String address,
                                    @Field("latitude") String latitude,
                                    @Field("longitude") String longitude




    );
    @Multipart
    @POST("api/ClientRegister")
    Call<UserModel> signup(@Part("phone_code") RequestBody phone_code,
                           @Part("phone") RequestBody phone,
                           @Part("name") RequestBody name,
                           @Part("software_type") RequestBody software_type,
                           @Part("category_id") RequestBody category_id,
                           @Part("sub_category_id") RequestBody sub_category_id,
                           @Part("current_package_id") RequestBody current_package_id,
                           @Part("work_title") RequestBody work_title,
                           @Part("more_details") RequestBody more_details,
                           @Part("contact_number") RequestBody contact_number,
                           @Part("whatsapp_number") RequestBody whatsapp_number,
                           @Part("address") RequestBody address,
                           @Part("latitude") RequestBody latitude,
                           @Part("longitude") RequestBody longitude,

                           @Part MultipartBody.Part image


    );

    @FormUrlEncoded
    @POST("api/userDetails")
    Call<UserModel> getprofile(@Field("user_id") int user_id

    );

}