package ar.com.gitmo.androidpruebas.database;

import android.provider.BaseColumns;

/**
 * Created by Gitmo on 27/08/2016.
 */

public final class DictionaryModel {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DictionaryModel() {}

    /* Inner class that defines the table contents */
    public static class DictionaryEntry implements BaseColumns {
        public static final String TABLE_NAME = "dictionary";
        public static final String COLUMN_WORD = "word";
        public static final String COLUMN_POPULARITY = "popularity";
    }
}