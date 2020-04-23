package com.afiq.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afiq.myapplication.databinding.ActivityProfileBinding;
import com.afiq.myapplication.models.ProfileModel;
import com.afiq.myapplication.services.UserService;
import com.afiq.myapplication.utilities.Database;
import com.afiq.myapplication.utilities.Interaction;
import com.afiq.myapplication.utilities.MyDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.SetOptions;
import com.roger.catloadinglibrary.CatLoadingView;

public class ProfileActivity extends AppCompatActivity implements OnCompleteListener<Void> {

    private String _name = "";
    private String _contact = "";
    private String _address = "";
    private String _error = "";

    private Boolean _profile_exist;

    private ActivityProfileBinding binding;
    private CatLoadingView dialog;

    private UserService service;
    private ProfileServiceConnection serviceConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        _profile_exist = getIntent().getBooleanExtra(Interaction.EXTRA_BOOLEAN_PROFILE_EXIST, true);

        bindService();

        setupActionBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        else {
            assert task.getException() != null;
            Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
        }

        dialog.dismiss();
    }

    public void onClickUpdate(View view) {
        collectUpdateInfo();
    }

    private void bindService() {
        Intent intent = new Intent(this, UserService.class);
        serviceConnection = new ProfileServiceConnection();

        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void setupActionBar() {
        setTitle("Update Profile");

        if (!_profile_exist && getSupportActionBar() != null) {
            setTitle("Create A Profile");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    private void collectUpdateInfo() {
        _error = "";

        _name = ((EditText) binding.name).getText().toString();
        _contact = ((EditText) binding.contact).getText().toString();
        _address = ((EditText) binding.address).getText().toString();

        validateUpdateInfo();
    }

    private void validateUpdateInfo() {
        if (_name.isEmpty() || _contact.isEmpty() || _address.isEmpty())
            _error += "All field must be fill.\n";

        if (!_error.isEmpty()) {
            MyDialog.showDialog(this, _error);
            return;
        }

        updateProfile();
    }

    private void updateProfile() {
        dialog = new CatLoadingView();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "");

        FirebaseUser user = Database.getUser();

        ProfileModel data = new ProfileModel();
        data.setEmail(user.getEmail());
        data.setName(_name);
        data.setContact(_contact);
        data.setAddress(_address);

        Database
                .DOC_PROFILE(Database.getUser().getUid())
                .set(data, SetOptions.merge())
                .addOnCompleteListener(this);
    }

    private class ProfileServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            service = ((UserService.UserBinder) binder).getService();
            service.getProfile().observe(ProfileActivity.this, data -> {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);

                if (data != null) {
                    binding.name.setText(data.getName());
                    binding.contact.setText(data.getContact());
                    binding.address.setText(data.getAddress());

                    if (!_profile_exist) Interaction.nextEnd(ProfileActivity.this, intent);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            service.getProfile().removeObservers(ProfileActivity.this);
        }
    }
}
