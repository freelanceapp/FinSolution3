package com.semicode.findsolution.ui.activity_home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;


import com.semicode.findsolution.R;
import com.semicode.findsolution.adapters.Category_Adapter;
import com.semicode.findsolution.adapters.SliderAdapter;
import com.semicode.findsolution.databinding.ActivityHomeBinding;
import com.semicode.findsolution.language.Language;
import com.semicode.findsolution.models.AllCatogryModel;
import com.semicode.findsolution.models.SingleCategoryModel;
import com.semicode.findsolution.models.Slider_Model;
import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.mvp.activity_home_mvp.ActivityHomePresenter;
import com.semicode.findsolution.mvp.activity_home_mvp.HomeActivityView;
import com.semicode.findsolution.preferences.Preferences;
import com.semicode.findsolution.ui.activity_department_detials.DepartmentDetialsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements HomeActivityView {
    private ActivityHomeBinding binding;
    private ActivityHomePresenter presenter;
    private String lang;

    private Preferences preferences;
    private UserModel userModel;
    private FragmentManager fragmentManager;
    private ActionBarDrawerToggle toggle;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initView();
    }


    private void initView() {
        fragmentManager = getSupportFragmentManager();
        presenter = new ActivityHomePresenter(this, this,fragmentManager);
        toggle = new ActionBarDrawerToggle(this, binding.drawar, binding.toolbar, getString(R.string.open), R.string.close);
        toggle.syncState();
    }


    @Override
    public void onFinished() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.backPress();
    }
}