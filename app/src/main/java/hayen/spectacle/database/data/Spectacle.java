//package com.ift2905.reservation.database.entities;
package hayen.spectacle.database.entities;



public class Spectacle {

    public static final String TABLE_NAME= "spectacle";
    public static final String TABLE_NAME_REF_SALLE = "salle";
    public static final String TABLE_NAME_REF_GENRE = "genre";


    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITRE = "titre";
    public static final String COLUMN_DATE_SPECTACLE = "date_spectacle";
    public static final String COLUMN_GENRE_ID = "id_genre";
    public static final String COLUMN_SALLE_ID = "id_salle";



//    public static final String CREATE_TABLE_SPECTACLE =
//            "CREATE TABLE IF NOT EXISTS "+TABLE_NAME +
//                    " ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    COLUMN_TITRE + " VARCHAR(40), " +
//                    COLUMN_DATE_SPECTACLE + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
//                    COLUMN_GENRE_ID + " INTEGER, " +
//                    " FOREIGN KEY ("+COLUMN_GENRE_ID+") REFERENCES "+TABLE_NAME_REF_GENRE+"("+COLUMN_ID+"))";



    private int id;
    private String titre;
    private String date;
    private int genreId;
    private int salleId;

    public Spectacle(){


    }

    public Spectacle(String titre, String date, int genreId, int salleId){
        this.id = 0;
        this.titre = titre;
        this.date = date;
        this.genreId = genreId;
        this.salleId =  salleId;
    }

    public Spectacle(int id, String titre, String date, int genreId, int salleId){
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.genreId = genreId;
        this.salleId =  salleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public int getSalleId() {
        return salleId;
    }

    public void setSalleId(int salleId) {
        this.salleId = salleId;
    }

    public String toString(){
        String str = "id: " + getId();
        str += " titre: " + getTitre() ;
        str += " date: " + getDate();
        str += " genreId: " + getGenreId();
        str += " salleId: " + getSalleId();
        return str;

    }
}
