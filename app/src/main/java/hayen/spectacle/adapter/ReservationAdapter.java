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
        String titre = "PLACE HOLDER", date = "aujourd'hui", salle = "1", rangee = "J", colonne = "3", num, section = "Terasse";
        num = reservation.getNumeroConfirmation();
        // WE HAVE A DATABASE AND I AM GOING TO USE IT!!!
        if (Constant.fightLaDB){
            DatabaseHelper dbh = DatabaseHelper.getInstance(getContext());
            SQLiteDatabase db = dbh.getReadableDatabase();
            Cursor cursor = db.rawQuery(DatabaseQueries.RESERVATION_QUERY, new String[]{Integer.toString(reservation.getId())});
            cursor.moveToFirst(); // seul la premiere ligne nous interesse. De toute facon, il ne devrait pas y en avoir d'autre
            titre = cursor.getString(cursor.getColumnIndex(DatabaseQueries.RESERVATION_QUERY_COLONNE_TITRE));
            date = cursor.getString(cursor.getColumnIndex(DatabaseQueries.RESERVATION_QUERY_COLONNE_DATE));
            salle = cursor.getString(cursor.getColumnIndex(DatabaseQueries.RESERVATION_QUERY_COLONNE_SALLE));
            section = cursor.getString(cursor.getColumnIndex(DatabaseQueries.RESERVATION_QUERY_COLONNE_SECTION));
            rangee = cursor.getString(cursor.getColumnIndex(DatabaseQueries.RESERVATION_QUERY_COLONNE_RANGEE));
            // colonne est un tinyint
            colonne = Integer.toString(cursor.getInt(cursor.getColumnIndex(DatabaseQueries.RESERVATION_QUERY_COLONNE_COLONNE)));
            db.close();
        }

        // 4. formatter et afficher les infos
        titreTV.setText(titre);
        dateTV.setText(String.format("Date: %s", date));
        salleTV.setText(String.format("Salle %s\tsection %s", salle, section));
        siegeTV.setText(String.format("Siege %s%s", rangee, colonne));
        numTV.setText(String.format("Numéro: %s", num));

        return convertView;
    }
}
