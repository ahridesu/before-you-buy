package com.example.beforeyoubuy.Handlers;

import android.media.Image;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.beforeyoubuy.R;
import com.example.beforeyoubuy.models.Produto;
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
    //link para o firebase https://console.firebase.google.com/u/0/project/beforeyoubuy-1a840/overview
    //falar com o Ricardo para dar permissões!
    //Padrão Singleton! Só há uma instância desta classe
    private static DataBaseHandler INSTANCE = null;

    private final String REMOVER = "remover";
    private final int ECOLOGICAL_FOOTPRINT = 0;
    private final String FAVORITO = "favoritos";
    private ArrayList<Produto> listaDeProdutos;
    private ArrayList<Produto> favoritos;

    private DatabaseReference databaseRealtime;
    private final String INSTANCE_FIREBASE_REALTIME = "https://beforeyoubuy-1a840-default-rtdb.europe-west1.firebasedatabase.app/"; //link para o firebase

    private StorageReference storageRef;
    private final String INSTANCE_FIREBASE_STORAGE = "gs://beforeyoubuy-1a840.appspot.com";

    public static DataBaseHandler getInstance(){
        if(INSTANCE == null){ //SINGLETON
            INSTANCE = new DataBaseHandler();
        }
        return INSTANCE;
    }

    protected DataBaseHandler(){
        this.listaDeProdutos = new ArrayList<>();
        this.databaseRealtime = FirebaseDatabase.getInstance(INSTANCE_FIREBASE_REALTIME).getReference();
        this.storageRef = FirebaseStorage.getInstance(INSTANCE_FIREBASE_STORAGE).getReference();
        this.favoritos = new ArrayList<>();
        databaseRealtime.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.e("Child added", "");
                //Verificar este link
                //https://console.firebase.google.com/u/0/project/beforeyoubuy-1a840/database/beforeyoubuy-1a840-default-rtdb/data
                //E como queremos aceder ao favoritos comparamos a key com isso
                if(snapshot.getKey().equals(FAVORITO)){
                    HashMap key = (HashMap) snapshot.getValue();
                    Log.e("DataSnapShot key", key.toString());
                    //Para cada favorito que exista é na verdade um hashmap porque é um produto e tem varios atributos
                    for(Object s : key.keySet()){
                        HashMap product = (HashMap) key.get(s);
                        Log.e("Obj", product.toString());

                        String id = (String) product.get("id");
                        String name = (String) product.get("name");
                        //TODO check what is Long doing here
                        int ecologicalFootprint = ((Long) product.get("pegadaEcologica")).intValue();
                        //int image = (int) product.get("image");

                        //criar o produto com o que foi recolhido na base de dados
                        Produto produto = new Produto(id, name, ecologicalFootprint, 0);
                        //Verificar se ele já está na lista de favoritos, porque isto pode ser chamado varias vezes desnecessariamente
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
        //Para dar trigger ao childAdded e meter os favoritos na lista
        databaseRealtime.child(REMOVER).setValue(REMOVER);
        databaseRealtime.child(REMOVER).removeValue();
    }

    private void loadProdutos() {
        //TODO dynamic
        //De momento isto está assim porque são poucos produtos mas no futuro vai estar na base de dados
        listaDeProdutos.add(new Produto("8480017005045", "Pacote de Leite", ECOLOGICAL_FOOTPRINT, R.drawable.pacote_de_leite));
        listaDeProdutos.add(new Produto("7622300165628", "Mini Oreo", ECOLOGICAL_FOOTPRINT, R.drawable.mini_oreo));
    }

    /**
     * Adiciiona um produto
     * @param name nome do Produto
     * @param id id do produto
     * @param pegadaEcologica pegada ecologica do produto
     * requires nao seja favorito!
     */
    public void addFavorite(String id, String name, int pegadaEcologica, int image){
        Log.e("Adicionado Favorito", name);
        favoritos.add(new Produto(id, name, pegadaEcologica,image));
        databaseRealtime.child(FAVORITO).child(id).setValue(new Produto(id, name, pegadaEcologica,image));
    }

    /**
     * Verificar se é favorito dado um nome de um produto
     * @param favorito
     * @return
     */
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

    /**
     * Remove o produto com aquele nome dos favoritos
     * @param nome
     */
    public void removeFavorite(String nome) {
        if(nome != null) {
            Log.e("Removido Favorito", nome);
            databaseRealtime.child(FAVORITO).child(nome).removeValue();
            removeProduto(nome);
        }
    }

    /**
     * Remove o produto com aquele nome dos favoritos
     * @param nome
     */
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


    /**
     * Vai buscar a pegada ecologica recebendo um nome de um produto
     * @param produto
     * @return
     */
    public int getPegadaEcologica(String produto){
        for(Produto p : listaDeProdutos){
            if(p.getName().equals(produto)){
                return p.getPegadaEcologica();
            }
        }
        return -1;
    }



    /**
     * Devolve a lista de favoritos
     * @return
     */
    public ArrayList<Produto> getFavoritos() {
        return favoritos;
    }

    /**
     * Recebe um ID e devolve o nome desse produto
     * @param produto
     * @return
     */
    public String getNomeProduto(String produto) {
        for(Produto p : listaDeProdutos){
            if(p.getId().equals(produto)){
                return p.getName();
            }
        }
        return null;
    }

    /**
     * Devolve a storageRef!
     * @return
     */

    public StorageReference getStorageReference() {
        return storageRef;
    }
}
