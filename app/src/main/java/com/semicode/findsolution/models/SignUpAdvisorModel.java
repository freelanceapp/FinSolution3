package com.semicode.findsolution.models;

import android.content.Context;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.semicode.findsolution.BR;
import com.semicode.findsolution.R;

import java.io.Serializable;

public class SignUpAdvisorModel extends BaseObservable implements Serializable {
    private String imageUrl;
    private String name;

    private String phone_code;
    private String phone;
    private int category_id;
    private int sub_category_id;
    private String current_package_id;
    private String work_title;
    private String more_details;
    private String contact_number;


    private String whatsapp_number;

    private String address;

    private double latitude;

    private double longitude;
    public ObservableField<String> error_name = new ObservableField<>();

    public ObservableField<String> error_work = new ObservableField<>();

    public SignUpAdvisorModel(String phone_code, String phone) {
        this.phone_code = phone_code;
        this.phone = phone;
        this.imageUrl = "";
        this.name = "";
        this.category_id=0;
        this.sub_category_id=0;
        this.current_package_id="";
        this.work_title="";
        this.more_details="";
        this.contact_number="";

    }

    public boolean isDataValid(Context context) {
        if (!name.isEmpty()&&category_id!=0&&sub_category_id!=0&&!work_title.isEmpty()

        ) {

            error_name.set(null);

            error_work.set(null);

            return true;

        } else {
            if (name.isEmpty()) {
                error_name.set(context.getString(R.string.field_req));
            } else {
                error_name.set(null);

            }

if(category_id==0){
    Toast.makeText(context,context.getResources().getString(R.string.choose_category),Toast.LENGTH_LONG).show();
}
if(sub_category_id==0){
    Toast.makeText(context,context.getResources().getString(R.string.choose_subcategory),Toast.LENGTH_LONG).show();

}
            if (work_title.isEmpty()) {
                error_work.set(context.getString(R.string.field_req));
            } else {
                error_work.set(null);
            }

            return false;
        }
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(int sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public String getCurrent_package_id() {
        return current_package_id;
    }

    public void setCurrent_package_id(String current_package_id) {
        this.current_package_id = current_package_id;
    }

    public String getWork_title() {
        return work_title;
    }

    public void setWork_title(String work_title) {
        this.work_title = work_title;
    }

    public String getMore_details() {
        return more_details;
    }

    public void setMore_details(String more_details) {
        this.more_details = more_details;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getWhatsapp_number() {
        return whatsapp_number;
    }

    public void setWhatsapp_number(String whatsapp_number) {
        this.whatsapp_number = whatsapp_number;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
