package com.example.ournews;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.AdapterView;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RActivity extends AppCompatActivity {

    EditText Password, Email;
    Button signup, signin;
    Spinner spinner;
    public static final String[] languages = {"Select Language","English", "Gujarati" , "Hindi", "Japanese", "Malayalam", "Marathi"};
    private FirebaseAuth fAuth;
    @SuppressLint("WrongViewCast")
    @SuppressWarnings("Convert2Lambda")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ractivity);

        Password = findViewById(R.id.password);
        Email = findViewById(R.id.email);
        signup = findViewById(R.id.btnsignup);
        signin = findViewById(R.id.btnsignin);
        spinner = findViewById(R.id.spinner);
        fAuth = FirebaseAuth.getInstance();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_selected_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectlang = parent.getItemAtPosition(position).toString();
                switch (selectlang) {
                    case "English":
                        setLocal(RActivity.this, "en");
                        finish();
                        startActivity(getIntent());
                        break;
                    case "Gujarati":
                        setLocal(RActivity.this, "gu");
                        finish();
                        startActivity(getIntent());
                        break;
                    case "Hindi":
                        setLocal(RActivity.this, "hi");
                        finish();
                        startActivity(getIntent());
                        break;
                    case "Japanese":
                        setLocal(RActivity.this, "ja");
                        finish();
                        startActivity(getIntent());
                        break;
                    case "Malayalam":
                        setLocal(RActivity.this, "ml");
                        finish();
                        startActivity(getIntent());
                        break;
                    case "Marathi":
                        setLocal(RActivity.this, "mr");
                        finish();
                        startActivity(getIntent());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void registerNewUser() {
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
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RActivity.this, MainActivity2.class);
                startActivity(intent);
            } else
            {
                Toast.makeText(getApplicationContext(), "Registration failed!!" + " Please try again later", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setLocal(Activity activity, String langcode){
        Locale locale = new Locale(langcode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}