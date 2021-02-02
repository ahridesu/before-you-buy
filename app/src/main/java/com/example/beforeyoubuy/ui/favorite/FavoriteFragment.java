package com.example.beforeyoubuy.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beforeyoubuy.DataBaseHandler;
import com.example.beforeyoubuy.Produto;
import com.example.beforeyoubuy.R;
import com.example.beforeyoubuy.ui.favorite.Adapter.AdapterFavorite;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        this.recyclerView = root.findViewById(R.id.recyclerView);
        DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance();

        //Configurar o RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        ArrayList<Produto> listaDeProdutos = dataBaseHandler.getFavoritos();

        AdapterFavorite adapter = new AdapterFavorite(listaDeProdutos);

        recyclerView.setAdapter(adapter);

        return root;
    }
}