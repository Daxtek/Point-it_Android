package com.example.point_it_android;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Profil extends Activity {

	//D�claration des variables
	private ListView ListeDesPoints;
	private TextView Nom_et_Img;
	private TableLayout TableauPoint;
	private SharedPreferences profilPreferences; // Preferences ( Sauvegarde) du profil
	SharedPreferences.Editor mEditor; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profil);
		
		//Initialisation des variables
		ListeDesPoints = (ListView)findViewById(R.id.ListeDesPoints);
		Nom_et_Img = (TextView)findViewById(R.id.PseudoEtImgDuProfil);
		TableauPoint = (TableLayout)findViewById(R.id.TableauDuProfil);
		
		profilPreferences = getSharedPreferences("prefProfil", 0); //r�cup�ration des pr�f�rences du profil
		mEditor = profilPreferences.edit();
		
		if(isOnline() && profilPreferences.getAll().isEmpty()) //Si on est connect� et que les pr�f�rences sont vides
		{
			//Recherche des donnees dans la BDD
			
			
			//Ajout des donn�es dans le tableau
			
			//Ajout des descriptions point dans la liste
			//ListeDesPoints.addView(child);
			
			//Ajout des donn�es dans les pr�f�rences
			
		}
		else if (!profilPreferences.getAll().isEmpty()) //Si les pr�f�rences ne sont pas vides on les places
		{
			
		}
		else
		{
			Toast.makeText(getApplicationContext(), R.string.toastNoConnexion, Toast.LENGTH_LONG ).show(); // Affiche qu'on est pas connect�
			finish();//Quitte l'activit�
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profil, menu);
		return true;
	}
	
	/**
	 * Test si le device est connect� � internet
	 * @return boolean
	 */
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    return netInfo != null && netInfo.isConnectedOrConnecting();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
