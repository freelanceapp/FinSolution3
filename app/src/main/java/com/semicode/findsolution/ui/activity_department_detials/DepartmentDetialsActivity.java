package com.semicode.findsolution.ui.activity_department_detials;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.semicode.findsolution.R;
import com.semicode.findsolution.adapters.Advisor_Adapter;
import com.semicode.findsolution.adapters.SubCategory_Adapter;
import com.semicode.findsolution.databinding.ActivityDepartmentsDetialsBinding;
import com.semicode.findsolution.language.Language;
import com.semicode.findsolution.models.AllAdvisorModel;
import com.semicode.findsolution.models.AllSubCatogryModel;
import com.semicode.findsolution.models.SingleCategoryModel;
import com.semicode.findsolution.models.SingleSubCategoryModel;
import com.semicode.findsolution.models.SingleUserModel;
import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.mvp.activity_department_detials_mvp.ActivityDepartmentDetialsPresenter;
import com.semicode.findsolution.mvp.activity_department_detials_mvp.DepartmentDetialsActivityView;
import com.semicode.findsolution.preferences.Preferences;
import com.semicode.findsolution.ui.activity_advisor_detials.AdvisorDetialsActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class DepartmentDetialsActivity extends AppCompatActivity implements DepartmentDetialsActivityView {
    private ActivityDepartmentsDetialsBinding binding;
    private ActivityDepartmentDetialsPresenter presenter;
    private String lang;

    private Preferences preferences;
    private UserModel userModel;
    private SingleCategoryModel categorymodel;
    private List<SingleSubCategoryModel> singleSubCategoryModelList;
    private List<SingleUserModel> singleUserModelList;
    private SubCategory_Adapter subCategory_adapter;
    private Advisor_Adapter advisor_adapter;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_departments_detials);
        getdatafromIntent();
        initView();
    }

    private void getdatafromIntent() {
        Intent intent = getIntent();
        categorymodel = (SingleCategoryModel) intent.getSerializableExtra("data");
    }

    private void initView() {
        singleUserModelList = new ArrayList<>();
        singleSubCategoryModelList = new ArrayList<>();

        preferences = Preferences.getInstance();

        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.setTitle(categorymodel.getTitle());
        binding.llBack.setOnClickListener(view -> {
            finish();
        });
        advisor_adapter = new Advisor_Adapter(singleUserModelList, this);
        subCategory_adapter = new SubCategory_Adapter(singleSubCategoryModelList, this);
        binding.recViewcategories.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.recViewcategories.setAdapter(subCategory_adapter);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(advisor_adapter);
        presenter = new ActivityDepartmentDetialsPresenter(this, this);
        presenter.getcategories(userModel, categorymodel.getId());

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
    public void onLoad() {
        binding.progBarcategories.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinishload() {
        binding.progBarcategories.setVisibility(View.GONE);

    }

    @Override
    public void getalladvisor(AllAdvisorModel body) {
        singleUserModelList.clear();
        singleUserModelList.addAll(body.getData());
        advisor_adapter.notifyDataSetChanged();
        if (singleUserModelList.size() == 0) {
            binding.llNoNotification.setVisibility(View.VISIBLE);
        } else {
            binding.llNoNotification.setVisibility(View.GONE);
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

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSuccess(AllSubCatogryModel allCategoryModel) {
        singleSubCategoryModelList.clear();
        singleSubCategoryModelList.addAll(allCategoryModel.getData());
        subCategory_adapter.notifyDataSetChanged();
    }


    public void showdata(SingleSubCategoryModel singleSubCategoryModel) {
        presenter.getproducts(categorymodel.getId(), singleSubCategoryModel.getId());
    }

    public void showdprofile(SingleUserModel singleUserModel) {
        Intent intent=new Intent(this, AdvisorDetialsActivity.class);
        intent.putExtra("data",singleUserModel);
        startActivity(intent);
    }
}