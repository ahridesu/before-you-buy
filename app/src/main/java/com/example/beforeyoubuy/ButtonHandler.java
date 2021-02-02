package com.example.beforeyoubuy;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ButtonHandler {

    private Button button;
    private ImageButton imageButton;
    private DataBaseHandler dataBaseHandler;
    private String produto;
    private String id;
    private int pegadaEcologica;
    private int imagem;

    public ButtonHandler(Button button, ImageButton imageButton, DataBaseHandler dataBaseHandler){
        this.button = button;
        button.setVisibility(View.INVISIBLE);
        this.imageButton = imageButton;
        this.dataBaseHandler = dataBaseHandler;
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataBaseHandler.isFavorite(produto)){
                    dataBaseHandler.removeFavorite(produto);
                    imageButton.setImageResource(R.drawable.pre_favorite);
                } else {
                    dataBaseHandler.addFavorite(produto, id, pegadaEcologica, imagem);
                    imageButton.setImageResource(R.drawable.favorite);
                }
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    public void newProduct(String produto, String id, int pegadaEcologica, int imagem) {
        button.setBackgroundColor(Color.WHITE);
        button.setText(produto);
        button.setVisibility(View.VISIBLE);
        this.produto = produto;
        this.id = id;
        this.pegadaEcologica = pegadaEcologica;
        this.imagem = imagem;
    }

    public void invisible() {
        this.button.setVisibility(View.INVISIBLE);
        this.imageButton.setVisibility(View.INVISIBLE);
    }
}
