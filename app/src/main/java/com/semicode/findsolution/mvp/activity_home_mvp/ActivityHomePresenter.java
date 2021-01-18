package com.semicode.findsolution.mvp.activity_home_mvp;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.FragmentManager;

import com.semicode.findsolution.R;
import com.semicode.findsolution.models.AllCatogryModel;
import com.semicode.findsolution.models.Slider_Model;
import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.preferences.Preferences;
import com.semicode.findsolution.remote.Api;
import com.semicode.findsolution.tags.Tags;
import com.semicode.findsolution.ui.activity_home.fragments.Fragment_Home;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityHomePresenter {
    private final FragmentManager fragmentManager;
    private UserModel userModel;
    private Preferences preferences;
    private HomeActivityView view;
    private Context context;
    private Fragment_Home fragment_home;

    public ActivityHomePresenter(HomeActivityView view, Context context, FragmentManager fragmentManager) {
        this.view = view;
        this.context = context;
        this.fragmentManager=fragmentManager;
        displayFragmentHome();
    }

    public void backPress() {

        view.onFinished();


    }
    private void displayFragmentHome(){
        if (fragment_home==null){
            fragment_home = Fragment_Home.newInstance();
        }

//        if (fragment_appointment!=null&&fragment_appointment.isAdded()){
//            fragmentManager.beginTransaction().hide(fragment_appointment).commit();
//        }
//
//        if (fragment_medicine!=null&&fragment_medicine.isAdded()){
//            fragmentManager.beginTransaction().hide(fragment_medicine).commit();
//        }
//
//
//        if (fragment_more!=null&&fragment_more.isAdded()){
//            fragmentManager.beginTransaction().hide(fragment_more).commit();
//        }

        if (fragment_home.isAdded()){
            fragmentManager.beginTransaction().show(fragment_home).commit();
        }else {
            fragmentManager.beginTransaction().add(R.id.fragment_container_view_tag,fragment_home,"fragment_home").commit();
        }
    }





}
