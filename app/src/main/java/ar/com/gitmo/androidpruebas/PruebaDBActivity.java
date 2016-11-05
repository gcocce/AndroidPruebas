package ar.com.gitmo.androidpruebas;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ar.com.gitmo.androidpruebas.database.DictionaryModel;
import ar.com.gitmo.androidpruebas.database.DictionaryOpenHelper;
import timber.log.Timber;

public class PruebaDBActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Cursor cursor;

    long current_word_id=0;
    String current_word = "";
    int current_word_popularity = 0;

    ListView llWords;

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DictionaryOpenHelper mDbHelper = new DictionaryOpenHelper(getApplicationContext());

        // Gets the data repository in write mode
        db = mDbHelper.getWritableDatabase();

        Button btAddWord= (Button) findViewById(R.id.btAddWord);
        btAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText etAddWord= (EditText) findViewById(R.id.etAddWord);

                String word = etAddWord.getText().toString();

                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(DictionaryModel.DictionaryEntry.COLUMN_WORD, word);
                values.put(DictionaryModel.DictionaryEntry.COLUMN_POPULARITY, 0);

                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(DictionaryModel.DictionaryEntry.TABLE_NAME, null, values);

                Timber.i("Word Id just inserted is: " + newRowId);

                etAddWord.setText("");
            }
        });



        Button btUgly= (Button) findViewById(R.id.btUgly);
        btUgly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateData(-1);
            }
        });

        Button btAcceptable= (Button) findViewById(R.id.btAcceptable);
        btAcceptable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //updateData(0);
                getNextWord();
            }
        });

        Button btNice= (Button) findViewById(R.id.btNice);
        btNice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateData(1);
            }
        });

        queryDB();

        long size=getRowsInDB();
        Timber.i("DB size: " + size);

        llWords = getListView();

        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        setListAdapter(adapter);

        showWords();
    }

    protected ListView getListView() {
        if (llWords == null) {
            llWords = (ListView) findViewById(R.id.llWords);
        }
        return llWords;
    }

    protected void setListAdapter(ListAdapter adapter) {
        getListView().setAdapter(adapter);
    }

    protected ListAdapter getListAdapter() {
        ListAdapter adapter = getListView().getAdapter();
        if (adapter instanceof HeaderViewListAdapter) {
            return ((HeaderViewListAdapter)adapter).getWrappedAdapter();
        } else {
            return adapter;
        }
    }

    private void queryDB(){
        // Reed first word

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DictionaryModel.DictionaryEntry._ID,
                DictionaryModel.DictionaryEntry.COLUMN_WORD,
                DictionaryModel.DictionaryEntry.COLUMN_POPULARITY
        };

        // Filter results WHERE "title" = 'My Title'
        //String selection = DictionaryModel.DictionaryEntry + " = ?";
        //String[] selectionArgs = { "My Title" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =  DictionaryModel.DictionaryEntry._ID + " ASC";

        cursor = db.query(
                DictionaryModel.DictionaryEntry.TABLE_NAME,                     // The table to query
                null,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if(cursor.moveToFirst()){
            getWord();
        }
    }

    private void getWord(){
        current_word_id = cursor.getLong(cursor.getColumnIndexOrThrow(DictionaryModel.DictionaryEntry._ID));
        current_word = cursor.getString(cursor.getColumnIndexOrThrow(DictionaryModel.DictionaryEntry.COLUMN_WORD));
        current_word_popularity = cursor.getInt(cursor.getColumnIndexOrThrow(DictionaryModel.DictionaryEntry.COLUMN_POPULARITY));

        TextView textView= (TextView ) findViewById(R.id.tvShowWord);
        textView.setText(current_word);
    }

    private void updateData(int popu){
        current_word_popularity = current_word_popularity +popu;

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(DictionaryModel.DictionaryEntry.COLUMN_POPULARITY, current_word_popularity);

        String selection = DictionaryModel.DictionaryEntry._ID + " = ?";
        String[] selectionArgs = { Long.valueOf(current_word_id).toString() };

        int count = db.update(
                DictionaryModel.DictionaryEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        Timber.i("updateData count: "+ count);

        getNextWord();
    }

    private void getNextWord(){

        if (cursor.moveToNext()){
            getWord();
        }else{
            queryDB();
        }

        showWords();
    }

    private long getRowsInDB(){
        long numRows = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM " + DictionaryModel.DictionaryEntry.TABLE_NAME, null);
        return numRows;
    }

    private void showWords(){

        listItems.clear();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DictionaryModel.DictionaryEntry._ID,
                DictionaryModel.DictionaryEntry.COLUMN_WORD,
                DictionaryModel.DictionaryEntry.COLUMN_POPULARITY
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =  DictionaryModel.DictionaryEntry.COLUMN_POPULARITY + " DESC";

        Cursor cursor = db.query(
                DictionaryModel.DictionaryEntry.TABLE_NAME,                     // The table to query
                null,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if(cursor.moveToFirst()){
            addWordToList(cursor);
            while(cursor.moveToNext()){
                addWordToList(cursor);
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void addWordToList(Cursor cursor){

        String word = cursor.getString(cursor.getColumnIndexOrThrow(DictionaryModel.DictionaryEntry.COLUMN_WORD));
        int popularity = cursor.getInt(cursor.getColumnIndexOrThrow(DictionaryModel.DictionaryEntry.COLUMN_POPULARITY));

        Timber.i("addWordToList: " + word + " popularity: " + popularity);

        listItems.add(word);
    }

}
