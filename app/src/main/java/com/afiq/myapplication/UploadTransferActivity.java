package com.afiq.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.afiq.myapplication.databinding.ActivityUploadTransferBinding;
import com.afiq.myapplication.utilities.Interaction;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class UploadTransferActivity extends AppCompatActivity {

    public static final int REQUEST_GALLERY_CODE = 10;
    public static final int REQUEST_CAMERA_CODE = 11;

    private String _progressID;

    private ActivityUploadTransferBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadTransferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        _progressID = getIntent().getStringExtra(Interaction.EXTRA_STRING_PROGRESS_ID);

        setTitle("Upload Transfer");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {

            // if data from gallery
            if (requestCode == REQUEST_GALLERY_CODE) {
                binding.viewer.setImageURI(data.getData());

                uploadImage(data.getData());
            }

            // if data from camera
            if (requestCode == REQUEST_CAMERA_CODE) {

                if (data.getExtras() == null) return;

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                binding.viewer.setImageBitmap(bitmap);
            }
        }
    }

    public void onClickUpload(View view) {
        openDialog();
    }

    public void onClickSubmit(View view) {
    }

    private void openDialog() {
        String[] items = {"Gallery", "Camera"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select image from");
        builder.setItems(items, this::onClickItem);
        builder.show();
    }

    private void onClickItem(DialogInterface dialog, int which) {
        switch (which) {
            case 0:
                openGallery();
                break;
            case 1:
                openCamera();
                break;
        }
    }

    private void openGallery() {
        String[] mimes = {"image/jpeg", "image/png"};

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimes);

        startActivityForResult(Intent.createChooser(intent, "Select Transfer Receipt"), REQUEST_GALLERY_CODE);
    }

    private void openCamera() {
        Dexter
                .withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CAMERA_CODE);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(UploadTransferActivity.this, "Abort this operation because lack of permision", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
    }

    private void uploadImage(Uri uri) {

    }
}
