package dev.marc.m335.weathercompareapp;


import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
        TextInputLayout nameInput = (TextInputLayout) findViewById(R.id.name);
    /*   nameInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() == 0) {
                    nextBtn.setEnabled(false);
                } else {
                    nextBtn.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/
        TextInputLayout apiIdInput = (TextInputLayout) findViewById(R.id.apiKeyLayout);
    /*    nameInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() == 0) {
                    nextBtn.setEnabled(false);
                } else {
                    nextBtn.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/



        nextBtn.setEnabled(true);
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


        System.out.println(apiIdInput.getEditText());
        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, CompareActivity.class);
                i.putExtra("name", nameInput.getEditText().getText());
                i.putExtra("apiKey", apiIdInput.getEditText().getText());
                startActivity(i);

            }

        });
    }


}
