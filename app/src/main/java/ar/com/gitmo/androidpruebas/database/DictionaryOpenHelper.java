package ar.com.gitmo.androidpruebas.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gitmo on 27/08/2016.
 */
public class DictionaryOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "prueba.db";
    private static final String DICTIONARY_TABLE_NAME = "dictionary";
    private static final String DICTIONARY_TABLE_CREATE =
            "CREATE TABLE " + DICTIONARY_TABLE_NAME + " (" +
                    DictionaryModel.DictionaryEntry._ID + " INTEGER PRIMARY KEY, " +
                    DictionaryModel.DictionaryEntry.COLUMN_WORD + " TEXT, " +
                    DictionaryModel.DictionaryEntry.COLUMN_POPULARITY + " INTEGER );";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DictionaryModel.DictionaryEntry.TABLE_NAME;

    DictionaryOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DICTIONARY_TABLE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}