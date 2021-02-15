package com.example.beforeyoubuy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.beforeyoubuy.Handlers.DataBaseHandler;
import com.example.beforeyoubuy.main.NavigationDrawer;

public class LoadingScreen extends AppCompatActivity {
    private final String[] PERMISSIONS_CAMERA = {Manifest.permission.CAMERA};
    private final int REQUEST_CAMERA = 1;

    private final int SPLASH_TIME_OUT = 3000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /******************************Verifica Permissões***********************************/
        verifyPermissions();

        /******************************Verifica Permissões***********************************/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        /******************************Loading screen****************************************/
        new Handler().postDelayed(() -> {
            Intent loadingScreen = new Intent(LoadingScreen.this, NavigationDrawer.class);
            startActivity(loadingScreen);
            finish();
        }, SPLASH_TIME_OUT);
        /******************************Loading screen****************************************/
        DataBaseHandler dbHandler = DataBaseHandler.getInstance();
    }
    private void verifyPermissions() {
        verifyCameraPermissions();
    }

    private void verifyCameraPermissions() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, PERMISSIONS_CAMERA, REQUEST_CAMERA);
        }
    }
}