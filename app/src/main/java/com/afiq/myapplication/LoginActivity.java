package com.afiq.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afiq.myapplication.databinding.ActivityLoginBinding;
import com.afiq.myapplication.utilities.Database;
import com.afiq.myapplication.utilities.Interaction;
import com.afiq.myapplication.utilities.MyDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.roger.catloadinglibrary.CatLoadingView;

public class LoginActivity extends AppCompatActivity implements OnCompleteListener<AuthResult> {

    private String _email = "";
    private String _password = "";
    private String _error = "";

    private ActivityLoginBinding binding;
    private CatLoadingView dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupActionBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPreviousUser();
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
        dialog = new CatLoadingView();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "");

        FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(_email, _password)
                .addOnCompleteListener(this);
    }

    private void checkPreviousUser() {
        SharedPreferences preferences = getSharedPreferences(Interaction.APPLICATION_NAME, Context.MODE_PRIVATE);
        String _email = preferences.getString(Interaction.SHARED_SAVED_USER_EMAIL, "");

        binding.email.setText(_email);
    }
}
