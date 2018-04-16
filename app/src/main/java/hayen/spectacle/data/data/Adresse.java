//package com.ift2905.reservation.database.entities;
package hayen.spectacle.data.data;



public class Adresse {
    public static final Adresse bidon = new Adresse(-1, 1234, "rue Bidon", "Bidonville", "QC", "A1B 2C3", 0, 0);

    public static final String TABLE_NAME = "adresse";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NUMERO = "numero";
    public static final String COLUMN_RUE = "rue";
    public static final String COLUMN_VILLE = "ville";
    public static final String COLUMN_PROVINCE = "province";
    public static final String COLUMN_CODE_POSTAL = "code_postal";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_LATITUDE = "latitude";


    private int id;
    private int numero;
    private String rue;
    private String ville;
    private String province;
    private String codePostal;
    private long longitude;
    private long latitude;

    public Adresse(){}

    public Adresse(int id, int numero, String rue, String ville, String province, String codePostal, long longitude, long latitude){
        this.id = id;
        this.numero = numero;
        this.rue =  rue;
        this.ville =  ville;
        this.province = province;
        this.codePostal = codePostal;
        this.longitude = longitude;
        this.latitude =  latitude;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public String toString(){

        String str =  "Numéro: " + getNumero() + "; rue: " + getRue() + "; ville: " + getVille();
        str += "; Province: " + getProvince() + "; code postal: " + getCodePostal();

        return str;
    }

}
