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

    //Class to handle the buttons
    //Handles favorite and ?another? that can redirect to the products website or to register that product
    //

    private Button button;
    private ImageButton imageButton; //Add Image Name home in Drawable Folder to customize button image.
    private String produto; //product name
    private String id; //product id, barcode code
    private int pegadaEcologica; //associated ecological footprint

    /**
     * treats favorites, removing and adding to the database accordingly to the database information
     *
     * TODO change if statement at @35 so it does not access database - take current status of button
     *
     * @param button- button to handle
     * @param imageButton - image that customizes button
     * @param dataBaseHandler - communicates with the database
     */
    public ButtonHandler(Button button, ImageButton imageButton, DataBaseHandler dataBaseHandler){
        this.button = button;
        this.imageButton = imageButton;
        //event listener on imageButton or button
        imageButton.setOnClickListener(new View.OnClickListener() {
            //Tira ou remove o produto dos favoritos tendo em conta ele existir ou não
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

    /**
     * creates a product associating name with the respective code and ecological footprint
     * @param produto - name of the product
     * @param id - product id - barcode code
     * @param pegadaEcologica - value of ecological footprint referring to @produto
     */
    public void newProduct(String produto, String id, int pegadaEcologica) {
        this.produto = produto;
        this.id = id;
        this.pegadaEcologica = pegadaEcologica;
        button.setBackgroundColor(Color.WHITE);
        button.setText(produto);
        button.setVisibility(View.VISIBLE);
    }

    /**
     * sets a button, and the respective image view invisible
     */
    public void invisible() { //Mete os botões invisiveis
        this.button.setVisibility(View.INVISIBLE);
        this.imageButton.setVisibility(View.INVISIBLE);
    }

    /**
     * returns ?current? @button
     * @return Button
     */
    public Button getButton() {
        return button;
    }
}
