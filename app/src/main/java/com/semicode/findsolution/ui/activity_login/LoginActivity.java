package com.semicode.findsolution.ui.activity_login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.semicode.findsolution.R;
import com.semicode.findsolution.databinding.ActivityLoginBinding;
import com.semicode.findsolution.language.Language;
import com.semicode.findsolution.models.LoginModel;
import com.semicode.findsolution.mvp.activity_login_presenter.ActivityLoginPresenter;
import com.semicode.findsolution.mvp.activity_login_presenter.ActivityLoginView;
import com.semicode.findsolution.ui.activity_confirm_code.ConfirmCodeActivity;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity implements ActivityLoginView {
    private ActivityLoginBinding binding;
    private LoginModel model;
    private ActivityLoginPresenter presenter;
    private double lat=0.0,lng=0.0;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat",0.0);
        lng = intent.getDoubleExtra("lng",0.0);
    }

    private void initView() {
        model = new LoginModel();
       // binding.tv1.setText(Html.fromHtml(getString(R.string.login2)));
        binding.tvskip.setPaintFlags(binding.tvskip.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        binding.setModel(model);
        presenter = new ActivityLoginPresenter(this,this);
        binding.btnLogin.setOnClickListener(view -> {
            presenter.checkData(model);
        });

        binding.tvskip.setOnClickListener(view -> {
//            Intent intent = new Intent(this, HomeActivity.class);
//            intent.putExtra("lat",lat);
//            intent.putExtra("lng",lng);
//            startActivity(intent);
//            finish();
        });
    }

    @Override
    public void onLoginValid() {
        Intent intent = new Intent(this, ConfirmCodeActivity.class);
        intent.putExtra("phone_code",model.getPhone_code());
        intent.putExtra("phone",model.getPhone());
        intent.putExtra("lat",lat);
        intent.putExtra("lng",lng);
        startActivity(intent);
        finish();

    }
}