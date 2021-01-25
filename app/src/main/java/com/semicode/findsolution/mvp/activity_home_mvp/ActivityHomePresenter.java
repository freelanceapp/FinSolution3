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
import com.semicode.findsolution.ui.activity_home.fragments.FragmentAboutUs;
import com.semicode.findsolution.ui.activity_home.fragments.FragmentContactUs;
import com.semicode.findsolution.ui.activity_home.fragments.FragmentProfile;
import com.semicode.findsolution.ui.activity_home.fragments.FragmentTermsConditions;
import com.semicode.findsolution.ui.activity_home.fragments.FragmentUSerProfile;
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
    private FragmentTermsConditions fragmentTermsConditions;
    private FragmentAboutUs fragmentAboutUs;
    private FragmentContactUs fragmentContactUs;
    private FragmentProfile fragmentProfile;
    private FragmentUSerProfile fragmentUSerProfile;

    public ActivityHomePresenter(HomeActivityView view, Context context, FragmentManager fragmentManager) {
        this.view = view;
        this.context = context;
        this.fragmentManager = fragmentManager;
        displayFragmentHome();
    }

    public void backPress() {

        view.onFinished();


    }

    public void displayFragmentHome() {
        if (fragment_home == null) {
            fragment_home = Fragment_Home.newInstance();
        }

        if (fragmentAboutUs != null && fragmentAboutUs.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentAboutUs).commit();
        }

        if (fragmentTermsConditions != null && fragmentTermsConditions.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentTermsConditions).commit();
        }
        if (fragmentContactUs != null && fragmentContactUs.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentContactUs).commit();
        }
        if (fragmentProfile != null && fragmentProfile.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentProfile).commit();
        }
        if (fragmentUSerProfile != null && fragmentUSerProfile.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentUSerProfile).commit();
        }
//
//
//        if (fragment_more!=null&&fragment_more.isAdded()){
//            fragmentManager.beginTransaction().hide(fragment_more).commit();
//        }

        if (fragment_home.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_home).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_container_view_tag, fragment_home, "fragment_home").commit();
        }
    }


    public void displayFragmentTerms() {
        if (fragmentTermsConditions == null) {
            fragmentTermsConditions = FragmentTermsConditions.newInstance();
        }

        if (fragmentAboutUs != null && fragmentAboutUs.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentAboutUs).commit();
        }

        if (fragment_home != null && fragment_home.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_home).commit();
        }
        if (fragmentContactUs != null && fragmentContactUs.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentContactUs).commit();
        }
        if (fragmentProfile != null && fragmentProfile.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentProfile).commit();
        }
        if (fragmentUSerProfile != null && fragmentUSerProfile.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentUSerProfile).commit();
        }
//
//
//        if (fragment_more!=null&&fragment_more.isAdded()){
//            fragmentManager.beginTransaction().hide(fragment_more).commit();
//        }

        if (fragmentTermsConditions.isAdded()) {
            fragmentManager.beginTransaction().show(fragmentTermsConditions).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_container_view_tag, fragmentTermsConditions, "fragmentTermsConditions").commit();
        }
    }

    public void displayFragmentAboutus() {
        if (fragmentAboutUs == null) {
            fragmentAboutUs = FragmentAboutUs.newInstance();
        }

        if (fragmentTermsConditions != null && fragmentTermsConditions.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentTermsConditions).commit();
        }

        if (fragment_home != null && fragment_home.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_home).commit();
        }
        if (fragmentContactUs != null && fragmentContactUs.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentContactUs).commit();
        }
        if (fragmentProfile != null && fragmentProfile.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentProfile).commit();
        }
        if (fragmentUSerProfile != null && fragmentUSerProfile.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentUSerProfile).commit();
        }
//
//
//        if (fragment_more!=null&&fragment_more.isAdded()){
//            fragmentManager.beginTransaction().hide(fragment_more).commit();
//        }

        if (fragmentAboutUs.isAdded()) {
            fragmentManager.beginTransaction().show(fragmentAboutUs).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_container_view_tag, fragmentAboutUs, "fragmentAboutUs").commit();
        }
    }

    public void displayFragmentContactus() {
        if (fragmentContactUs == null) {
            fragmentContactUs = FragmentContactUs.newInstance();
        }

        if (fragmentTermsConditions != null && fragmentTermsConditions.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentTermsConditions).commit();
        }

        if (fragment_home != null && fragment_home.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_home).commit();
        }
        if (fragmentAboutUs != null && fragmentAboutUs.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentAboutUs).commit();
        }
        if (fragmentProfile != null && fragmentProfile.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentProfile).commit();
        }
        if (fragmentUSerProfile != null && fragmentUSerProfile.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentUSerProfile).commit();
        }
//
//
//        if (fragment_more!=null&&fragment_more.isAdded()){
//            fragmentManager.beginTransaction().hide(fragment_more).commit();
//        }

        if (fragmentContactUs.isAdded()) {
            fragmentManager.beginTransaction().show(fragmentContactUs).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_container_view_tag, fragmentContactUs, "fragmentContactUs").commit();
        }
    }

    public void displayFragmentProfile() {
        if (fragmentProfile == null) {
            fragmentProfile = FragmentProfile.newInstance();
        }

        if (fragmentTermsConditions != null && fragmentTermsConditions.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentTermsConditions).commit();
        }

        if (fragment_home != null && fragment_home.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_home).commit();
        }
        if (fragmentAboutUs != null && fragmentAboutUs.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentAboutUs).commit();
        }
        if (fragmentContactUs != null && fragmentContactUs.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentContactUs).commit();
        }
        if (fragmentUSerProfile != null && fragmentUSerProfile.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentUSerProfile).commit();
        }
//
//
//        if (fragment_more!=null&&fragment_more.isAdded()){
//            fragmentManager.beginTransaction().hide(fragment_more).commit();
//        }

        if (fragmentProfile.isAdded()) {
            fragmentManager.beginTransaction().show(fragmentProfile).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_container_view_tag, fragmentProfile, "fragmentProfile").commit();
        }
    }
    public void displayFragmentUserProfile() {
        if (fragmentUSerProfile == null) {
            fragmentUSerProfile = FragmentUSerProfile.newInstance();
        }

        if (fragmentTermsConditions != null && fragmentTermsConditions.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentTermsConditions).commit();
        }

        if (fragment_home != null && fragment_home.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_home).commit();
        }
        if (fragmentAboutUs != null && fragmentAboutUs.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentAboutUs).commit();
        }
        if (fragmentContactUs != null && fragmentContactUs.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentContactUs).commit();
        }
        if (fragmentProfile != null && fragmentProfile.isAdded()) {
            fragmentManager.beginTransaction().hide(fragmentProfile).commit();
        }
//
//
//        if (fragment_more!=null&&fragment_more.isAdded()){
//            fragmentManager.beginTransaction().hide(fragment_more).commit();
//        }

        if (fragmentUSerProfile.isAdded()) {
            fragmentManager.beginTransaction().show(fragmentUSerProfile).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_container_view_tag, fragmentUSerProfile, "fragmentUSerProfile").commit();
        }
    }


}
