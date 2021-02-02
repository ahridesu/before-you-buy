package com.example.beforeyoubuy;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Produto {
    private int pegadaEcologica;
    private String name;
    private String id;
    private int imagem;

    public Produto(String name, String id, int pegadaEcologica, int imagem){
        this.name = name;
        this.id = id;
        this.pegadaEcologica = pegadaEcologica;
        this.imagem = imagem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return "ID: " + id + "\nNOME: " + name + "\nPegada Ecologica: " + pegadaEcologica;
    }

    public int getPegadaEcologica() {
        return pegadaEcologica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return pegadaEcologica == produto.pegadaEcologica &&
                imagem == produto.imagem &&
                Objects.equals(name, produto.name) &&
                Objects.equals(id, produto.id);
    }

    public int getImagem() {
        return imagem;
    }
}