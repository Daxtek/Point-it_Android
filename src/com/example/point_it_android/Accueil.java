package com.example.point_it_android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Accueil extends Activity 
{
	//Déclaration des variables
	Button boutonVersProfil , boutonVersClassement , boutonVersInscription , boutonVersConnexion;
	ArrayList<Button> BoutonsListe; //Liste des boutons
	TextView connecte;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accueil);
		
		//Initialisation des variables
		connecte = (TextView)findViewById(R.id.connecte);
		//BoutonsListe = new ArrayList<Button>();
		boutonVersProfil= (Button)findViewById(R.id.boutonVersProfil);
		boutonVersClassement = (Button)findViewById(R.id.boutonVersClassement);
		boutonVersInscription  = (Button)findViewById(R.id.boutonVersInscription);
		boutonVersConnexion  = (Button)findViewById(R.id.boutonVersConnexion);
		
		/*BoutonsListe.add(boutonVersProfil );
		BoutonsListe.add(boutonVersClassement );
		BoutonsListe.add(boutonVersInscription );
		BoutonsListe.add(boutonVersConnexion );*/
	
		Log.v("Accueil", "Connexion.connecte" + Connexion.connecte);
		if(Connexion.connecte ) //Si on est connecté au site Point-it
		{
			connecte.setText(getResources().getString(R.string.connexionSuccess));
			//Affiche les boutons
			boutonVersProfil.setVisibility(View.VISIBLE);
			boutonVersClassement.setVisibility(View.VISIBLE);
			
			boutonVersConnexion.setVisibility(View.GONE); //Cache le bouton de connexion
			boutonVersInscription.setVisibility(View.GONE); //Cache le bouton d'inscription
			
		
		}
		else //Si on est pas connecté
		{
			connecte.setText(getResources().getString(R.string.notConnected));
			//Cache les boutons autres que celui de connection 
			boutonVersProfil.setVisibility(View.GONE);
			boutonVersClassement.setVisibility(View.GONE);
			

			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accueil, menu);
		return true;
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
	
	/**
	 * Lance l'activité Profil
	 * @param v
	 */
	public void Profil(View v)
	{
		Intent intent = new Intent(this,Profil.class); //Défini l'intent
		startActivity(intent); //Lance l'intent
	}
	
	/**
	 * Lance l'activité Classement
	 * @param v
	 */
	public void Classement(View v)
	{
		//Intent intent = new Intent(this,Classement.class); //Défini l'intent
		//startActivity(intent); //Lance l'intent
	}
	
	/**
	 * Lance l'activité connexion
	 * @param v
	 */
	public void Connexion(View v)
	{
		Intent intent = new Intent(this,Connexion.class); //Défini l'intent
		startActivity(intent); //Lance l'intent
	}
	
	/**
	 * Lance l'activité inscription
	 * @param v
	 */
	public void Inscription(View v)
	{
		Intent intent = new Intent(this,Inscription.class); //Défini l'intent
		startActivity(intent); //Lance l'intent
	}
	
	/**
	 * Test si le device est connecté à internet
	 * @return boolean
	 */
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    return netInfo != null && netInfo.isConnectedOrConnecting();
	}
}
