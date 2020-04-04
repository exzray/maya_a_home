package com.afiq.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afiq.myapplication.databinding.ActivityLoginBinding;
import com.afiq.myapplication.utilities.MyDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements OnCompleteListener<AuthResult> {

    private String _email = "";
    private String _password = "";
    private String _error = "";

    private ActivityLoginBinding binding;
    private ProgressDialog dialog;

    private FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
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

    public void onClickSignIn(View view) {
        collectLoginInfo();
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            setTitle("Login");

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void collectLoginInfo() {
        _error = "";

        _email = ((EditText) binding.email).getText().toString();
        _password = ((EditText) binding.password).getText().toString();

        validateRegisterInfo();
    }

    private void validateRegisterInfo() {
        if (_email.isEmpty() || _password.isEmpty())
            _error += "All field must be fill.\n";
        if (_password.length() < 6) _error += "Password minimum must 6 character long.\n";

        if (!_error.isEmpty()) {
            MyDialog.showDialog(this, _error);
            return;
        }

        authenticateAccount();
    }

    private void authenticateAccount() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait while we getting your account information");
        dialog.setCancelable(false);
        dialog.show();

        auth
                .signInWithEmailAndPassword(_email, _password)
                .addOnCompleteListener(this);
    }
}
