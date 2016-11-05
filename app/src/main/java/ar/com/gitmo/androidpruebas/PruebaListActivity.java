package ar.com.gitmo.androidpruebas;

import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import ar.com.gitmo.androidpruebas.adapters.TestListAdapter;
import ar.com.gitmo.androidpruebas.decorations.TestListDivider;
import ar.com.gitmo.androidpruebas.models.Prueba;


public class PruebaListActivity extends AppCompatActivity {

    private static String LOG_TAG = "RecyclerViewActivity";
    private RecyclerView mRecyclerView;
    private TestListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private static final int VERTICAL_ITEM_SPACE = 48;

    ArrayList<Prueba> myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        Log.i(LOG_TAG, "PruebaListActivity started");

        AppBarLayout app_bar_layout=(AppBarLayout)findViewById(R.id.app_bar);
        app_bar_layout.setExpanded(false);

        ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar)).setTitle("Android Tests");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_test_list_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        // Add ItemDecoration
        // mUiRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        // mUiRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        mRecyclerView.addItemDecoration(
                new TestListDivider(this, R.drawable.test_list_divider));

        mAdapter = new TestListAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item

                mLayoutManager.scrollToPositionWithOffset(0,0);

                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.setOnItemClickListener(
                new TestListAdapter.OnItemClickListener() {
                  @Override
                  public void onItemClick(int position, View v) {
                      Log.i(LOG_TAG, " Clicked on Item " + position);

                      startActivity(new Intent(myDataset.get(position).getActivity()));
                  }
              });
    }

    private ArrayList<Prueba> getDataSet() {
        myDataset = new ArrayList<Prueba>();

        Prueba test = new Prueba("Agenda", "RecyclerView con items que continenen lista de items", "ar.com.gitmo.androidpruebas.AgendaActivity");
        myDataset.add(test);
        test = new Prueba("MenuGrid", "Menu de Imagenes con Textos", "ar.com.gitmo.androidpruebas.MenuActivity");
        myDataset.add(test);
        test = new Prueba("Bienvenido", "PageViewer con Fragmentos", "ar.com.gitmo.androidpruebas.BienvenidoActivity");
        myDataset.add(test);
        test = new Prueba("SimpleProvider", "Ejemplo de Content Provider con SQLite", "ar.com.gitmo.androidpruebas.PruebaContentProvider");
        myDataset.add(test);
        test = new Prueba("DBActivity", "Ejemplo de Content Provider con SQLite", "ar.com.gitmo.androidpruebas.PruebaDBActivity");
        myDataset.add(test);

        for (int x=0; x < 20; x++){
            test = new Prueba("MenuGrid", "Menú de Imágenes con Textos", "ar.com.gitmo.androidpruebas.MenuActivity");
            myDataset.add(test);
        }

        return myDataset;
    }
}


