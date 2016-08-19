package ar.com.gitmo.androidpruebas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    GridView grid;
    String[] opciones = {
            "Calendario",
            "Institucional",
            "Teléfonos",
            "Sedes"
    } ;

    int[] imageId = {
            R.drawable.icono_calendario,
            R.drawable.icono_insitucional,
            R.drawable.icono_guiatelefonica,
            R.drawable.icono_sedes
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        CustomMenuGrid adapter = new CustomMenuGrid(MenuActivity.this, opciones, imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                switch (position){
                    case 0: // Calendario
                        startActivity(new Intent("ar.com.gitmo.androidpruebas.AgendaActivity"));
                        break;
                    default:
                        Toast.makeText(MenuActivity.this, getResources().getString(R.string.menu_proximamente),
                                Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}