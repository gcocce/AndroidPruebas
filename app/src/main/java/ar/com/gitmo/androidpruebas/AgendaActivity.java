package ar.com.gitmo.androidpruebas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import ar.com.gitmo.androidpruebas.adapters.AgendaAdapter;
import ar.com.gitmo.androidpruebas.models.Actividad;
import ar.com.gitmo.androidpruebas.models.Semana;

public class AgendaActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;

    private AgendaAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ArrayList<Semana> myDataset;
    int semanaActual=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        generarDatos();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new AgendaAdapter(myDataset, this);

        mAdapter.setOnItemClickListener(new AgendaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Actividad actividad) {
                //Toast.makeText(AgendaActivity.this, actividad.getNombre() + " was clicked " + actividad.getDescripcion(), Toast.LENGTH_SHORT).show();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AgendaActivity.this);

                SimpleDateFormat sdf= new SimpleDateFormat("MMM-dd", new Locale("es_ES"));

                alertDialogBuilder.setMessage( sdf.format(actividad.getFechaDesde()) + " " + sdf.format(actividad.getFechaHasta())+ "\n" +actividad.getNombre() + ": " + actividad.getDescripcion());

                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(AgendaActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                    }
                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AgendaActivity.this,"You clicked no button",Toast.LENGTH_LONG).show();
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
                mLayoutManager.scrollToPositionWithOffset(semanaActual, 0);
            }
        });
    }

    private void generarDatos(){
        myDataset = new ArrayList<Semana>();

        GregorianCalendar gc=new GregorianCalendar();

        final Calendar calendar = Calendar.getInstance();

        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        semanaActual=dayOfYear / 7;

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