package com.semicode.findsolution.ui.activity_home.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.semicode.findsolution.R;
import com.semicode.findsolution.databinding.FragmentWebViewBinding;
import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.preferences.Preferences;
import com.semicode.findsolution.ui.activity_home.HomeActivity;

import io.paperdb.Paper;


public class FragmentAboutUs extends Fragment  {
    private FragmentWebViewBinding binding;
    private HomeActivity activity;

    private String lang;
    private Preferences preferences;
    private UserModel userModel;

    public static FragmentAboutUs newInstance() {

        FragmentAboutUs fragmentAboutUs = new FragmentAboutUs();
        return fragmentAboutUs;
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
