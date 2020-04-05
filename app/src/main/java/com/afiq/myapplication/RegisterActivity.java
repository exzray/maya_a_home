package com.afiq.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afiq.myapplication.databinding.ActivityRegisterBinding;
import com.afiq.myapplication.utilities.FirebaseHelper;
import com.afiq.myapplication.utilities.MyDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.roger.catloadinglibrary.CatLoadingView;

public class RegisterActivity extends AppCompatActivity implements OnCompleteListener<AuthResult> {

    private String _email = "";
    private String _password = "";
    private String _retype = "";
    private String _error = "";

    private ActivityRegisterBinding binding;
    private CatLoadingView dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupActionBar();
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            onBackPressed();

        } else {
            assert task.getException() != null;
            MyDialog.showDialog(this, task.getException().getMessage());
        }

        dialog.dismiss();
    }

    public void onClickSignUp(View view) {
        collectRegisterInfo();
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            setTitle("Register");

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void collectRegisterInfo() {
        _error = "";

        _email = ((EditText) binding.email).getText().toString();
        _password = ((EditText) binding.password).getText().toString();
        _retype = ((EditText) binding.retype).getText().toString();

        validateRegisterInfo();
    }

    private void validateRegisterInfo() {
        if (_email.isEmpty() || _password.isEmpty() || _retype.isEmpty())
            _error += "All field must be fill.\n";
        if (_password.length() < 6) _error += "Password minimum must 6 character long.\n";
        if (!_password.equals(_retype)) _error += "Your password is not same.";

        if (!_error.isEmpty()) {
            MyDialog.showDialog(this, _error);
            return;
        }

        createAccount();
    }

    private void createAccount() {
        dialog = new CatLoadingView();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "");

        FirebaseHelper.getAuth()
                .createUserWithEmailAndPassword(_email, _password)
                .addOnCompleteListener(this);
    }
}
