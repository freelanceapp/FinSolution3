package com.semicode.findsolution.ui.activity_home.fragments;

import android.content.Intent;
import android.graphics.Color;
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
import com.semicode.findsolution.models.AllCatogryModel;
import com.semicode.findsolution.models.SingleCategoryModel;
import com.semicode.findsolution.models.Slider_Model;
import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.mvp.activity_home_mvp.ActivityHomePresenter;
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


public class Fragment_Home extends Fragment implements HomeFragmentView {
    private FragmentHomeBinding binding;
    private HomeActivity activity;
    private SliderAdapter sliderAdapter;
    private List<Slider_Model.Data> sliDataList;
    private Timer timer;
    private TimerTask timerTask;
    private UserModel body;
    private List<SingleCategoryModel> allcategorlist;
    private Category_Adapter category_adapter;
    private FragmentHomePresenter presenter;
    private String lang;
    private Preferences preferences;
    private UserModel userModel;

    public static Fragment_Home newInstance() {

        Fragment_Home fragment_home = new Fragment_Home();
        return fragment_home;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.getRoot().setBackgroundColor(getResources().getColor(R.color.white));

        initView();

        return binding.getRoot();
    }

    private void initView() {
        sliDataList = new ArrayList<>();
        allcategorlist = new ArrayList<>();
        preferences = Preferences.getInstance();
        activity=(HomeActivity)getActivity(); 
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        lang = Paper.book().read("lang", "ar");
        sliderAdapter = new SliderAdapter(sliDataList, activity);
        category_adapter = new Category_Adapter(allcategorlist, activity,this);
        binding.recView.setLayoutManager(new GridLayoutManager(activity, 3));
        binding.recView.setAdapter(category_adapter);
        binding.pager.setAdapter(sliderAdapter);
        binding.pager.setClipToPadding(false);
        binding.pager.setPageMargin(15);
        binding.pager.setPadding(50, 2, 50, 0);
        presenter = new FragmentHomePresenter(this, activity);

        presenter.getSlider();
        presenter.getcategories();

    }


    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(activity, DepartmentDetialsActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);

    }


    public class MyTask extends TimerTask {
        @Override
        public void run() {
            activity.runOnUiThread(() -> {
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
