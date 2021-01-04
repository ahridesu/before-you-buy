package com.example.beforeyoubuy;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class MainActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    private ImageView imageView;
/*
    private StorageReference mStorageRef;

    private ArrayList<String> images;
*/

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        this.mCodeScanner = new CodeScanner(this, scannerView);
        //Caso queira dar scan a mais que um objeto continuamente
        //mCodeScanner.setScanMode(ScanMode.CONTINUOUS);
        /*
        recyclerView = findViewById(R.id.recyclerview_gallery_images);
        recyclerView.setVisibility(View.INVISIBLE);

         */
        /******************************Verifica Permissões***********************************/
        checkFilePermissions();
        verifyStoragePermissions(this);
        //loadImages();

        /******************************Verifica Permissões***********************************/
        /****************************Tentativa firebase**************************************/

/*
        mStorageRef = FirebaseStorage.getInstance().getReference();
        checkFilePermissions();
        String imagem = getImagem();
        if(imagem != null)
            Log.e("***********",imagem);
        if(imagem != null){
            Uri uri = Uri.fromFile(new File(imagem));
            StorageReference storageReference = mStorageRef.child("images/imagem1.png");
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //System.out.println("VAMOS");
                    Toast.makeText(MainActivity.this, "VAMOS", Toast.LENGTH_LONG);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //System.out.println("MERDA");
                    Toast.makeText(MainActivity.this, "MERDA", Toast.LENGTH_LONG);
                }
            });
        }
        Toast.makeText(MainActivity.this, "NO IMAGENS", Toast.LENGTH_LONG);
*/
        /****************************Tentativa firebase**************************************/
        this.mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //TODO
                        // Favoritos
                        // Eventualmente ter mais exemplos
                        // Criar um botão por baixo da imagem do produto que leva a uma outra imagem com mais detalhes
                        // Botão canto superior esquerdo tipo definições

                        if(result.getText().equals("google")){
                            Toast.makeText(MainActivity.this, result.getText(), Toast.LENGTH_LONG).show();
                        } else if(result.getText().equals("firefox")){
                            Toast.makeText(MainActivity.this, result.getText(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
                imageView.setVisibility(View.INVISIBLE);
            }
        });
    }
/*
    private String getImagem() {
        for(String imagem : images){
            if(imagem.contains("imagem1")){
                return imagem;
            }
        }
        return null;
    }
*/
    private static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
/*
    private void loadImages() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        images = ImagesGallery.listOfImages(this);
        for(String image : images){
            Log.e("#########",image);
        }
    }
 */
    private void checkFilePermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = MainActivity.this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += MainActivity.this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {
                this.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        }else{
            Log.d("MainActivity", "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

}