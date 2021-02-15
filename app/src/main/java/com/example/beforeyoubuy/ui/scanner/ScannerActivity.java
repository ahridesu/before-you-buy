package com.example.beforeyoubuy.ui.scanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.beforeyoubuy.Handlers.ButtonHandler;
import com.example.beforeyoubuy.Handlers.DataBaseHandler;
import com.example.beforeyoubuy.NewProductScreen;
import com.example.beforeyoubuy.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.Result;

import java.io.File;
import java.io.IOException;

public class ScannerActivity extends AppCompatActivity {
    /*Variables*/

    //Code Scanner
    private CodeScanner mCodeScanner;
    //Image view
    private ImageView imageView;
    //Database handler
    private DataBaseHandler dataBaseHandler;
    //Button handler
    private ButtonHandler buttonHandler;
    //Button
    private Button button;
    //Code scanner view
    private CodeScannerView scannerView;
    //ImageButton of favorites
    private ImageButton favorite;

    /**
     * Creates a view of the code bar scanner
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        //Setup of the scanner initializing all the buttons and images
        setupScanner();
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        //Barcode has been successfully decoded
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Product scanned: ", result.getText());
                        getProduct(result.getText());
                        //Toast.makeText(ScannerActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //Scanner click listener
        //Resets scanner to be able to scan again
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setVisibility(View.INVISIBLE);
                buttonHandler.invisible();
                mCodeScanner.startPreview();
            }
        });
    }

    /**
     * Setups the scanner initializing all the buttons and views
     */
    private void setupScanner() {
        this.favorite = findViewById(R.id.favorite);
        this.imageView = findViewById(R.id.imageView);
        this.dataBaseHandler = DataBaseHandler.getInstance();
        this.button = findViewById(R.id.button);
        this.buttonHandler = new ButtonHandler(button, favorite, dataBaseHandler);
        buttonHandler.invisible();
        imageView.setVisibility(View.INVISIBLE);
    }

    /**
     * Tries to load the product from the database and if successful shows it on the screen
     * If product not on the DB changes view to NewProduct and to insert on the database.
     * @param product - name of the product on the database
     */
    private void getProduct(String product){
        String name = dataBaseHandler.getNomeProduto(product);
        //If product exists
        if(name != null){
            StorageReference storageReference = dataBaseHandler.getStorageReference();
            try {
                //Loads information related to the product
                File localfile = File.createTempFile(name, "jpg"); //Gets the images
                storageReference.child("images/" + name + ".jpg").getFile(localfile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                //Always successful if enters the if-case
                                //Gets the pegadaEcologica of the product
                                int pegadaEcologica = dataBaseHandler.getPegadaEcologica(product);
                                //Bitmaps the image of the product
                                Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                //Shows the information on the screen
                                imageView.setImageBitmap(bitmap);
                                imageView.setVisibility(View.VISIBLE);
                                imageView.setBackgroundColor(Color.TRANSPARENT);
                                buttonHandler.newProduct(name, product, pegadaEcologica);
                                favorite.setVisibility(View.VISIBLE);
                                //If product is favorite
                                if(dataBaseHandler.isFavorite(name)){
                                    favorite.setImageResource(R.drawable.favorite);
                                } else { //if not favorite
                                    favorite.setImageResource(R.drawable.pre_favorite);
                                }
                            }
                        });
            } catch (IOException e) { //Error on reading file (database related)
                e.printStackTrace();
            }
        } else { //If product does not exist on the database
            //Setup up button to show NewProduct view
            Button button = buttonHandler.getButton();
            button.setBackgroundColor(Color.WHITE);
            button.setText("Registe um novo produto");
            button.setVisibility(View.VISIBLE);
            //Button onClick listener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Change view to new product
                    Intent i = new Intent(v.getContext(), NewProductScreen.class);
                    startActivity(i);
                    overridePendingTransition(0, 0);

                }
            });
        }

    };

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}


/*
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
import com.example.beforeyoubuy.Handlers.ButtonHandler;
import com.example.beforeyoubuy.Handlers.DataBaseHandler;
import com.example.beforeyoubuy.NewProductScreen;
import com.example.beforeyoubuy.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.Result;

import java.io.File;
import java.io.IOException;

public class ScannerActivity  extends Fragment {
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

        root = inflater.inflate(R.layout.fragment_scanner, container, false);
        activity = getActivity();

        setUpToolbar();

        setUpScanner();

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Novo produto detetado
                        Log.e(" New product scanned: ",result.getText());
                        getProduto(result.getText());
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Quando se clica após o scan queremos dar reset a isto tudo
                imageView.setVisibility(View.INVISIBLE);
                buttonHandler.invisible();
                mCodeScanner.startPreview();
            }
        });
        return root;
    }

    private void setUpScanner() {
        //Definir a imagem os butoes e a base de dados em que vamos mexer mais à frente
        this.favorite = root.findViewById(R.id.favorite);
        this.imageView = root.findViewById(R.id.imageView);
        this.dataBaseHandler = DataBaseHandler.getInstance();
        this.button = root.findViewById(R.id.button);
        scannerView = root.findViewById(R.id.scanner_view);
        this.buttonHandler = new ButtonHandler(button, favorite, dataBaseHandler);
        buttonHandler.invisible();
        imageView.setVisibility(View.INVISIBLE);
        mCodeScanner = new CodeScanner(activity, scannerView);
    }

    /**
     * @requires R.id.toolbar exista

    private void setUpToolbar() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        //Definir o titulo do fragmento, neste caso é uma string vazia!
        toolbar.setTitle(TITULO);
    }


    private void getProduto(String produto) {
        //Se o produto existir o nome dele vai existor
        String nome = dataBaseHandler.getNomeProduto(produto);
        if(nome != null){ //Caso exista ele tem uma imagem corresponde na base de dados
            StorageReference storageReference = dataBaseHandler.getStorageReference();
            try {
                File localfile = File.createTempFile(nome, "jpg"); //Vai buscar a imagem desse produto
                storageReference.child("images/" + nome + ".jpg").getFile(localfile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                //Vai ser sempre bem sucedido se entrar no if
                                int pegadaEcologica = dataBaseHandler.getPegadaEcologica(produto);
                                Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath()); //Passa a imagem para bitmap
                                imageView.setImageBitmap(bitmap);
                                imageView.setVisibility(View.VISIBLE);
                                imageView.setBackgroundColor(Color.TRANSPARENT);
                                buttonHandler.newProduct(nome, produto, pegadaEcologica);
                                favorite.setVisibility(View.VISIBLE);
                                if(dataBaseHandler.isFavorite(nome)){ //Caso o produto seja um favorito a estrela que aparece é cheia
                                    favorite.setImageResource(R.drawable.favorite);
                                } else { //Vazia caso contrário
                                    favorite.setImageResource(R.drawable.pre_favorite);
                                }
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else { //Caso o produto não exista
            Button button = buttonHandler.getButton();
            button.setBackgroundColor(Color.WHITE);
            button.setText("Registe um novo produto");
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Código para mudar para a atividade do novo produto
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
*/