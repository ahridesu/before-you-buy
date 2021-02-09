package com.example.beforeyoubuy.Handlers;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.beforeyoubuy.R;

public class ButtonHandler {
    //Classe para lidar com os botões
    // O de favorito e o outro que pode ser para ir para o website do produto ou para registar o produto

    private Button button;
    private ImageButton imageButton;
    private String produto;
    private String id;
    private int pegadaEcologica;

    public ButtonHandler(Button button, ImageButton imageButton, DataBaseHandler dataBaseHandler){
        this.button = button;
        this.imageButton = imageButton;
        imageButton.setOnClickListener(new View.OnClickListener() { //Tira ou remove o produto dos favoritos tendo em conta ele existir ou não
            @Override
            public void onClick(View v) {
                if(dataBaseHandler.isFavorite(produto)){
                    dataBaseHandler.removeFavorite(produto);
                    imageButton.setImageResource(R.drawable.pre_favorite);
                } else {
                    dataBaseHandler.addFavorite(produto, id, pegadaEcologica);
                    imageButton.setImageResource(R.drawable.favorite);
                }
            }
        });
    }

    public void newProduct(String produto, String id, int pegadaEcologica) {
        this.produto = produto;
        this.id = id;
        this.pegadaEcologica = pegadaEcologica;
        button.setBackgroundColor(Color.WHITE);
        button.setText(produto);
        button.setVisibility(View.VISIBLE);
    }

    public void invisible() { //Mete os botões invisiveis
        this.button.setVisibility(View.INVISIBLE);
        this.imageButton.setVisibility(View.INVISIBLE);
    }

    public Button getButton() {
        return button;
    }
}
