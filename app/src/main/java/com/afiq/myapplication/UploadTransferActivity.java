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
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.afiq.myapplication.databinding.ActivityUploadTransferBinding;
import com.afiq.myapplication.models.PaymentModel;
import com.afiq.myapplication.utilities.Database;
import com.afiq.myapplication.utilities.Interaction;
import com.afiq.myapplication.viewmodels.PaymentViewModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.SetOptions;
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

    private String user_id;
    private String agent_id;
    private String project_id;
    private String progress_id;
    private PaymentModel data;
    private Uri uri;

    private ActivityUploadTransferBinding binding;

    private CatLoadingView loadingUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadTransferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user_id = getIntent().getStringExtra(Interaction.EXTRA_STRING_USER_ID);
        agent_id = getIntent().getStringExtra(Interaction.EXTRA_STRING_AGENT_ID);
        project_id = getIntent().getStringExtra(Interaction.EXTRA_STRING_PROJECT_ID);
        progress_id = getIntent().getStringExtra(Interaction.EXTRA_STRING_PROGRESS_ID);

        setTitle("Upload Transfer");

        bindPaymentViewModel();
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

    private void updateUI(PaymentModel data) {
        if (data.getAccepted())
            binding.submit.setVisibility(View.GONE);

        if (!data.getReceipt().isEmpty())
            Glide.with(this).load(data.getReceipt()).into(binding.viewer);

        binding.transfer.setText(String.valueOf(data.getTransfer()));
        binding.reference.setText(data.getReference());
        binding.description.setText(data.getDescription());
    }

    private void bindPaymentViewModel() {
        PaymentViewModel vm = new ViewModelProvider(this).get(PaymentViewModel.class);
        vm.getData().observe(this, this::progressListener);
        vm.start(Database.DOC_PAYMENT(progress_id));
    }

    private void progressListener(PaymentModel data) {
        this.data = data;

        if (this.data == null) {
            this.data = new PaymentModel();
            this.data.setUserID(user_id);
            this.data.setAgentID(agent_id);
            this.data.setProjectID(project_id);
        }

        updateUI(this.data);
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

        if (uri != null) {
            String pathname = "mobile/receipt/" + data.getUserID() + "/" + progress_id;
            StorageReference reference = FirebaseStorage.getInstance().getReference().child(pathname);

            reference
                    .putFile(uri)
                    .continueWithTask(task -> reference.getDownloadUrl())
                    .addOnCompleteListener(this::uriListener);
        } else {
            updateProgress(data.getReceipt());
        }
    }

    private void uriListener(Task<Uri> task) {
        Uri downloadUri = task.getResult();

        if (downloadUri != null) updateProgress(downloadUri.toString());
    }

    private void updateProgress(String url) {
        String transfer = ((EditText) binding.transfer).getText().toString();
        String reference = ((EditText) binding.reference).getText().toString();
        String description = ((EditText) binding.description).getText().toString();

        data.setReceipt(url);
        data.setTransfer(Integer.parseInt(transfer));
        data.setReference(reference);
        data.setDescription(description);

        Database
                .DOC_PAYMENT(progress_id).set(data, SetOptions.merge())
                .addOnCompleteListener(this::paymentSetListener);
    }

    private void paymentSetListener(Task<Void> task) {
        loadingUpload.dismiss();
    }
}
