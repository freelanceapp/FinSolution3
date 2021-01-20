package com.semicode.findsolution.ui.activity_advisor_signup;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.semicode.findsolution.R;
import com.semicode.findsolution.adapters.SpinnerCategoryAdapter;
import com.semicode.findsolution.adapters.SpinnerSubCategoryAdapter;
import com.semicode.findsolution.databinding.ActivitySignUpAdvisorBinding;
import com.semicode.findsolution.databinding.ActivitySignUpBinding;
import com.semicode.findsolution.databinding.DialogSelectImageBinding;
import com.semicode.findsolution.language.Language;
import com.semicode.findsolution.models.AllCatogryModel;
import com.semicode.findsolution.models.AllSubCatogryModel;
import com.semicode.findsolution.models.PlaceGeocodeData;
import com.semicode.findsolution.models.SignUpAdvisorModel;
import com.semicode.findsolution.models.SignUpModel;
import com.semicode.findsolution.models.SingleCategoryModel;
import com.semicode.findsolution.models.SingleSubCategoryModel;
import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.mvp.activity_sign_up_mvp.ActivitySignUpPresenter;
import com.semicode.findsolution.mvp.activity_sign_up_mvp.ActivitySignUpView;
import com.semicode.findsolution.mvp.activity_signupadvisor_mvp.ActivitySignUpAdvisorPresenter;
import com.semicode.findsolution.mvp.activity_signupadvisor_mvp.ActivitySignUpAdvisorView;
import com.semicode.findsolution.preferences.Preferences;
import com.semicode.findsolution.share.Common;
import com.semicode.findsolution.ui.activity_home.HomeActivity;
import com.semicode.findsolution.ui.activity_packges.PackgesActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class SignUpAdvisorActivity extends AppCompatActivity implements ActivitySignUpAdvisorView, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private ActivitySignUpAdvisorBinding binding;
    private String phone = "", phone_code = "";
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int READ_REQ = 1, CAMERA_REQ = 2;
    private Uri uri = null;


    private ActivitySignUpAdvisorPresenter presenter;
    private SignUpAdvisorModel model;

    private ProgressDialog dialog2;
    private AlertDialog dialog;
    private double lat = 0.0, lng = 0.0;
    private String lang;

    private Preferences preferences;
    private GoogleMap mMap;
    private Marker marker;
    private float zoom = 15.0f;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private final String fineLocPerm = Manifest.permission.ACCESS_FINE_LOCATION;
    private final int loc_req = 1225;
    private SpinnerCategoryAdapter spinnerCategoryAdapter;
    private List<SingleCategoryModel> singleCategoryModelList;
    private SpinnerSubCategoryAdapter spinnerSubCategoryAdapter;
    private List<SingleSubCategoryModel> singleSubCategoryModelList;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_advisor);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            phone_code = intent.getStringExtra("phone_code");
            phone = intent.getStringExtra("phone");
            lat = intent.getDoubleExtra("lat", 0.0);
            lng = intent.getDoubleExtra("lng", 0.0);


        }
    }

    private void initView() {
        singleCategoryModelList = new ArrayList<>();
        singleSubCategoryModelList = new ArrayList<>();
        preferences = Preferences.getInstance();
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        model = new SignUpAdvisorModel(phone_code, phone);
        binding.setModel(model);
        presenter = new ActivitySignUpAdvisorPresenter(this, this);
        presenter.getcategories();
        binding.btnConfirm.setOnClickListener(view -> {
            presenter.checkData(model);
        });
        spinnerCategoryAdapter = new SpinnerCategoryAdapter(singleCategoryModelList, this);

        spinnerSubCategoryAdapter = new SpinnerSubCategoryAdapter(singleSubCategoryModelList, this);
        binding.spCat.setAdapter(spinnerCategoryAdapter);
        binding.spsubcat.setAdapter(spinnerSubCategoryAdapter);
        binding.spCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    model.setCategory_id(0);
                } else {
                    model.setCategory_id(singleCategoryModelList.get(i).getId());
                    presenter.getsubcategories(singleCategoryModelList.get(i).getId());

                }
                //    presenter.getproducts(userModel, cat, query);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spsubcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    model.setSub_category_id(0);
                } else {
                    model.setSub_category_id(singleSubCategoryModelList.get(i).getId());
                    // presenter.getsubcategories(singleCategoryModelList.get(i).getId());

                }
                //    presenter.getproducts(userModel, cat, query);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        createImageDialogAlert();

        updateUI();
        CheckPermission();
    }

    private void CheckPermission() {
        if (ActivityCompat.checkSelfPermission(this, fineLocPerm) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{fineLocPerm}, loc_req);
        } else {

            initGoogleApi();
        }
    }

    private void initGoogleApi() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
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

            mMap.setOnMapClickListener(latLng -> {
                lat = latLng.latitude;
                lng = latLng.longitude;
                presenter.getGeoData(lat, lng, lang);

            });


        }
    }

    public void AddMarker(double lat, double lng, String address) {

        this.lat = lat;
        this.lng = lng;
        model.setAddress(address);
        model.setLatitude(lat);
        model.setLongitude(lng);
        if (marker == null) {
            marker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        } else {
            marker.setPosition(new LatLng(lat, lng));


        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom));
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        initLocationRequest();
    }

    private void initLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setFastestInterval(1000);
        locationRequest.setInterval(60000);
        LocationSettingsRequest.Builder request = new LocationSettingsRequest.Builder();
        request.addLocationRequest(locationRequest);
        request.setAlwaysShow(false);


        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, request.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        startLocationUpdate();
                        break;

                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(SignUpAdvisorActivity.this, 100);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;

                }
            }
        });

    }

    @Override
    public void onConnectionSuspended(int i) {
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @SuppressLint("MissingPermission")
    private void startLocationUpdate() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                onLocationChanged(locationResult.getLastLocation());
            }
        };
        LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
        presenter.getGeoData(lat, lng, lang);

        if (googleApiClient != null) {
            LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(locationCallback);
            googleApiClient.disconnect();
            googleApiClient = null;
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (googleApiClient != null) {
            if (locationCallback != null) {
                LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(locationCallback);
                googleApiClient.disconnect();
                googleApiClient = null;
            }
        }
    }


    private void createImageDialogAlert() {
        dialog = new AlertDialog.Builder(this)
                .create();

        DialogSelectImageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_select_image, null, false);
        binding.btnCamera.setOnClickListener(view -> {
            dialog.dismiss();
            checkCameraPermission();
        });
        binding.btnGallery.setOnClickListener(view -> {
            dialog.dismiss();
            checkReadPermission();
        });
        binding.btnCancel.setOnClickListener(v -> dialog.dismiss()

        );
        // dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
    }

    public void checkReadPermission() {
        if (ActivityCompat.checkSelfPermission(this, READ_PERM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{READ_PERM}, READ_REQ);
        } else {
            SelectImage(READ_REQ);
        }
    }

    public void checkCameraPermission() {


        if (ContextCompat.checkSelfPermission(this, write_permission) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, camera_permission) == PackageManager.PERMISSION_GRANTED
        ) {
            SelectImage(CAMERA_REQ);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{camera_permission, write_permission}, CAMERA_REQ);
        }
    }


    private void SelectImage(int req) {

        Intent intent = new Intent();

        if (req == READ_REQ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            } else {
                intent.setAction(Intent.ACTION_GET_CONTENT);

            }

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");
            startActivityForResult(intent, req);

        } else if (req == CAMERA_REQ) {
            try {
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, req);
            } catch (SecurityException e) {
                Toast.makeText(this, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();

            }


        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_REQ) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SelectImage(requestCode);
            } else {
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == CAMERA_REQ) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                SelectImage(requestCode);
            } else {
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQ && resultCode == Activity.RESULT_OK && data != null) {

            uri = data.getData();
            File file = new File(Common.getImagePath(this, uri));
//            Picasso.get().load(file).fit().into(binding.image);
//            binding.icon.setVisibility(View.GONE);
            model.setImageUrl(uri.toString());
            binding.setModel(model);

        } else if (requestCode == CAMERA_REQ && resultCode == Activity.RESULT_OK && data != null) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            uri = getUriFromBitmap(bitmap);
            // binding.icon.setVisibility(View.GONE);

            if (uri != null) {
                model.setImageUrl(uri.toString());
                binding.setModel(model);
                String path = Common.getImagePath(this, uri);
//                if (path != null) {
//                    Picasso.get().load(new File(path)).fit().into(binding.image);
//
//                } else {
//                    Picasso.get().load(uri).fit().into(binding.image);
//
//                }
            }


        }

    }

    private Uri getUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "", ""));
    }


    @Override
    public void onLoad() {
        if (dialog2 == null) {
            dialog2 = Common.createProgressDialog(this, getString(R.string.wait));
            dialog2.setCancelable(false);
        } else {
            dialog2.dismiss();
        }
        dialog2.show();
    }

    @Override
    public void onFinishload() {
        dialog2.dismiss();
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onnotconnect(String msg) {
        Toast.makeText(SignUpAdvisorActivity.this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onaddress(PlaceGeocodeData body) {
        AddMarker(body.getResults().get(0).getGeometry().getLocation().getLat(), body.getResults().get(0).getGeometry().getLocation().getLng(), body.getResults().get(0).getFormatted_address().replace("Unnamed Road,", ""));

    }

    @Override
    public void choosepackges() {
        Intent intent = new Intent(SignUpAdvisorActivity.this, PackgesActivity.class);
        intent.putExtra("data", model);
        startActivity(intent);
    }

    @Override
    public void onSuccess(AllSubCatogryModel body) {
        singleSubCategoryModelList.clear();
        singleSubCategoryModelList.add(new SingleSubCategoryModel(getResources().getString(R.string.choose_category)));

        singleSubCategoryModelList.addAll(body.getData());
        spinnerSubCategoryAdapter.notifyDataSetChanged();

    }

    @Override
    public void onSuccesscategory(AllCatogryModel body) {
        singleCategoryModelList.clear();

        singleCategoryModelList.add(new SingleCategoryModel(getResources().getString(R.string.choose_category)));
        singleCategoryModelList.addAll(body.getData());
        spinnerCategoryAdapter.notifyDataSetChanged();
    }


    @Override
    public void onFailed() {
        Toast.makeText(SignUpAdvisorActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServer() {
        Toast.makeText(SignUpAdvisorActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();

    }
}