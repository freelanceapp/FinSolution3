package com.semicode.findsolution.ui.activity_sign_up;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.semicode.findsolution.R;

import com.semicode.findsolution.databinding.ActivitySignUpBinding;
import com.semicode.findsolution.databinding.DialogSelectImageBinding;
import com.semicode.findsolution.language.Language;
import com.semicode.findsolution.models.SignUpModel;
import com.semicode.findsolution.models.UserModel;
import com.semicode.findsolution.mvp.activity_sign_up_mvp.ActivitySignUpPresenter;
import com.semicode.findsolution.mvp.activity_sign_up_mvp.ActivitySignUpView;
import com.semicode.findsolution.preferences.Preferences;
import com.semicode.findsolution.share.Common;
import com.semicode.findsolution.ui.activity_home.HomeActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class SignUpActivity extends AppCompatActivity implements ActivitySignUpView {
    private ActivitySignUpBinding binding;
    private String phone = "", phone_code = "";
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int READ_REQ = 1, CAMERA_REQ = 2;
    private Uri uri = null;
    private List<String> genderList;

    private ActivitySignUpPresenter presenter;
    private SignUpModel model;

    private ProgressDialog dialog2;
    private AlertDialog dialog;
    private double lat = 0.0, lng = 0.0;
    private String lang;

    private Preferences preferences;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
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
        preferences = Preferences.getInstance();
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        model = new SignUpModel(phone_code, phone);
        binding.setModel(model);
        presenter = new ActivitySignUpPresenter(this, this);

        binding.btnLogin.setOnClickListener(view -> {
            presenter.checkData(model);
        });
        binding.flSelectImage.setOnClickListener(view -> {
            dialog.show();
        });
        createImageDialogAlert();

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
            Picasso.get().load(file).fit().into(binding.image);
            binding.icon.setVisibility(View.GONE);
            model.setImageUrl(uri.toString());
            binding.setModel(model);

        } else if (requestCode == CAMERA_REQ && resultCode == Activity.RESULT_OK && data != null) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            uri = getUriFromBitmap(bitmap);
            binding.icon.setVisibility(View.GONE);

            if (uri != null) {
                model.setImageUrl(uri.toString());
                binding.setModel(model);
                String path = Common.getImagePath(this, uri);
                if (path != null) {
                    Picasso.get().load(new File(path)).fit().into(binding.image);

                } else {
                    Picasso.get().load(uri).fit().into(binding.image);

                }
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
        dialog2 = Common.createProgressDialog(this, getString(R.string.wait));
        dialog2.setCancelable(false);
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
        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSignupValid(UserModel userModel) {
        preferences.create_update_userdata(SignUpActivity.this, userModel);


        Intent intent = new Intent(this, HomeActivity.class);

        startActivity(intent);
        finish();

    }

    @Override
    public void onFailed() {
        Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServer() {
        Toast.makeText(SignUpActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();

    }
}