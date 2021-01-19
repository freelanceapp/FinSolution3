package com.semicode.findsolution.ui.activity_choose_user;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import com.semicode.findsolution.R;
import com.semicode.findsolution.databinding.ActivityChooseUserBinding;
import com.semicode.findsolution.language.Language;
import com.semicode.findsolution.ui.activity_sign_up.SignUpActivity;

import io.paperdb.Paper;

public class ChooseUserActivity extends AppCompatActivity {
    private ActivityChooseUserBinding binding;
    private String lang = "";
    private String phone_code = "";
    private String phone = "";
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang","ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Transition transition = new Fade();
            transition.setInterpolator(new LinearInterpolator());
            transition.setDuration(500);
            getWindow().setEnterTransition(transition);
            getWindow().setExitTransition(transition);

        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_user);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            phone_code = intent.getStringExtra("phone_code");
            phone = intent.getStringExtra("phone");

        }
    }
    private void initView() {
        binding.cardAdvisor.setOnClickListener(view -> {
            lang = "ar";
            binding.flAdvisor.setBackgroundResource(R.drawable.small_rounded_red_strock);
            binding.flclient.setBackgroundResource(0);
            binding.btnNext.setVisibility(View.VISIBLE);

        });

        binding.cardclient.setOnClickListener(view -> {
            lang = "en";
            binding.flAdvisor.setBackgroundResource(0);
            binding.flclient.setBackgroundResource(R.drawable.small_rounded_red_strock);
        });

binding.btnNext.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ChooseUserActivity.this, SignUpActivity.class);
        intent.putExtra("phone_code", phone_code);
        intent.putExtra("phone", phone);
        startActivity(intent);
        finish();
    }
});

    }
}