package com.mbds.livreenfolie.model;

public class Chapitre {
    private int id;
    private int numeroChapitre;
    private String contenu;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroChapitre() {
        return numeroChapitre;
    }

    public void setNumeroChapitre(int numeroChapitre) {
        this.numeroChapitre = numeroChapitre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
}
