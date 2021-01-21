package com.semicode.findsolution.ui.activity_packges;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.semicode.findsolution.R;
import com.semicode.findsolution.adapters.Packges_Adapter;
import com.semicode.findsolution.databinding.ActivityPackgesBinding;
import com.semicode.findsolution.language.Language;
import com.semicode.findsolution.models.AllPackgesModel;
import com.semicode.findsolution.models.SignUpAdvisorModel;
import com.semicode.findsolution.models.SinglePakcgesModel;
import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.mvp.activity_packge_mvp.ActivityPackgePresenter;
import com.semicode.findsolution.mvp.activity_packge_mvp.PackgeActivityView;
import com.semicode.findsolution.preferences.Preferences;
import com.semicode.findsolution.share.Common;
import com.semicode.findsolution.ui.activity_home.HomeActivity;
import com.semicode.findsolution.ui.activity_web_view.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class PackgesActivity extends AppCompatActivity implements PackgeActivityView {
    private ActivityPackgesBinding binding;
    private ActivityPackgePresenter presenter;
    private String lang;

    private Preferences preferences;
    private UserModel userModel;
    private List<SinglePakcgesModel> singlePakcgesModels;
    private SignUpAdvisorModel signUpAdvisorModel;
    private Packges_Adapter packges_adapter;
    private ProgressDialog dialog2;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_packges);
        getdatafromIntent();
        initView();
    }

    private void getdatafromIntent() {
        Intent intent = getIntent();
        signUpAdvisorModel=(SignUpAdvisorModel)intent.getSerializableExtra("data");
    }

    private void initView() {
        singlePakcgesModels = new ArrayList<>();

        preferences = Preferences.getInstance();

        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.llBack.setOnClickListener(view -> {
            finish();
        });
        packges_adapter = new Packges_Adapter(singlePakcgesModels, this);

        binding.recView.setLayoutManager(new GridLayoutManager(this,2));
        binding.recView.setAdapter(packges_adapter);
        presenter = new ActivityPackgePresenter(this, this);
        presenter.getpackge();

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
        dialog2 = Common.createProgressDialog(this, getString(R.string.wait));
        dialog2.setCancelable(false);
        dialog2.show();
    }

    @Override
    public void onFinishload() {
        dialog2.dismiss();
    }

    @Override
    public void getallpackges(AllPackgesModel body) {
        singlePakcgesModels.clear();
        singlePakcgesModels.addAll(body.getData());
        packges_adapter.notifyDataSetChanged();
        if (singlePakcgesModels.size() == 0) {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
presenter.getprofile(userModel.getData().getId());
        }
    }

    @Override
    public void onSignupValid(UserModel userModel) {
if(userModel.getData().getUrl().isEmpty()||userModel.getData().getUrl()==null){
        preferences.create_update_userdata(PackgesActivity.this, userModel);


        Intent intent = new Intent(this, HomeActivity.class);

        startActivity(intent);
        finish();}
else {
    this.userModel=userModel;
    Intent intent = new Intent(this, WebViewActivity.class);
intent.putExtra("url",userModel.getData().getUrl());
    startActivityForResult(intent,1);

    }



    }




    public void choosepackge(SinglePakcgesModel singlePakcgesModel) {
        signUpAdvisorModel.setCurrent_package_id(singlePakcgesModel.getId()+"");
        presenter.sign_up_without_image(signUpAdvisorModel);

    }
    @Override
    public void onFailed() {
        Toast.makeText(PackgesActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServer() {
        Toast.makeText(PackgesActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onnotconnect(String msg) {
        Toast.makeText(PackgesActivity.this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void profile(UserModel body) {
        body.getData().setToken(userModel.getData().getToken());
        this.userModel=body;
        preferences.create_update_userdata(PackgesActivity.this, userModel);


        Intent intent = new Intent(this, HomeActivity.class);

        startActivity(intent);
        finish();
    }
}