package com.example.beforeyoubuy.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.beforeyoubuy.R;
import com.example.beforeyoubuy.ui.allProducts.AllProductsActivity;
import com.example.beforeyoubuy.ui.favorite.FavoriteActivity;
import com.example.beforeyoubuy.ui.profile.ProfileActivity;
import com.example.beforeyoubuy.ui.scanner.ScannerActivity;

public class MainActivity extends AppCompatActivity {
    /**
     * Creates a View of the dashboard
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * Click listener for the dashboard button to acess other menus
     * @param v - view for the selected menu
     */
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            //Favorite menu
            case R.id.favorite_button:
                i = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(i);
                break;
            //Scanner menu.
            case R.id.scan_button:
                i = new Intent(MainActivity.this, ScannerActivity.class);
                startActivity(i);
                break;

            //Profile menu
            case R.id.profile_button:
                i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
                break;

            case R.id.allproducts_button:
                i = new Intent(MainActivity.this, AllProductsActivity.class);
                startActivity(i);
                break;
        }

    }
}