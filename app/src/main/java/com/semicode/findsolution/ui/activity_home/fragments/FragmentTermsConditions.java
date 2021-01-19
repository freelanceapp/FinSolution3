package com.semicode.findsolution.ui.activity_home.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.semicode.findsolution.R;
import com.semicode.findsolution.adapters.Category_Adapter;
import com.semicode.findsolution.adapters.SliderAdapter;
import com.semicode.findsolution.databinding.FragmentHomeBinding;
import com.semicode.findsolution.databinding.FragmentWebViewBinding;
import com.semicode.findsolution.models.AllCatogryModel;
import com.semicode.findsolution.models.SingleCategoryModel;
import com.semicode.findsolution.models.Slider_Model;
import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.mvp.fragment_home_mvp.FragmentHomePresenter;
import com.semicode.findsolution.mvp.fragment_home_mvp.HomeFragmentView;
import com.semicode.findsolution.preferences.Preferences;
import com.semicode.findsolution.ui.activity_department_detials.DepartmentDetialsActivity;
import com.semicode.findsolution.ui.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;


public class FragmentTermsConditions extends Fragment  {
    private FragmentWebViewBinding binding;
    private HomeActivity activity;

    private String lang;
    private Preferences preferences;
    private UserModel userModel;

    public static FragmentTermsConditions newInstance() {

        FragmentTermsConditions fragmentTermsConditions = new FragmentTermsConditions();
        return fragmentTermsConditions;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_web_view, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {

        preferences = Preferences.getInstance();
        activity=(HomeActivity)getActivity(); 
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        lang = Paper.book().read("lang", "ar");

    }


}
