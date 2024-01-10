package dev.marc.m335.weathercompareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CompareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        TextView temperatureSensor = (TextView) findViewById(R.id.tempreatureSensor);
        temperatureSensor.setText("1");

        Bundle exttras = getIntent().getExtras();

        if (exttras != null) {
            String name = exttras.getString("name");
            TextView nameText = (TextView) findViewById(R.id.yourName);
            nameText.setText(name);
        }
    }
}