//package com.ift2905.reservation.database.entities;
package hayen.spectacle.data.data;


import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class Spectacle {

    public static final Spectacle bidonA = new Spectacle(-1, "Bidon A", "Aujourd'hui", 3, -1, -1)
            .addArtiste(Artiste.bidonA);
    public static final Spectacle bidonB = new Spectacle(-1, "Bidon B", "Aujourd'hui", 3, -1, -1)
            .addArtiste(Artiste.bidonB);
    public static final Spectacle bidonC = new Spectacle(-1, "Bidon C", "Aujourd'hui", 3, -1, -1)
            .addArtiste(Artiste.bidonC);
    public static final Spectacle[] bidons = {bidonA, bidonB, bidonC};

    public static final String TABLE_NAME= "spectacle";
    public static final String TABLE_NAME_REF_SALLE = "salle";
    public static final String TABLE_NAME_REF_GENRE = "genre";


    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITRE = "titre";
    public static final String COLUMN_DATE_SPECTACLE = "date_spectacle";
    public static final String COLUMN_GENRE_ID = "id_genre";
    public static final String COLUMN_DUREE = "duree";
    public static final String COLUMN_SALLE_ID = "id_salle";



    public static final String CREATE_TABLE_SPECTACLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITRE + " VARCHAR(40), " +
                    COLUMN_DATE_SPECTACLE + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                    COLUMN_GENRE_ID + " INTEGER, " +
                    " FOREIGN KEY ("+COLUMN_GENRE_ID+") REFERENCES "+TABLE_NAME_REF_GENRE+"("+COLUMN_ID+"))";



    private int id;
    private String titre;
    private String date;
    private int duree;
    private int genreId;
    private Genre genre;
    private List<Artiste> artistes;
    private int salleId;

    public Spectacle(){}

    public Spectacle(String titre, String date, int duree, int genreId, int salleId){
        this.id = 0;
        this.titre = titre;
        this.date = date;
        this.duree = duree;
        this.genreId = genreId;
        this.salleId =  salleId;
    }

    public Spectacle(int id, String titre, String date, int duree, int genreId, int salleId){
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.duree = duree;
        this.genreId = genreId;
        this.salleId =  salleId;
    }

    public Spectacle(String titre, String date, int duree, Genre genre, int salleId, List<Artiste> artistes){
        this.id = 0;
        this.titre = titre;
        this.date = date;
        this.duree = duree;
        this.genre = genre;
        this.artistes =  artistes;
        this.salleId =  salleId;
    }



    public int getId() {
        return id;
    }

    public Spectacle setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitre() {
        return titre;
    }

    public Spectacle setTitre(String titre) {
        this.titre = titre;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Spectacle setDate(String date) {
        this.date = date;
        return this;
    }

    public int getDuree() {
        return duree;
    }

    public Spectacle setDuree(int duree) {
        this.duree = duree;
        return this;
    }

    public int getGenreId() {
        return genreId;
    }

    public Spectacle setGenreId(int genreId) {
        this.genreId = genreId;
        return this;
    }

    public int getSalleId() {
        return salleId;
    }

    public Spectacle setSalleId(int salleId) {
        this.salleId = salleId;
        return this;
    }

    public Genre getGenre() {
        return genre;
    }

    public Spectacle setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public List<Artiste> getArtistes() {
        return artistes;
    }

    public Spectacle setArtistes(List<Artiste> artistes) {
        this.artistes = artistes;
        return this;
    }

    public Spectacle addArtiste(Artiste artiste){
        if(artistes == null){
            artistes =  new ArrayList<>();
        }
        artistes.add(artiste);
        return this;
    }

    public String toString(){
        String str = "id: " + getId();
        str += " titre: " + getTitre() ;
        str += " date: " + getDate();
        str += " duree: " + getDuree() + " min";
        str += " genreId: " + getGenreId();
        str += " salleId: " + getSalleId();
        return str;

    }
}
