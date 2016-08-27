package ar.com.gitmo.androidpruebas;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ar.com.gitmo.androidpruebas.database.DictionaryModel;
import ar.com.gitmo.androidpruebas.database.DictionaryOpenHelper;

public class DBTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DictionaryOpenHelper mDbHelper = new DictionaryOpenHelper(getApplicationContext());

        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DictionaryModel.DictionaryEntry.COLUMN_WORD, "dios");
        values.put(DictionaryModel.DictionaryEntry.COLUMN_POPULARITY, 0);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DictionaryModel.DictionaryEntry.TABLE_NAME, null, values);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
