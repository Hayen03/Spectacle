//package com.ift2905.reservation.database.dao;
package hayen.spectacle.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import com.ift2905.reservation.database.entities.Salle;
//import com.ift2905.reservation.database.entities.Section;

import hayen.spectacle.database.entities.Salle;
import hayen.spectacle.database.entities.Section;


import java.util.ArrayList;
import java.util.List;


public class SectionSQLHelper extends SQLiteOpenHelper {

  //  private static final String TABLE_NAME = "section";

//    public static final String CREATE_TABLE_SECTION =
//            "CREATE TABLE IF NOT EXISTS " + Section.TABLE_NAME +
//                    " (" + Section.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    Section.COLUMN_NAME + " VARCHAR(30), " +
//                    Section.COLUMN_CATEGORIE + " INTEGER, " +
//                    Section.COLUMN_NB_SIEGES + " INTEGER DEFAULT 0, " +
//                    Section.COLUMN_SALLE_ID + " INTEGER, " +
//                    " FOREIGN KEY (" + Section.COLUMN_SALLE_ID + ") REFERENCES " + Salle.TABLE_NAME + "(" + Salle.COLUMN_ID + "))";


    public SectionSQLHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

    }

    public SectionSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    //**********************************************************************************************
    //**********************************************************************************************

    public Section getSectionById(int id) {


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Section.TABLE_NAME,
                new String[]{   Section.COLUMN_ID, Section.COLUMN_NAME, Section.COLUMN_CATEGORIE,
                                Section.COLUMN_NB_SIEGES, Section.COLUMN_SALLE_ID },
                        Section.COLUMN_ID + "=?",
                        new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            Section section = new Section(
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Section.COLUMN_NAME)),
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_CATEGORIE)),
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_NB_SIEGES)),
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_SALLE_ID)));

            cursor.close();

            return section;
        }
        return null;
    }


    //**********************************************************************************************
    //**********************************************************************************************

    public Section getSectionBySalleId(int idSalle) {


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Section.TABLE_NAME,
                new String[]{   Section.COLUMN_ID, Section.COLUMN_NAME, Section.COLUMN_CATEGORIE,
                        Section.COLUMN_NB_SIEGES, Section.COLUMN_SALLE_ID },
                Section.COLUMN_SALLE_ID + "=?",
                new String[]{String.valueOf(idSalle)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            Section section = new Section(
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Section.COLUMN_NAME)),
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_CATEGORIE)),
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_NB_SIEGES)),
                    cursor.getInt(cursor.getColumnIndex(Section.COLUMN_SALLE_ID)));

            cursor.close();

            return section;
        }
        return null;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public List<Section> getAllSections() {
        List<Section> sections = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Section.TABLE_NAME + " ORDER BY " +
                Section.COLUMN_ID + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Section section = new Section();
                section.setId(cursor.getInt(cursor.getColumnIndex(Section.COLUMN_ID)));
                section.setName(cursor.getString(cursor.getColumnIndex(Section.COLUMN_NAME)));
                section.setCategorie(cursor.getInt(cursor.getColumnIndex(Section.COLUMN_CATEGORIE)));
                section.setNbSieges(cursor.getInt(cursor.getColumnIndex(Section.COLUMN_NB_SIEGES)));
                section.setSalleId(cursor.getInt(cursor.getColumnIndex(Section.COLUMN_SALLE_ID)));

                sections.add(section);
            } while (cursor.moveToNext());
        }


        db.close();

        return sections;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int getSectionsCount() {
        String countQuery = "SELECT  * FROM " + Section.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        return count;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public long addSection(Section section) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Section.COLUMN_NAME, section.getName());
        values.put(Section.COLUMN_CATEGORIE, section.getCategorie());
        values.put(Section.COLUMN_NB_SIEGES, section.getNbSieges());
        values.put(Section.COLUMN_SALLE_ID, section.getSalleId());

        long nbAffectedRows= db.insert (Section.TABLE_NAME, null, values);

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************


    public int updateSection(Section section) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Section.COLUMN_NAME, section.getName());
        values.put(Section.COLUMN_CATEGORIE, section.getCategorie());
        values.put(Section.COLUMN_NB_SIEGES, section.getNbSieges());
        values.put(Section.COLUMN_SALLE_ID, section.getSalleId());

        int nbAffectedRows= db.update (Section.TABLE_NAME, values, Section.COLUMN_ID + " = ?",
                new String[]{String.valueOf(section.getId())});

        return nbAffectedRows;
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public int deleteSection(Section section) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(Section.TABLE_NAME, Section.COLUMN_ID + " = ?",
                new String[]{String.valueOf(section.getId())});
        db.close();

        return result;
    }
    //**********************************************************************************************
    //**********************************************************************************************

    @Override
    public synchronized void close() {
        super.close();
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    //**********************************************************************************************
    //**********************************************************************************************


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    //**********************************************************************************************
    //**********************************************************************************************


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
