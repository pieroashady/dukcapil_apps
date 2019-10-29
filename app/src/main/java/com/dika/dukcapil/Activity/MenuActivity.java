package com.dika.dukcapil.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dika.dukcapil.Form.NikValidation;
import com.dika.dukcapil.Form.ScanForm;
import com.dika.dukcapil.Form.SimilarForm;
import com.dika.dukcapil.Form.TokenForm;
import com.dika.dukcapil.Form.VideoThumbnail;
import com.dika.dukcapil.R;
import com.google.android.material.snackbar.Snackbar;

public class MenuActivity extends AppCompatActivity {

    Button btnScan, btnSimilar, btnVideo, btnToken, btnNik;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        layout = findViewById(R.id.layout);

        btnScan = findViewById(R.id.btnValidate);
        btnSimilar = findViewById(R.id.btnSimilar);
        btnVideo = findViewById(R.id.btnVideo);
        btnToken = findViewById(R.id.btnToken);
        btnNik =  findViewById(R.id.btnNik);

        btnToken.setOnClickListener((c)->{
            goToTokenForm();
        });

        btnScan.setOnClickListener((x)->{
            goToScanActivity();
        });

        btnSimilar.setOnClickListener((v)->{
            goToVideoCheck();
        });

        btnVideo.setOnClickListener((f)->{
            Snackbar snackbar = Snackbar.make(layout, "Sedang dalam mode Pengembangan", Snackbar.LENGTH_LONG);
            snackbar.show();
        });

        btnNik.setOnClickListener((k)->{
            goToValidateNik();
        });

//        initToolbar();
    }

    private void goToValidateNik() {
        startActivity(new Intent(MenuActivity.this, NikValidation.class));
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Basic");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void goToTokenForm(){
        startActivity(new Intent(MenuActivity.this, TokenForm.class));
    }

    private void goToVideoThumbnail(){
        startActivity(new Intent(MenuActivity.this, VideoThumbnail.class));
    }

    private void goToScanActivity(){
        Intent intent = new Intent(MenuActivity.this, ScanForm.class);
        startActivity(intent);
    }

    private void goToVideoCheck(){
        startActivity(new Intent(MenuActivity.this, SimilarForm.class));
    }
}
