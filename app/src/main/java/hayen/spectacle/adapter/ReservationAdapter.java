package hayen.spectacle.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import hayen.spectacle.R;
import hayen.spectacle.data.dao.DatabaseHelper;
import hayen.spectacle.data.dao.DatabaseQueries;
import hayen.spectacle.data.data.Reservation;
import hayen.spectacle.data.data.Salle;
import hayen.spectacle.data.data.Section;
import hayen.spectacle.data.data.Siege;
import hayen.spectacle.data.data.Spectacle;
import hayen.spectacle.util.Constant;

public class ReservationAdapter extends ArrayAdapter<Reservation> {

    public ReservationAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        // 1. initialiser des trucs
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ticket_layout, parent, false);
        }
        Reservation reservation = getItem(position);

        // 2. aller chercher les views pertinents
        TextView    titreTV = convertView.findViewById(R.id.titreTextView),
                    dateTV = convertView.findViewById(R.id.dateTextView),
                    salleTV = convertView.findViewById(R.id.salleTextView),
                    siegeTV = convertView.findViewById(R.id.siegeTextView),
                    numTV = convertView.findViewById(R.id.numeroTextView);

        // 3. aller chercher les informations pertinentes
        String titre = "PLACE HOLDER",
                date = "aujourd'hui",
                salle = "1",
                rangee = "J",
                colonne = "3",
                num,
                section = "Terasse";

        num = reservation.getNumeroConfirmation();

        // WE HAVE A DATABASE AND I AM GOING TO USE IT!!!
        if (Constant.fightLaDB){
            DatabaseHelper dbh = DatabaseHelper.getInstance(getContext());
            SQLiteDatabase db = dbh.getReadableDatabase();
            int id_siege, id_spectacle;
            Cursor cursor;
            if ((cursor = db.rawQuery("select * from reservation_spectacle_siege as rss where rss.id_reservation = ?", new String[]{Integer.toString(reservation.getId())})) != null && cursor.moveToFirst()){
                id_siege = cursor.getInt(cursor.getColumnIndex("id_siege"));
                id_spectacle = cursor.getInt(cursor.getColumnIndex("id_spectacle"));
                Siege siege = dbh.getSiegeById(id_siege);
                Spectacle spectacle = dbh.getSpectacleById(id_spectacle);
                Section section1 = dbh.getSectionById(siege.getSectionId());
                Salle salle1 = dbh.getSalleById(section1.getId());
                titre = spectacle.getTitre();
                date = spectacle.getDate();
                salle = salle1.getNom();
                rangee = siege.getRangee();
                colonne = Integer.toString(siege.getColonne());
                section = section1.getName();
            }
            /*
//            Cursor cursor = db.rawQuery(DatabaseQueries.E, new String[]{Integer.toString(reservation.getId())});
            if (cursor!= null && cursor.moveToFirst()) { // seul la premiere ligne nous interesse. De toute facon, il ne devrait pas y en avoir d'autre
                titre = cursor.getString(cursor.getColumnIndex(DatabaseQueries.RESERVATION_QUERY_COLONNE_TITRE));
                date = cursor.getString(cursor.getColumnIndex(DatabaseQueries.RESERVATION_QUERY_COLONNE_DATE));
                salle = cursor.getString(cursor.getColumnIndex(DatabaseQueries.RESERVATION_QUERY_COLONNE_SALLE));
                section = cursor.getString(cursor.getColumnIndex(DatabaseQueries.RESERVATION_QUERY_COLONNE_SECTION));
                rangee = cursor.getString(cursor.getColumnIndex(DatabaseQueries.RESERVATION_QUERY_COLONNE_RANGEE));
                // colonne est un tinyint
                colonne = Integer.toString(cursor.getInt(cursor.getColumnIndex(DatabaseQueries.RESERVATION_QUERY_COLONNE_COLONNE)));
            }
            */
            db.close();
        }

        // 4. formatter et afficher les infos
        titreTV.setText(titre);
        dateTV.setText(String.format("%s: %s", convertView.getResources().getString(R.string.str_date), date));
        salleTV.setText(String.format("%s %s\t%s %s", convertView.getResources().getString(R.string.str_salle), salle, convertView.getResources().getString(R.string.str_section), section));
        siegeTV.setText(String.format("%s %s%s", convertView.getResources().getString(R.string.str_siege), rangee, colonne));
        numTV.setText(String.format("%s: %s", convertView.getResources().getString(R.string.str_numero), num));

        return convertView;
    }
}
