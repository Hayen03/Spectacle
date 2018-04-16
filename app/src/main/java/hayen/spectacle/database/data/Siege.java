//package com.ift2905.reservation.database.entities;
package hayen.spectacle.database.data;



public class Siege {


    public static final String TABLE_NAME = "siege";
    public static final String TABLE_NAME_REF_SECTION = "section";


    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RANGEE = "rangee";

    public static final String COLUMN_COLUMN = "colonne";
    public static final String COLUMN_RESERVE = "reserve";
    public static final String COLUMN_SECTION_ID = "id_section";


    public static final String CREATE_TABLE_SIEGE=
            "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
                    " ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_RANGEE + " VARCHAR(3) NOT NULL, " +
                    COLUMN_COLUMN + " INTEGER NOT NULL, " +
                    COLUMN_RESERVE + " TINYINT DEFAULT 0, " +
                    COLUMN_SECTION_ID + " INTEGER, FOREIGN KEY ("+COLUMN_SECTION_ID+") REFERENCES "+TABLE_NAME_REF_SECTION+"("+COLUMN_ID+"))";



    private int id;
    private String rangee;
    private int colonne;
    private int sectionId;

    public Siege(){}

    public Siege(String rangee, int colonne, int sectionId){
        this.id = 0;
        this.rangee = rangee;
        this.colonne = colonne;
        this.sectionId = sectionId;
    }


    public Siege(int id, String rangee, int colonne, int sectionId){
        this.id = id;
        this.rangee = rangee;
        this.colonne = colonne;
        this.sectionId = sectionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRangee() {
        return rangee;
    }

    public void setRangee(String rangee) {
        this.rangee = rangee;
    }

    public int getColonne() {
        return colonne;
    }

    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String toString(){
        String str = "id: " + getId() + "; rangee: " + getRangee() + "; colonne: " + getColonne() +
                "; sectionId: " + getSectionId();

        return str;

    }
}
