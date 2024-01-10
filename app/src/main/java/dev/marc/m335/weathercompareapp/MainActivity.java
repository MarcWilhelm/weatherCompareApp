package dev.marc.m335.weathercompareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button nextBtn = (Button)findViewById(R.id.nextButton);
        TextInputLayout nameInput = (TextInputLayout) findViewById(R.id.nameInput);
       if (nameInput.getEditText().getText().toString().matches("")){
           nextBtn.setEnabled(false);
       }
       else {
           nextBtn.setEnabled(true);
       }




        Intent i = new Intent(MainActivity.this, CompareActivity.class);
        i.putExtra("name", "Marc");
        startActivity(i);

    }
}