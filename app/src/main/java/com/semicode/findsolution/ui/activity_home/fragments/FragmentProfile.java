package com.semicode.findsolution.ui.activity_home.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.semicode.findsolution.R;
import com.semicode.findsolution.databinding.FragmentContactusBinding;
import com.semicode.findsolution.databinding.FragmentProfileBinding;
import com.semicode.findsolution.models.ContactUsModel;
import com.semicode.findsolution.models.SettingModel;
import com.semicode.findsolution.models.SingleUserModel;
import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.mvp.fragment_contactus_mvp.FragmentContactusPresenter;
import com.semicode.findsolution.mvp.fragment_contactus_mvp.FragmentContactusView;
import com.semicode.findsolution.mvp.fragment_profile_mvp.FragmentProfilePresenter;
import com.semicode.findsolution.mvp.fragment_profile_mvp.FragmentProfileView;
import com.semicode.findsolution.preferences.Preferences;
import com.semicode.findsolution.share.Common;
import com.semicode.findsolution.ui.activity_advisor_signup.SignUpAdvisorActivity;
import com.semicode.findsolution.ui.activity_home.HomeActivity;
import com.semicode.findsolution.ui.activity_sign_up.SignUpActivity;

import io.paperdb.Paper;


public class FragmentProfile extends Fragment implements FragmentProfileView {
    private FragmentProfileBinding binding;
    private HomeActivity activity;

    private String lang;
    private Preferences preferences;
    private UserModel userModel;
    private SingleUserModel singleUserModel;
    private FragmentProfilePresenter presenter;
    private ProgressDialog dialog2;

    public static FragmentProfile newInstance() {

        FragmentProfile fragmentProfile = new FragmentProfile();
        return fragmentProfile;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        preferences = Preferences.getInstance();
        activity = (HomeActivity) getActivity();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
      //  binding.setModel(userModel.getData());
        lang = Paper.book().read("lang", "ar");
        presenter = new FragmentProfilePresenter(activity, this);
        presenter.getprofile(userModel.getData());
        binding.flEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userModel != null) {
                    Intent intent = new Intent(activity, SignUpAdvisorActivity.class);
                    startActivityForResult(intent, 400);
                }
            }
        });
    }


    @Override
    public void onLoad() {
        dialog2 = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog2.setCancelable(false);
        dialog2.show();
    }

    @Override
    public void onFinishload() {
        dialog2.dismiss();
    }


    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onnotconnect(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onprofile(UserModel body) {
        binding.setModel(body.getData());
    }


    @Override
    public void onFailed() {
        Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServer() {
        Toast.makeText(activity, getString(R.string.server_error), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            userModel = preferences.getUserData(activity);
            binding.setModel(singleUserModel);

        } else if (requestCode == 400 && resultCode == Activity.RESULT_OK) {
            userModel = preferences.getUserData(activity);
            binding.setModel(userModel.getData());
        }
    }

}
