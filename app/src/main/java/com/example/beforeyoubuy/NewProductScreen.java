package com.example.beforeyoubuy;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class NewProductScreen extends AppCompatActivity {

    private HashMap<String, String[]> categorias;
    private Spinner spinnerCat;
    private Spinner spinnerSubCat;
    private Spinner location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product_screen);

        this.categorias = new HashMap<>();

        setUpCat();

        setUpSpinner();

        spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
                spinnerSubCat.setVisibility(View.VISIBLE);
                List<String> subCat = Arrays.asList(categorias.get(s));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(NewProductScreen.this, android.R.layout.simple_spinner_item, subCat);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSubCat.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setUpSpinner() {
        spinnerCat = findViewById(R.id.spinnerCat);
        /*Adapter*/
        String[] cat = getResources().getStringArray(R.array.categorias);
        List<String> categorias = Arrays.asList(cat);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCat.setAdapter(adapter);

        spinnerSubCat = findViewById(R.id.spinnerSubCat);

        spinnerSubCat.setVisibility(View.INVISIBLE);

        location = findViewById(R.id.loc_spin);
        String[] loc = getResources().getStringArray(R.array.Localizaçao);
        List<String> localizacao = Arrays.asList(loc);
        ArrayAdapter<String> locAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,localizacao);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(locAdapter);
    }

    private void setUpCat() {
        Resources res = getResources();

        String[] cat = res.getStringArray(R.array.categorias);

        for(String s : cat){
            switch (s){
                case "Mercearia Doce":
                    categorias.put(s, res.getStringArray(R.array.Mercearia_Doce));
                    break;
                case "Produtos Lacteos":
                    categorias.put(s, res.getStringArray(R.array.Produtos_Lacteos));
                    break;
                default:
                    Log.e("Categoria", "Default");
                    break;
            }
        }

    }
}
