package com.example.beforeyoubuy.ui.favorite.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beforeyoubuy.DataBaseHandler;
import com.example.beforeyoubuy.NewProductScreen;
import com.example.beforeyoubuy.Produto;
import com.example.beforeyoubuy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.FavoriteViewHolder> {


    private final ArrayList<Produto> listaDeProdutos;
    private DataBaseHandler dataBaseHandler;

    public AdapterFavorite(ArrayList<Produto> listaDeProdutos) {
        this.listaDeProdutos = listaDeProdutos;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_favorite, parent, false);
        dataBaseHandler = DataBaseHandler.getInstance();
        return new FavoriteViewHolder(itemLista);
    }

    @Override
    public int getItemCount() {
        return listaDeProdutos.size();
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        StorageReference storageReference = dataBaseHandler.getStorageReference();
        String nome = listaDeProdutos.get(position).getName();

        try {
            File localfile = File.createTempFile(nome, "jpg");
            storageReference.child("images/" + nome + ".jpg").getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            holder.setImagem(bitmap);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.setNome("Produto: " + nome);
        holder.setPegada(listaDeProdutos.get(position).getPegadaEcologica());
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder{
        private ImageView imagem;
        private TextView nome;
        private TextView pegada;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imagem = itemView.findViewById(R.id.icon);
            this.nome = itemView.findViewById(R.id.nome);
            this.pegada = itemView.findViewById(R.id.pegada);
        }

        public void setImagem(Bitmap imagem) {
            this.imagem.setImageBitmap(imagem);
        }

        public void setNome(String nome) {
            this.nome.setText(nome);
        }

        public void setPegada(int pegada) {
            String text = "Pegada Ecólogica: " + pegada;
            this.pegada.setText(text);
        }
    }
}
