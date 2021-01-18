package com.semicode.findsolution.ui.activity_home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;


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
import com.semicode.findsolution.tags.Tags;
import com.semicode.findsolution.ui.activity_products_sell.DepartmentDetialsActivity;

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
    private SliderAdapter sliderAdapter;
    private List<Slider_Model.Data> sliDataList;
    private Timer timer;
    private TimerTask timerTask;
    private UserModel body;
    private int pos;
    private List<SingleCategoryModel> allcategorlist;
    private Category_Adapter category_adapter;

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
        sliDataList = new ArrayList<>();
        allcategorlist = new ArrayList<>();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        sliderAdapter = new SliderAdapter(sliDataList, this);
        category_adapter = new Category_Adapter(allcategorlist, this);
        binding.recView.setLayoutManager(new GridLayoutManager(this, 3));
        binding.recView.setAdapter(category_adapter);
        binding.pager.setAdapter(sliderAdapter);
        binding.pager.setClipToPadding(false);
        binding.pager.setPageMargin(15);
        binding.pager.setPadding(50, 2, 50, 0);
        presenter = new ActivityHomePresenter(this, this);

        presenter.getSlider();
        presenter.getcategories();
    }


    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        presenter.backPress();
    }


    @Override
    public void onFinished() {
        finish();
    }


    @Override
    public void onProgressSliderShow() {
        binding.progBarslider.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressSliderHide() {
        binding.progBarslider.setVisibility(View.GONE);
    }

    @Override
    public void onSliderSuccess(List<Slider_Model.Data> sliderModelList) {


        sliDataList.addAll(sliderModelList);

        Log.e("mmmmmmmmm", sliDataList.size() + "");
        sliderAdapter.notifyDataSetChanged();

        if (sliDataList.size() > 1) {
            timer = new Timer();
            timerTask = new MyTask();
            timer.scheduleAtFixedRate(timerTask, 6000, 6000);
        }

    }

    @Override
    public void onSuccess(AllCatogryModel body) {
        allcategorlist.addAll(body.getData());
        category_adapter.notifyDataSetChanged();
        if (allcategorlist.size() == 0) {
            binding.tvNoData.setVisibility(View.VISIBLE);
        } else {
            binding.tvNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onProgressShow() {
        binding.progBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressHide() {
        binding.progBar.setVisibility(View.GONE);

    }

    public void showdata(SingleCategoryModel data) {
        Intent intent = new Intent(this, DepartmentDetialsActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);

    }


    public class MyTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(() -> {
                int current_page = binding.pager.getCurrentItem();
                if (current_page < sliderAdapter.getCount() - 1) {
                    binding.pager.setCurrentItem(binding.pager.getCurrentItem() + 1);
                } else {
                    binding.pager.setCurrentItem(0);

                }
            });

        }
    }


}