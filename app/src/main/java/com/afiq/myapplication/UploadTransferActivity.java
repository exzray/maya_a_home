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
import androidx.lifecycle.ViewModelProvider;

import com.afiq.myapplication.databinding.ActivityUploadTransferBinding;
import com.afiq.myapplication.models.ProgressModel;
import com.afiq.myapplication.utilities.Database;
import com.afiq.myapplication.utilities.Interaction;
import com.afiq.myapplication.viewmodels.ProgressViewModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.roger.catloadinglibrary.CatLoadingView;

public class UploadTransferActivity extends AppCompatActivity {

    private static final String TAG = "UploadTransferActivity";

    public static final int REQUEST_GALLERY_CODE = 10;
    public static final int REQUEST_CAMERA_CODE = 11;

    private String _progressID;
    private ProgressModel data;
    private Uri uri;

    private ActivityUploadTransferBinding binding;

    private CatLoadingView loadingUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadTransferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        _progressID = getIntent().getStringExtra(Interaction.EXTRA_STRING_PROGRESS_ID);

        setTitle("Upload Transfer");

        bindViewModel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {

            // if data from gallery
            if (requestCode == REQUEST_GALLERY_CODE) {
                uri = data.getData();
                binding.viewer.setImageURI(uri);
            }

            // if data from camera
            if (requestCode == REQUEST_CAMERA_CODE) {
                Toast.makeText(this, "Camera functionality under review, refrain to use this function for uploading photo", Toast.LENGTH_SHORT).show();

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
        uploadImage();
    }

    private void updateUI(ProgressModel data) {
        if (data.getStatus() == ProgressModel.STATUS.SUCCESS)
            binding.submit.setVisibility(View.GONE);
        if (!data.getImage().isEmpty())
            Glide.with(this).load(data.getImage()).into(binding.viewer);
    }

    private void bindViewModel() {
        ProgressViewModel vm = new ViewModelProvider(this).get(ProgressViewModel.class);
        vm.getData(Database.refProgress(_progressID)).observe(this, this::progressListener);
    }

    private void progressListener(ProgressModel data) {
        this.data = data;

        updateUI(data);
    }

    private void openDialog() {
        String[] items = {"Gallery", "Camera(disable)"};

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

    private void uploadImage() {
        loadingUpload = new CatLoadingView();
        loadingUpload.setCancelable(false);
        loadingUpload.show(getSupportFragmentManager(), TAG);

        if (data != null && uri != null) {
            String pathname = "mobile/receipt/" + data.getUserID() + "/" + _progressID;
            StorageReference reference = FirebaseStorage.getInstance().getReference().child(pathname);

            reference
                    .putFile(uri)
                    .continueWithTask(task -> reference.getDownloadUrl())
                    .addOnCompleteListener(this::uriListener);
        }
    }

    private void uriListener(Task<Uri> task) {
        Uri downloadUri = task.getResult();

        if (downloadUri != null) updateProgress(downloadUri.toString());
    }

    private void updateProgress(String img_url) {
        FirebaseFirestore.getInstance().runTransaction(transaction -> {
            DocumentReference progressRef = Database.refProgress(_progressID);

            DocumentSnapshot snapshot = transaction.get(progressRef);
            ProgressModel data = snapshot.toObject(ProgressModel.class);

            if (data != null) {
                data.setImage(img_url);
                transaction.set(progressRef, data);
            }

            return null;
        });
    }
}
