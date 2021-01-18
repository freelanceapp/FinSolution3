package com.semicode.findsolution.ui.activity_edit_profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;

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


        binding.flback.setOnClickListener(view -> {
           presenter.backPress();
        });


        updateUI();
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