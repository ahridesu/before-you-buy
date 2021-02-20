package com.example.beforeyoubuy.models;

import android.media.Image;
import android.provider.MediaStore;

import java.util.Objects;

public class Produto {
    private int pegadaEcologica; //product's ecological footprint
    private String name; //product's name
    private String id; //product's barcode code
    private int image;

    public Produto(String id, String name , int pegadaEcologica, int image){
        this.id = id;
        this.name = name;
        this.pegadaEcologica = pegadaEcologica;
        this.image = image;
    }

    /**
     * returns product image
     * @return product image
     */
    public int getImage() {
        return image;
    }


    /**
     * changes product image
     * @param image - new product image
     */
    public void setImage(int image) {
        this.image = image;
    }


    /**
     * returns product name
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * changes product current name with @name
     * @param name - new product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns the product id - barcode code
     * @return product's id
     */
    public String getId() {
        return id;
    }

    /**
     * set new id for product
     *
     * TODO check barcode types and if this method is needed. Refer-to: https://barcode-labels.com/getting-started/barcodes/types/
     *
     * @param id product's barcode code
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * returns the product's ID, name and ecological footprint, respectively in a string
     * @return String product ID, name, ecological footprint
     */
    @Override
    public String toString(){
        return "ID: " + id + "\nNOME: " + name + "\nPegada Ecologica: " + pegadaEcologica;
    }

    /**
     * returns the product's ecological footprint
     * @return ecological footprint
     */
    public int getPegadaEcologica() {
        return pegadaEcologica;
    }

    /**
     * Checks if Object @o has the same attributes as this.Produto
     * @param o Object
     * @return true if the object has the same attributes as this.Produto, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return pegadaEcologica == produto.pegadaEcologica &&
                Objects.equals(name, produto.name) &&
                Objects.equals(id, produto.id);
    }
}
