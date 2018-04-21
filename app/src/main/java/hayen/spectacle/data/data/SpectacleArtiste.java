package hayen.spectacle.data.data;

/**
 * Created by daristote on 18-04-15.
 */

public class SpectacleArtiste {


    public static final String TABLE_NAME = "spectacle_artiste";

    public static final String COLUMN_SPECTACLE_ID = "id_spectacle";
    public static final String COLUMN_ARTISTE_ID = "id_artiste";


    private int spectacleId;
    private int artisteId;


    public SpectacleArtiste(){}
    public SpectacleArtiste(int spectacleId, int artisteId){

        this.spectacleId = spectacleId;
        this.artisteId = artisteId;
    }

    public int getSpectacleId() {
        return spectacleId;
    }

    public void setSpectacleId(int spectacleId) {
        this.spectacleId = spectacleId;
    }

    public int getArtisteId() {
        return artisteId;
    }

    public void setArtisteId(int artisteId) {
        this.artisteId = artisteId;
    }
}
