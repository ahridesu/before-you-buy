package com.example.beforeyoubuy;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
public class DataBaseHandler {

    private static DataBaseHandler INSTANCE = null;

    private final String REMOVER = "remover";
    private final int PEGADA_ECOLOGICA = 0;
    private final String FAVORITO = "favoritos";
    private ArrayList<Produto> listaDeProdutos;
    private ArrayList<Produto> favoritos;

    private DatabaseReference databaseRealtime;
    private final String INSTANCE_FIREBASE_REALTIME = "https://beforeyoubuy-1a840-default-rtdb.europe-west1.firebasedatabase.app/";

    private StorageReference storageRef;
    private final String INSTANCE_FIREBASE_STORAGE = "gs://beforeyoubuy-1a840.appspot.com";

    public static DataBaseHandler getInstance(){
        if(INSTANCE == null){ //SINGLETON
            INSTANCE = new DataBaseHandler();
        }
        return INSTANCE;
    }

    protected DataBaseHandler(){
        listaDeProdutos = new ArrayList<>();
        this.databaseRealtime = FirebaseDatabase.getInstance(INSTANCE_FIREBASE_REALTIME).getReference();
        this.storageRef = FirebaseStorage.getInstance(INSTANCE_FIREBASE_STORAGE).getReference();
        this.favoritos = new ArrayList<>();
        databaseRealtime.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.e("Child added", "");
                if(snapshot.getKey().equals(FAVORITO)){
                    HashMap key = (HashMap) snapshot.getValue();
                    Log.e("DataSnapShot key", key.toString());
                    for(Object s : key.keySet()){
                        HashMap obj = (HashMap) key.get(s);
                        Log.e("Obj", obj.toString());
                        String name = (String) obj.get("name");
                        String value = (String) obj.get("id");
                        int pegadaEcologica = ((Long) obj.get("pegadaEcologica")).intValue();
                        Produto produto = new Produto(name, value, pegadaEcologica);
                        if(!isFavorite(produto.getName())){
                            favoritos.add(produto);
                            Log.e("Favorito " , "Adicionado");
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //Do nothing
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                //Do nothing
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //Do nothing
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Base de dados", "Cancelado");
            }
        });
        loadProdutos();
        databaseRealtime.child(REMOVER).setValue(REMOVER);
        databaseRealtime.child(REMOVER).removeValue();
    }

    private void loadProdutos() {
        listaDeProdutos.add(new Produto("Pacote de Leite", "8480017005045", PEGADA_ECOLOGICA));
        listaDeProdutos.add(new Produto("Mini Oreo", "7622300165628", PEGADA_ECOLOGICA));
    }

    /**
     *
     * @param nome nome do Produto
     * @param id id do produto
     * @param pegadaEcologica pegada ecologica do produto
     * requires nao seja favorito!
     */
    public void addFavorite(String nome, String id, int pegadaEcologica){
        Log.e("Adicionado Favorito", nome);
        favoritos.add(new Produto(nome, id, pegadaEcologica));
        databaseRealtime.child(FAVORITO).child(nome).setValue(new Produto(nome, id, pegadaEcologica));
    }

    public boolean isFavorite(String favorito) {
        Log.e("Produto", favorito);
        Log.e("Favoritos", favoritos.toString());
        for(Produto p : favoritos){
            if(p.getName().equals(favorito)){
                Log.e("return", "true");
                return true;
            }
        }
        Log.e("return", "false");
        return false;
    }

    public void removeFavorite(String nome) {
        if(nome != null) {
            Log.e("Removido Favorito", nome);
            databaseRealtime.child(FAVORITO).child(nome).removeValue();
            removeProduto(nome);
        }
    }

    private void removeProduto(String nome) {
        if(nome != null){
            int count = 0;
            for (Produto p : favoritos) {
                if(p.getName().equals(nome)){
                    favoritos.remove(count);
                    break;
                } else {
                    count++;
                }
            }
        }
    }

    public int getPegadaEcologica(String produto){
        for(Produto p : listaDeProdutos){
            if(p.getName().equals(produto)){
                return p.getPegadaEcologica();
            }
        }
        return 0;
    }

    public ArrayList<Produto> getFavoritos() {
        return favoritos;
    }

    public String getNomeProduto(String produto) {
        for(Produto p : listaDeProdutos){
            if(p.getId().equals(produto)){
                return p.getName();
            }
        }
        return null;
    }

    public StorageReference getStorageReference() {
        return storageRef;
    }
}
