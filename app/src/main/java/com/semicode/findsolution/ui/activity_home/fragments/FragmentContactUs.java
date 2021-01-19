package com.semicode.findsolution.ui.activity_home.fragments;

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
import com.semicode.findsolution.databinding.FragmentWebViewBinding;
import com.semicode.findsolution.models.ContactUsModel;
import com.semicode.findsolution.models.SettingModel;
import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.mvp.fragment_contactus_mvp.FragmentContactusPresenter;
import com.semicode.findsolution.mvp.fragment_contactus_mvp.FragmentContactusView;
import com.semicode.findsolution.preferences.Preferences;
import com.semicode.findsolution.share.Common;
import com.semicode.findsolution.ui.activity_home.HomeActivity;

import io.paperdb.Paper;


public class FragmentContactUs extends Fragment  implements FragmentContactusView {
    private FragmentContactusBinding binding;
    private HomeActivity activity;

    private String lang;
    private Preferences preferences;
    private UserModel userModel;
    private ContactUsModel contactUsModel;
    private FragmentContactusPresenter presenter;
    private ProgressDialog dialog2;
    private SettingModel setting;
    private String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    public static FragmentContactUs newInstance() {

        FragmentContactUs fragmentContactUs = new FragmentContactUs();
        return fragmentContactUs;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contactus, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {

        preferences = Preferences.getInstance();
        activity=(HomeActivity)getActivity();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        lang = Paper.book().read("lang", "ar");
        contactUsModel = new ContactUsModel();
        binding.setContactModel(contactUsModel);

        presenter = new FragmentContactusPresenter(activity, this);
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.checkData(contactUsModel);
            }
        });
        binding.facebook.setOnClickListener(v -> {
            if (setting != null && setting.getSettings() != null && setting.getSettings().getFacebook() != null) {
                if (setting.getSettings().getInstagram().matches(regex)) {
                    presenter.open(setting.getSettings().getFacebook());

                } else {
                    Toast.makeText(activity, R.string.link_inc, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(activity, R.string.not_avail_now, Toast.LENGTH_SHORT).show();
            }
        });
        binding.instgram.setOnClickListener(v -> {
            if (setting != null && setting.getSettings() != null && setting.getSettings().getInstagram() != null) {
                if (setting.getSettings().getInstagram().matches(regex)) {
                    presenter.open(setting.getSettings().getInstagram());

                } else {
                    Toast.makeText(activity, R.string.link_inc, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(activity, R.string.not_avail_now, Toast.LENGTH_SHORT).show();
            }
        });

        binding.twitter.setOnClickListener(v -> {
            if (setting != null && setting.getSettings() != null && setting.getSettings().getTwitter() != null) {
                if (setting.getSettings().getTwitter().matches(regex)) {
                    presenter.open(setting.getSettings().getTwitter());

                } else {
                    Toast.makeText(activity, R.string.link_inc, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(activity, R.string.not_avail_now, Toast.LENGTH_SHORT).show();
            }
        });
        binding.google.setOnClickListener(v -> {
            if (setting != null && setting.getSettings() != null && setting.getSettings().getTwitter() != null) {
                if (setting.getSettings().getTwitter().matches(regex)) {
                    presenter.open(setting.getSettings().getTwitter());

                } else {
                    Toast.makeText(activity, R.string.link_inc, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(activity, R.string.not_avail_now, Toast.LENGTH_SHORT).show();
            }
        });
        presenter.getSetting();

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
    public void onContactVaild() {
        Toast.makeText(activity,activity.getResources().getString(R.string.suc),Toast.LENGTH_LONG).show();

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
    public void onFailed() {
        Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServer() {
        Toast.makeText(activity, getString(R.string.server_error), Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onsetting(SettingModel body) {
        this.setting = body;




    }
    @Override
    public void ViewSocial(String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
        startActivity(intent);

    }

}
