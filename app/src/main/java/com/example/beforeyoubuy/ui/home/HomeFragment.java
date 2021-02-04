package com.example.beforeyoubuy.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.beforeyoubuy.ButtonHandler;
import com.example.beforeyoubuy.DataBaseHandler;
import com.example.beforeyoubuy.NewProductScreen;
import com.example.beforeyoubuy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.Result;

import java.io.File;
import java.io.IOException;

public class HomeFragment extends Fragment {

    private final String TITULO = "";
    private CodeScanner mCodeScanner;
    private ImageView imageView;
    private DataBaseHandler dataBaseHandler;
    private ButtonHandler buttonHandler;
    private Button button;
    private CodeScannerView scannerView;
    private View root;

    private Activity activity;
    private ImageButton favorite;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);
        activity = getActivity();

        setUpToolbar();

        setUpScanner();

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(" New product scanned: ",result.getText());
                        getProduto(result.getText());
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setVisibility(View.INVISIBLE);
                buttonHandler.invisible();
                favorite.setVisibility(View.INVISIBLE);
                mCodeScanner.startPreview();
            }
        });
        return root;
    }

    private void setUpScanner() {
        this.favorite = root.findViewById(R.id.favorite);
        this.imageView = root.findViewById(R.id.imageView);
        this.dataBaseHandler = DataBaseHandler.getInstance();
        button = root.findViewById(R.id.button);
        scannerView = root.findViewById(R.id.scanner_view);
        this.buttonHandler = new ButtonHandler(button, favorite, dataBaseHandler);
        buttonHandler.invisible();
        imageView.setVisibility(View.INVISIBLE);
        mCodeScanner = new CodeScanner(activity, scannerView);
    }

    /**
     * @requires R.id.toolbar exista
     */
    private void setUpToolbar() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(TITULO);
    }


    private void getProduto(String produto) {
        int pegadaEcologica = dataBaseHandler.getPegadaEcologica(produto);
        String nome = dataBaseHandler.getNomeProduto(produto);
        if(nome != null){
            StorageReference storageReference = dataBaseHandler.getStorageReference();
            try {
                File localfile = File.createTempFile(nome, "jpg");
                storageReference.child("images/" + nome + ".jpg").getFile(localfile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                imageView.setImageBitmap(bitmap);
                                imageView.setVisibility(View.VISIBLE);
                                imageView.setBackgroundColor(Color.TRANSPARENT);
                                buttonHandler.newProduct(nome, produto, pegadaEcologica);
                                favorite.setVisibility(View.VISIBLE);
                                if(dataBaseHandler.isFavorite(nome)){
                                    favorite.setImageResource(R.drawable.favorite);
                                } else {
                                    favorite.setImageResource(R.drawable.pre_favorite);
                                }
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Button button = buttonHandler.getButton();
            button.setBackgroundColor(Color.WHITE);
            button.setText("Registe um novo produto");
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Código para mudar para a futura atividade
                    Intent i = new Intent(getActivity(), NewProductScreen.class);
                    startActivity(i);
                    (getActivity()).overridePendingTransition(0, 0);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}