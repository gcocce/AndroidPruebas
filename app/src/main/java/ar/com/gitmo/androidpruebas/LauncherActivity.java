package ar.com.gitmo.androidpruebas;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LauncherActivity extends AppCompatActivity {

    // Duración en Milisegundos de la pantalla inicial
    private final static int INTRO_TIME=2500;

    // Preferencias de la aplicación
    public static AppSettings appSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        appSettings = new AppSettings(getSharedPreferences(AppSettings.CONFIG_NAME, MODE_PRIVATE));

        // Comprobamos si el nombre de versión contiene la palabra "beta"
        String version="";

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }

        // Si tiene la palabra "beta" mostramos el indicador
        if (version.toLowerCase().contains("beta")){
            TextView betaTV= (TextView)findViewById(R.id.betaTextView);
            betaTV.setVisibility(View.VISIBLE);
        }

        // Thread para simular demora antes de cambiar de Activity
        Thread logoTimer= new Thread (){
            public void run(){
                try{
                    int logoTimer= 0;

                    while(logoTimer< INTRO_TIME){

                        sleep(50);
                        logoTimer= logoTimer + 50;
                    }

                    String activity="ar.com.gitmo.androidpruebas.MenuActivity";

                    if (appSettings.getRunTimes()==0){
                        activity="ar.com.gitmo.androidpruebas.BienvenidoActivity";
                    }

                    // Aumentamos en uno el valor del atributo
                    appSettings.setRunTimes(appSettings.getRunTimes() + 1);
                    appSettings.saveConfig();

                    startActivity(new Intent(activity));

                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    finish();
                }
            }
        };

        logoTimer.start();
    }
}
