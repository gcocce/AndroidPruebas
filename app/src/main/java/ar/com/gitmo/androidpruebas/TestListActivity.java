package ar.com.gitmo.androidpruebas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import ar.com.gitmo.androidpruebas.adapters.AgendaAdapter;
import ar.com.gitmo.androidpruebas.adapters.TestListAdapter;
import ar.com.gitmo.androidpruebas.decorations.TestListDivider;
import ar.com.gitmo.androidpruebas.models.Semana;
import ar.com.gitmo.androidpruebas.models.Test;

public class TestListActivity extends AppCompatActivity {

    private static String LOG_TAG = "RecyclerViewActivity";

    private RecyclerView mRecyclerView;
    private TestListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final int VERTICAL_ITEM_SPACE = 48;

    ArrayList<Test> myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        Log.i(LOG_TAG, "TestListActivity started");

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

    private ArrayList<Test> getDataSet() {
        myDataset = new ArrayList<Test>();

        Test test = new Test("Agenda", "RecyclerView con items que continenen lista de items", "ar.com.gitmo.androidpruebas.AgendaActivity");
        myDataset.add(test);
        test = new Test("MenuGrid", "Menu de Imagenes con Textos", "ar.com.gitmo.androidpruebas.MenuActivity");
        myDataset.add(test);
        test = new Test("Bienvenido", "PageViewer con Fragmentos", "ar.com.gitmo.androidpruebas.BienvenidoActivity");
        myDataset.add(test);
        test = new Test("NotDefined", "", "");
        myDataset.add(test);

        for (int x=0; x < 20; x++){
            test = new Test("MenuGrid", "Menú de Imágenes con Textos", "ar.com.gitmo.androidpruebas.MenuActivity");
            myDataset.add(test);
        }

        return myDataset;
    }
}


