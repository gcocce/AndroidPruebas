package ar.com.gitmo.androidpruebas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AgendaActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Semana> myDataset;

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
        mAdapter = new MyAdapter(myDataset, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void generarDatos(){
        myDataset = new ArrayList<Semana>();

        GregorianCalendar gc=new GregorianCalendar();

        final Calendar calendar = Calendar.getInstance();

        int dias = 0;
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
                actividad.setDescripcion("Descripcion...");

                semana.addActividad(actividad);
            }

            myDataset.add(semana);
        }


    }
}