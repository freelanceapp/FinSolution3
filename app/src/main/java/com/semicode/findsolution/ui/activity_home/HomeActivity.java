package com.semicode.findsolution.ui.activity_home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;


import com.semicode.findsolution.R;
import com.semicode.findsolution.databinding.ActivityHomeBinding;
import com.semicode.findsolution.language.Language;
import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.mvp.activity_home_mvp.ActivityHomePresenter;
import com.semicode.findsolution.mvp.activity_home_mvp.HomeActivityView;
import com.semicode.findsolution.preferences.Preferences;
import com.semicode.findsolution.ui.activity_login.LoginActivity;
import com.semicode.findsolution.ui.activity_packges.PackgesActivity;
import com.semicode.findsolution.ui.activity_search.SearchActivity;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements HomeActivityView {
    private ActivityHomeBinding binding;
    private ActivityHomePresenter presenter;
    private String lang;

    private Preferences preferences;
    private UserModel userModel;
    private FragmentManager fragmentManager;
    private ActionBarDrawerToggle toggle;
    private float lastTranslate = 0.0f;


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
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        fragmentManager = getSupportFragmentManager();
        presenter = new ActivityHomePresenter(this, this, fragmentManager);
        toggle = new ActionBarDrawerToggle(this, binding.drawar, binding.toolbar, R.string.open, R.string.close) {
            @SuppressLint("NewApi")
            public void onDrawerSlide(View drawerView, float slideOffset) {
                slide(slideOffset);

            }
        };
        //   toggle.setDrawerIndicatorEnabled(false);

        toggle.syncState();
        binding.toolbar.setNavigationIcon(R.drawable.ic_menu);

        binding.drawar.setDrawerListener(toggle);
        binding.llabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawar.closeDrawer(Gravity.RIGHT);
                binding.drawar.closeDrawer(Gravity.RIGHT);
                binding.tv1.setTextColor(getResources().getColor(R.color.gray9));
                binding.tv2.setTextColor(getResources().getColor(R.color.gray9));
                binding.tv3.setTextColor(getResources().getColor(R.color.gray9));
                binding.tv4.setTextColor(getResources().getColor(R.color.colorAccent));
                binding.tv5.setTextColor(getResources().getColor(R.color.gray9));
                binding.image1.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);
                binding.image2.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);
                binding.image3.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);
                binding.image4.setColorFilter(R.color.colorAccent, android.graphics.PorterDuff.Mode.MULTIPLY);
                binding.image5.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);
                presenter.displayFragmentAboutus();

            }
        });
        binding.llterms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawar.closeDrawer(Gravity.RIGHT);
                binding.drawar.closeDrawer(Gravity.RIGHT);
                binding.tv1.setTextColor(getResources().getColor(R.color.gray9));
                binding.tv2.setTextColor(getResources().getColor(R.color.gray9));
                binding.tv3.setTextColor(getResources().getColor(R.color.gray9));
                binding.tv4.setTextColor(getResources().getColor(R.color.gray9));
                binding.tv5.setTextColor(getResources().getColor(R.color.colorAccent));
                binding.image1.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);
                binding.image2.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);
                binding.image3.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);
                binding.image4.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);
                binding.image5.setColorFilter(R.color.colorAccent, android.graphics.PorterDuff.Mode.MULTIPLY);
                presenter.displayFragmentTerms();
            }
        });
        binding.llcontactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawar.closeDrawer(Gravity.RIGHT);
                binding.tv1.setTextColor(getResources().getColor(R.color.gray9));
                binding.tv2.setTextColor(getResources().getColor(R.color.gray9));
                binding.tv3.setTextColor(getResources().getColor(R.color.colorAccent));
                binding.tv4.setTextColor(getResources().getColor(R.color.gray9));
                binding.tv5.setTextColor(getResources().getColor(R.color.gray9));
                binding.image1.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);
                binding.image2.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);
                binding.image3.setColorFilter(R.color.colorAccent, android.graphics.PorterDuff.Mode.MULTIPLY);
                binding.image4.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);
                binding.image5.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);

                presenter.displayFragmentContactus();

            }
        });
        binding.llhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawar.closeDrawer(Gravity.RIGHT);
                binding.tv1.setTextColor(getResources().getColor(R.color.colorAccent));
                binding.tv2.setTextColor(getResources().getColor(R.color.gray9));
                binding.tv3.setTextColor(getResources().getColor(R.color.gray9));
                binding.tv4.setTextColor(getResources().getColor(R.color.gray9));
                binding.tv5.setTextColor(getResources().getColor(R.color.gray9));
                binding.image1.setColorFilter(R.color.colorAccent, android.graphics.PorterDuff.Mode.MULTIPLY);
                binding.image2.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);
                binding.image3.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);
                binding.image4.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);
                binding.image5.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);
                presenter.displayFragmentHome();
            }
        });
        binding.llprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawar.closeDrawer(Gravity.RIGHT);
                if (userModel != null) {
                    binding.tv1.setTextColor(getResources().getColor(R.color.gray9));
                    binding.tv2.setTextColor(getResources().getColor(R.color.colorAccent));
                    binding.tv3.setTextColor(getResources().getColor(R.color.gray9));
                    binding.tv4.setTextColor(getResources().getColor(R.color.gray9));
                    binding.tv5.setTextColor(getResources().getColor(R.color.gray9));
                    binding.image1.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);
                    binding.image2.setColorFilter(R.color.colorAccent, android.graphics.PorterDuff.Mode.MULTIPLY);
                    binding.image3.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);
                    binding.image4.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);
                    binding.image5.setColorFilter(R.color.gray9, android.graphics.PorterDuff.Mode.MULTIPLY);
                    if (userModel.getData().getUser_type().equals("adviser")) {
                        presenter.displayFragmentProfile();
                    } else {
                        presenter.displayFragmentUserProfile();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, getResources().getString(R.string.please_sign_in_or_sign_up), Toast.LENGTH_LONG).show();
                }
            }
        });
        binding.lllogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userModel != null) {
                    preferences.clear(HomeActivity.this);
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(HomeActivity.this, getResources().getString(R.string.please_sign_in_or_sign_up), Toast.LENGTH_LONG).show();

                }

            }

        });
        binding.llsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PackgesActivity.class);
                startActivity(intent);
                finish();

            }
        });
        binding.flSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        if ((userModel != null && (!userModel.getData().getNumber_of_payment_days().equals("0") || !userModel.getData().getUser_type().equals("adviser"))) || userModel == null) {
            binding.llsubscribe.setVisibility(View.GONE);
        } else {
            binding.llsubscribe.setVisibility(View.VISIBLE);

        }
    }

    public void slide(float slideOffset) {
        float moveFactor = (float) ((binding.cons.getWidth() * slideOffset) / 1.5);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            binding.cons.setTranslationX(-moveFactor);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.cons.getLayoutParams();
            if (binding.drawar.isOpen()) {
                params.setMargins(0, 0, 0, 0);
            } else {
                params.setMargins(0, 200, 0, 200);
            }
            binding.cons.setLayoutParams(params);

        } else {
            TranslateAnimation anim = new TranslateAnimation(lastTranslate, -moveFactor, 0.0f, 0.0f);
            anim.setDuration(0);
            anim.setFillAfter(true);

            lastTranslate = -moveFactor;
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.cons.getLayoutParams();
            if (binding.drawar.isOpen()) {
                params.setMargins(0, 0, 0, 0);
            } else {
                params.setMargins(0, 200, 0, 200);
            }
            binding.cons.setLayoutParams(params);
            binding.cons.startAnimation(anim);

        }

    }
    // To animate view slide out from right to left


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