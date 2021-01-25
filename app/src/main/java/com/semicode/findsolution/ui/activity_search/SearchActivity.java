package com.semicode.findsolution.ui.activity_search;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.semicode.findsolution.R;
import com.semicode.findsolution.adapters.Advisor_Adapter;
import com.semicode.findsolution.databinding.ActivitySearchBinding;
import com.semicode.findsolution.language.Language;
import com.semicode.findsolution.models.AllAdvisorModel;
import com.semicode.findsolution.models.SingleUserModel;
import com.semicode.findsolution.mvp.activity_search_mvp.ActivitySearchPresenter;
import com.semicode.findsolution.mvp.activity_search_mvp.SearchActivityView;
import com.semicode.findsolution.share.Common;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class SearchActivity extends AppCompatActivity implements SearchActivityView {
    private ActivitySearchBinding binding;
    private ActivitySearchPresenter presenter;
    private String lang;

    private Advisor_Adapter adapter;

    private List<SingleUserModel> singleUserModelList;
    private String query;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang","ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        initView();

    }


    private void initView() {
        presenter = new ActivitySearchPresenter(this, this);
        singleUserModelList = new ArrayList<>();

        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        adapter = new Advisor_Adapter(singleUserModelList, this);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(adapter);
        //binding.progBar.setVisibility(View.GONE);
        binding.llBack.setOnClickListener(view -> {
            presenter.backPress();
        });

        binding.editQuery.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                query = binding.editQuery.getText().toString();
                if (!TextUtils.isEmpty(query)) {
                    Common.CloseKeyBoard(SearchActivity.this, binding.editQuery);
                    presenter.getdoctors(query);
                    return false;
                } else {
                    query = "";
                }
                presenter.getdoctors(query);

            }
            return false;
        });


        presenter.getdoctors(query);


    }



    @Override
    public void onFinished() {
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            Intent intent=getIntent();
            setResult(RESULT_OK,intent);

            finish();}
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
        Toast.makeText(SearchActivity.this, msg, Toast.LENGTH_LONG).show();
    }



    @Override
    public void searchsuc(AllAdvisorModel body) {
        singleUserModelList.clear();
        singleUserModelList.addAll(body.getData());
        adapter.notifyDataSetChanged();
        if(singleUserModelList.size()==0){
            binding.tvNoData.setVisibility(View.VISIBLE);
        }
        else {
            binding.tvNoData.setVisibility(View.GONE);
        }
    }


}