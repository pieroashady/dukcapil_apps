package com.dika.dukcapil.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.dika.dukcapil.Form.ScanForm;
import com.dika.dukcapil.Form.SimilarForm;
import com.dika.dukcapil.R;

public class MenuActivity extends AppCompatActivity {

    Button btnScan, btnSimilar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnScan = findViewById(R.id.btnScan);
        btnSimilar = findViewById(R.id.btnSimilar);

        btnScan.setOnClickListener((x)->{
            goToScanActivity();
        });

        btnSimilar.setOnClickListener((v)->{
            goToVideoCheck();
        });

//        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Basic");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void goToScanActivity(){
        Intent intent = new Intent(MenuActivity.this, ScanForm.class);
        startActivity(intent);
    }

    private void goToVideoCheck(){
        startActivity(new Intent(MenuActivity.this, SimilarForm.class));
    }
}
