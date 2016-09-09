package ar.com.gitmo.androidpruebas;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;

import ar.com.gitmo.androidpruebas.adapters.AgendaAdapter;
import ar.com.gitmo.androidpruebas.adapters.TestDrawerAdapter;
import ar.com.gitmo.androidpruebas.models.Actividad;
import ar.com.gitmo.androidpruebas.models.Semana;

public class TestDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , SearchView.OnQueryTextListener {

    private final String TAG="AndroidPruebas";
    private final String TAG_ACTIVITY_NAME="TestDrawerActivity";

    private RecyclerView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;

    private TestDrawerAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ArrayList<Semana> myDataset;
    int semanaActual=0;

    boolean filtro=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.test_drawer_open, R.string.test_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        generarDatos();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Specify an adapter (see also next example)
        mAdapter = new TestDrawerAdapter(myDataset, this);

        mAdapter.setOnItemClickListener(new TestDrawerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final Actividad actividad) {
                //Toast.makeText(AgendaActivity.this, actividad.getNombre() + " was clicked " + actividad.getDescripcion(), Toast.LENGTH_SHORT).show();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestDrawerActivity.this);

                SimpleDateFormat sdf= new SimpleDateFormat("MMM-dd", new Locale("es_ES"));

                alertDialogBuilder.setMessage( sdf.format(actividad.getFechaDesde()) + " " + sdf.format(actividad.getFechaHasta())+ "\n" +actividad.getNombre() + ": " + actividad.getDescripcion());

                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(TestDrawerActivity.this,"You clicked Ok button",Toast.LENGTH_LONG).show();
                    }
                });

                alertDialogBuilder.setNegativeButton("Agendar",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(TestDrawerActivity.this,"Adding event.",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Intent.ACTION_INSERT)
                                .setData(CalendarContract.Events.CONTENT_URI)
                                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, actividad.getFechaDesde().getTime())
                                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, actividad.getFechaHasta().getTime())
                                .putExtra(CalendarContract.Events.TITLE, actividad.getNombre())
                                .putExtra(CalendarContract.Events.DESCRIPTION, actividad.getDescripcion())
                                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Fiuba")
                                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
                                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                        startActivity(intent);
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        mLayoutManager.scrollToPosition(semanaActual);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mLayoutManager.scrollToPosition(semanaActual);

                if(!filtro){
                    Log.i(TAG, TAG_ACTIVITY_NAME + " ITEMS: "+ mAdapter.getItemCount());
                    mLayoutManager.scrollToPositionWithOffset(semanaActual, 0);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_drawer, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        // Here is where we are going to implement the filter logic

        Log.i(TAG, TAG_ACTIVITY_NAME + " QUERY: " + query);

        if (query.length()==0){
            filtro=false;

            mAdapter.setFilter(myDataset);
        }else{
            filtro=true;

            ArrayList<Semana> myNewDataset=new ArrayList<Semana>();

            Iterator<Semana> itSemanas=myDataset.iterator();
            while(itSemanas.hasNext()){
                final Semana semana = itSemanas.next();

                boolean tieneActividad=false;

                Semana nSemana = new Semana();
                nSemana.setFechaDesde(semana.getFechaDesde());
                nSemana.setFechaHasta(semana.getFechaHasta());

                Iterator<Actividad> itActividades=semana.getIterator();
                while(itActividades.hasNext()) {
                    final Actividad actividad = itActividades.next();

                    if (actividad.getNombre().toLowerCase().contains(query.toLowerCase())){
                        tieneActividad=true;

                        nSemana.addActividad(actividad);
                    }
                }

                if (tieneActividad){
                    myNewDataset.add(nSemana);
                }
            }

            mAdapter.setFilter(myNewDataset);

        }

        mAdapter.notifyDataSetChanged();

        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {


        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void generarDatos(){
        myDataset = new ArrayList<Semana>();

        GregorianCalendar gc=new GregorianCalendar();

        final Calendar calendar = Calendar.getInstance();

        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        semanaActual = (int)Math.ceil((double) dayOfYear/7);

        int dias;
        if (gc.isLeapYear(2016)){
            dias = 366;
        }else{
            dias = 365;
        }

        for (int juliana=1; juliana < dias; juliana=juliana+7){

            calendar.set(Calendar.DAY_OF_YEAR, juliana);
            Date dateDesde = new Date(calendar.getTimeInMillis());

            calendar.set(Calendar.DAY_OF_YEAR, juliana+6);
            Date dateHasta = new Date(calendar.getTimeInMillis());

            Semana semana= new Semana();

            semana.setFechaDesde(dateDesde);

            semana.setFechaHasta(dateHasta);

            int random = (int) (Math.random() * 10);

            for(int a=1; a <= random; a++){

                Actividad actividad= new Actividad();
                actividad.setFechaDesde(dateDesde);
                actividad.setFechaHasta(dateHasta);
                actividad.setNombre("Actividad " + juliana + a );
                actividad.setDescripcion("Descripcion... " + juliana + a);

                semana.addActividad(actividad);
            }

            myDataset.add(semana);
        }


    }
}
