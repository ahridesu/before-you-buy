package com.example.beforeyoubuy.ui.favorite;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beforeyoubuy.Handlers.DataBaseHandler;
import com.example.beforeyoubuy.models.Produto;
import com.example.beforeyoubuy.R;
import com.example.beforeyoubuy.ui.favorite.adapter.AdapterFavorite;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity{
    /*Variables*/

    //RecyclerView
    private RecyclerView recyclerView;

    /**
     * Creates a view of the list of favorites.
     * @param bundle
     */
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_favorite);

        //RecyclerView of favorites.
        this.recyclerView = findViewById(R.id.recyclerView);
        //Database Handler
        DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance();
        //List of all favorite products
        ArrayList<Produto> listaDeProdutos = dataBaseHandler.getFavoritos();
        //Adapter of favorite products
        AdapterFavorite adapter = new AdapterFavorite(listaDeProdutos);

        //Configure the RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getBaseContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


    }



}

/*import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beforeyoubuy.Handlers.DataBaseHandler;
import com.example.beforeyoubuy.models.Produto;
import com.example.beforeyoubuy.R;
import com.example.beforeyoubuy.ui.favorite.Adapter.AdapterFavorite;

import java.util.ArrayList;

public class FavoriteActivity extends Fragment {
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_favorite, container, false);
        this.recyclerView = root.findViewById(R.id.recyclerView);
        DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance();

        //Configurar o RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //Lista de Produtos Favoritos!
        ArrayList<Produto> listaDeProdutos = dataBaseHandler.getFavoritos();

        AdapterFavorite adapter = new AdapterFavorite(listaDeProdutos);

        recyclerView.setAdapter(adapter);

        return root;
    }
}*/