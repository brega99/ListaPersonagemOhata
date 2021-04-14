package com.example.listaohata.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Personagem implements Serializable {
    // variaveis de que serao usadas para receber dados do personagem
    private String nome;
    private String nacimento;
    private String altura;
    private int id = 0;


    public Personagem(String nome, String nascimento, String altura) {
        this.nome = nome;
        this.nacimento = nascimento;
        this.altura = altura;

    }

    public Personagem() {
    }


    @NonNull
    @Override
    public String toString()// para poder exibir nome salvo do personagem la lissta
    {
        return nome;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNacimento() {
        return nacimento;
    }

    public void setNacimento(String nacimento) {
        this.nacimento = nacimento;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }


    public void setId(int id)// setando id dos personagens salvos
    {
        this.id = id;
    }

    public int getId()// pegando o id dos pesonagens salvos
    {
        return id;
    }

    public boolean IdValido() {
        return id > 0;
    }

    ;
}
