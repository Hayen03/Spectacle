package hayen.spectacle.data.data;

/**
 * Created by daristote on 18-04-16.
 */

public class SpectacleSection {

    public static final String TABLE_NAME = "spectacle_section";

    public static final String COLUMN_SPECTACLE_ID = "id_spectacle";
    public static final String COLUMN_SECTION_ID = "id_section";
    public static final String COLUMN_PRICE = "prix";

    private int spectacleId;
    private int sectionid;
    private float price;

    public SpectacleSection(){}
    public SpectacleSection(int spectacleId, int sectionId, float price){

        this.spectacleId = spectacleId;
        this.sectionid = sectionId;
        this.price =  price;

    }


    public int getSpectacleId() {
        return spectacleId;
    }

    public void setSpectacleId(int spectacleId) {
        this.spectacleId = spectacleId;
    }

    public int getSectionid() {
        return sectionid;
    }

    public void setSectionid(int sectionid) {
        this.sectionid = sectionid;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String toString(){

        String str = "";

        str = "Spectacle id: " + this.getSpectacleId();
        str += " Section id: " + this.getSectionid();
        str += " Price: " + this.getPrice() +  "\n";
        return str;

    }
}
