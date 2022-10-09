package com.example.ournews;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends MainActivity {
    EditText Password, Email;
    Button btnSignIn;
    private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.password);
        btnSignIn = findViewById(R.id.btnsignin);
        fAuth = FirebaseAuth.getInstance();

        btnSignIn.setOnClickListener(v -> loginUserAccount());
    }


    private void loginUserAccount() {
        String email, password;
        email = Email.getText().toString();
        password = Password.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email!!", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!!", Toast.LENGTH_LONG).show();
            return;
        }

        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) { Toast.makeText(getApplicationContext(), "Login successful!!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
                startActivity(intent);
            } else
            {
                Toast.makeText(getApplicationContext(), "Login failed!!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, RActivity.class);
                startActivity(intent);
            }
        });
    }
}