package com.example.ournews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 0;
    private static final int STORAGE_PERMISSION_CODE = 1;
    TextView month, day, year, textview;
    Button btnNext;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        month = findViewById(R.id.month);
        day = findViewById(R.id.day);
        year = findViewById(R.id.year);
        textview = findViewById(R.id.tv);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, FrontPage.class);
            startActivity(intent);
        });
        Calendar calendar = Calendar.getInstance();
        int Hours = calendar.get(Calendar.HOUR_OF_DAY);
        if (Hours > 0 && Hours < 12) {
            textview.setText("Good Morning \uD83D\uDE0A");
        } else if (Hours >= 12 && Hours < 17) {
            textview.setText("Good AfterNoon \uD83C\uDF1E");
        } else if (Hours >= 17 && Hours < 21) {
            textview.setText("Good evening \uD83C\uDF04");
        } else {
            textview.setText("Good Night \uD83C\uDF19");
        }
        Date currentTime = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);
        String[] splitDate = formattedDate.split(",");
        Log.d("myLOG", currentTime.toString());
        Log.d("myLOG", formattedDate);
        day.setText(splitDate[0]);
        Log.d("myLOG",splitDate[0].trim());
        month.setText(splitDate[1]);
        Log.d("myLOG",splitDate[1].trim());
        year.setText(splitDate[2]);
        Log.d("myLOG",splitDate[2].trim());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity2.this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity2.this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity2.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity2.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}