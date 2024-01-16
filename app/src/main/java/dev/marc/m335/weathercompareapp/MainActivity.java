package dev.marc.m335.weathercompareapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button nextBtn = (Button) findViewById(R.id.nextButton);
        TextInputLayout nameInput = (TextInputLayout) findViewById(R.id.nameTextfield);
        TextInputLayout apiIdInput = (TextInputLayout) findViewById(R.id.apiKeyLayout);

        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, CompareActivity.class);
                i.putExtra("nameOfUser",String.valueOf( nameInput.getEditText().getText()));
                i.putExtra("apiKey",String.valueOf(apiIdInput.getEditText().getText()));
                startActivity(i);

            }

        });
    }


}
