package com.semicode.findsolution.ui.activity_advisor_detials;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.semicode.findsolution.R;
import com.semicode.findsolution.databinding.ActivityAdvisorDetialsBinding;
import com.semicode.findsolution.language.Language;
import com.semicode.findsolution.models.SingleUserModel;
import com.semicode.findsolution.mvp.activity_advisor_detials_mvp.ActivityAdvisorDetialsPresenter;
import com.semicode.findsolution.mvp.activity_advisor_detials_mvp.AdvisorDetialsActivityView;
import com.semicode.findsolution.preferences.Preferences;

import io.paperdb.Paper;

public class AdvisorDetialsActivity extends AppCompatActivity implements AdvisorDetialsActivityView, OnMapReadyCallback {
    private ActivityAdvisorDetialsBinding binding;
    private ActivityAdvisorDetialsPresenter presenter;
    private Preferences preferences;
    private SingleUserModel userModel;
    private ProgressDialog dialog;
    private double lat = 0.0, lng = 0.0;
    private GoogleMap mMap;
    private Marker marker;
    private float zoom = 15.0f;
    private String lang;
    private static final int REQUEST_PHONE_CALL = 3;
    private Intent intent;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_advisor_detials);
        getdatafromIntent();
        initView();

    }

    private void getdatafromIntent() {
        Intent intent = getIntent();
        userModel = (SingleUserModel) intent.getSerializableExtra("data");
    }

    private void initView() {
        preferences = Preferences.getInstance();

        presenter = new ActivityAdvisorDetialsPresenter(this, this);
        binding.setModel(userModel);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);

        binding.flback.setOnClickListener(view -> {
           presenter.backPress();
        });

        binding.cardcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(userModel);
            }
        });
        binding.cardwahts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userModel != null &&  userModel.getWhatsapp_number() != null) {
                    presenter.open("https://api.whatsapp.com/send?phone="+userModel.getWhatsapp_number());
                }
            }
        });
        updateUI();
        if(userModel.getContact_number()==null||userModel.getContact_number().isEmpty()){
            binding.cardcall.setVisibility(View.GONE);
        }
        if(userModel.getWhatsapp_number()==null||userModel.getWhatsapp_number().isEmpty()){
            binding.cardwahts.setVisibility(View.GONE);
        }
    }

    private void open(SingleUserModel userModel) {
        intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",  userModel.getContact_number(), null));
                    if (intent != null) {
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ContextCompat.checkSelfPermission(AdvisorDetialsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(AdvisorDetialsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                            } else {
                                startActivity(intent);
                            }
                        } else {
                            startActivity(intent);
                        }


    }}

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (this.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    Activity#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for Activity#requestPermissions for more details.
                            return;
                        }
                    }
                    startActivity(intent);
                } else {

                }
                return;
            }
        }

    }


    private void updateUI() {

        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap != null) {
            mMap = googleMap;
            mMap.setTrafficEnabled(false);
            mMap.setBuildingsEnabled(false);
            mMap.setIndoorEnabled(true);
            AddMarker(userModel.getLatitude(), userModel.getLongitude(), userModel.getAddress());


        }
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
    public void ViewSocial(String s) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
        startActivity(intent);
    }


    public void AddMarker(double lat, double lng, String address) {

        this.lat = lat;
        this.lng = lng;

        if (marker == null) {
            marker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        } else {
            marker.setPosition(new LatLng(lat, lng));


        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom));
    }


}