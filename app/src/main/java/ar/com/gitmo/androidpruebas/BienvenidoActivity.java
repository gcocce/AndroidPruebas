package ar.com.gitmo.androidpruebas;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BienvenidoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);

        Button buttonContinuar= (Button)findViewById(R.id.btContinuar);

        buttonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent("ar.com.gitmo.androidpruebas.PruebaListActivity"));
            }
        });
    }
}
