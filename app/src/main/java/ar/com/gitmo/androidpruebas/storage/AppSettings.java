package ar.com.gitmo.androidpruebas.storage;

import android.content.SharedPreferences;
import android.util.Log;

/*
 Esta clase se usa para encapsular el objeto SharedPreferences que permite
 guardar datos de tipo key value en la memoria
*/

public class AppSettings {

	public static final String CONFIG_NAME = "FiubappConfigFile";

	private final static String TAG = "FIUBAPP_LOG";
	private final static String TAG_ACTIVITY = "FIUBAPP_CONFIG: ";

	private SharedPreferences settings;

	private String nick; 				// Nombre de usuario
	private String email;				// Email del usuario si hubiere
	private String base_url;
	private int runTimes;

	public AppSettings(SharedPreferences settings){
		this.settings=settings;
		this.loadConfig();
	}
	
	public String getNick() {
		return nick;
	}
	public void setNick(String name) {
		nick = name;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String mail) {
		email = mail;
	}

	public String getBaseURL() {
		return base_url;
	}
	public void setBaseURL(String url) {
		base_url = url;
	}

	public void setRunTimes(int times){ runTimes=times;	}
	public int getRunTimes(){return runTimes;}

	//SharedPreferences settings
	public void loadConfig()
	{	
		Log.i("LoadConfig","Se ejecuta el metodo LoadConfig de la clase AppSettings.");

		nick=settings.getString("nick", "anonim");
		email=settings.getString("email", "");
		runTimes=settings.getInt("runtimes", 0);

        // Para pruebas en el emulador usar "http://10.0.2.2" si apuntamos al localhost de la pc
		base_url=settings.getString("base_url", "http://10.0.2.2:8080/"); // 192.168.1.101

		// Para el celular con el servidor corriendo en el localhost
		//base_url=settings.getString("base_url", "http://192.168.1.100:8080/"); // 192.168.1.101
	}
	
	public void saveConfig(){
		Log.i("SaveConfig","Se ejecuta el metodo SaveConfig de la clase AppSettings.");
		
		SharedPreferences.Editor editor=settings.edit();

		editor.putString("nick", nick);
		editor.putString("base_url", base_url);
		editor.putString("email", email);
		editor.putInt("runtimes", runTimes);
		editor.commit();
	}

	public void flush(){
		saveConfig();
	}

}
