package com.semicode.findsolution.models;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.semicode.findsolution.BR;
import com.semicode.findsolution.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SignUpModel extends BaseObservable implements Serializable {
    private String imageUrl;
    private String name;

    private String phone_code;
    private String phone;

    public ObservableField<String> error_name = new ObservableField<>();



    public SignUpModel() {
        this.phone_code = phone_code;
        this.phone = phone;
        this.imageUrl="";
        this.name = "";

    }

    public boolean isDataValid(Context context){
        if (!name.isEmpty()

        ){

            error_name.set(null);


                return true;

        }else {
            if (name.isEmpty()){
                error_name.set(context.getString(R.string.field_req));
            }else {
                error_name.set(null);
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
}
