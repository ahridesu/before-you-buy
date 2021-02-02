package com.example.beforeyoubuy.ui.favorite.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beforeyoubuy.Produto;
import com.example.beforeyoubuy.R;

import java.util.ArrayList;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.FavoriteViewHolder> {


    private final ArrayList<Produto> listaDeProdutos;

    public AdapterFavorite(ArrayList<Produto> listaDeProdutos) {
        this.listaDeProdutos = listaDeProdutos;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_favorite, parent, false);
        return new FavoriteViewHolder(itemLista);
    }

    @Override
    public int getItemCount() {
        return listaDeProdutos.size();
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {

        holder.setImagem(listaDeProdutos.get(position).getImagem());
        holder.setNome("Produto: " + listaDeProdutos.get(position).getName());
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

        public void setImagem(int imagem) {
            this.imagem.setImageResource(imagem);
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
