package com.semicode.findsolution.models;

import java.io.Serializable;

public class SingleUserModel  implements Serializable {
    private int id;
    private String user_type;
    private String name;
    private String email;
    private String phone_code;
    private String phone;
    private String logo;
    private String address;
    private double latitude;
    private double longitude;
    private String work_title;
    private String more_details;
    private String contact_number;
    private String whatsapp_number;
    private String current_package_id;
    private String package_started_at;
    private String package_finished_at;
    private String payment_status;
    private String is_confirmed;
    private String is_block;
    private String is_login;
    private String logout_time;
    private String software_type;
    private String banner;
    private double rating;
    private String email_verified_at;
    private String deleted_at;
    private String created_at;
    private String updated_at;
    private String token;
    private SingleCategoryModel category;
    private SingleSubCategoryModel sub_category;
   private String success_url;
    private String canceled_url;
    private String declined_url;
    private String url;
    public int getId() {
        return id;
    }

    public String getUser_type() {
        return user_type;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public String getPhone() {
        return phone;
    }

    public String getLogo() {
        return logo;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getWork_title() {
        return work_title;
    }

    public String getMore_details() {
        return more_details;
    }

    public String getContact_number() {
        return contact_number;
    }

    public String getWhatsapp_number() {
        return whatsapp_number;
    }

    public String getCurrent_package_id() {
        return current_package_id;
    }

    public String getPackage_started_at() {
        return package_started_at;
    }

    public String getPackage_finished_at() {
        return package_finished_at;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public String getIs_confirmed() {
        return is_confirmed;
    }

    public String getIs_block() {
        return is_block;
    }

    public String getIs_login() {
        return is_login;
    }

    public String getLogout_time() {
        return logout_time;
    }

    public String getSoftware_type() {
        return software_type;
    }

    public String getBanner() {
        return banner;
    }

    public double getRating() {
        return rating;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getToken() {
        return token;
    }

    public SingleCategoryModel getCategory() {
        return category;
    }

    public SingleSubCategoryModel getSub_category() {
        return sub_category;
    }

    public String getSuccess_url() {
        return success_url;
    }

    public String getCanceled_url() {
        return canceled_url;
    }

    public String getDeclined_url() {
        return declined_url;
    }

    public String getUrl() {
        return url;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
