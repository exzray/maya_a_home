package com.afiq.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.afiq.myapplication.databinding.ActivityUploadTransferBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UploadTransferActivity extends AppCompatActivity {

    public static final int REQUEST_GALLERY_CODE = 10;
    public static final int REQUEST_CAMERA_CODE = 11;

    private ActivityUploadTransferBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadTransferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                Toast.makeText(this, "Camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onClickUpload(View view) {
        openGallery();
    }

    public void onClickSubmit(View view) {
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Select image from");
    }

    private void openGallery() {
        String[] mimes = {"image/jpeg", "image/png"};

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimes);

        startActivityForResult(Intent.createChooser(intent, "Select Transfer Receipt"), REQUEST_GALLERY_CODE);
    }

    private void uploadImage(Uri uri) {
        StorageReference reference = FirebaseStorage.getInstance().getReference();
        reference
                .child("hehe.txt")
                .getDownloadUrl()
                .addOnFailureListener(e -> Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
