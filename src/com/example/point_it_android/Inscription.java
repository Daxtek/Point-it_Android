package com.example.point_it_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Inscription extends Activity {

	//D�clarations des variables
	private EditText nomUtilisateur , Mdp , confirmerMDP;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription);
		
		//Initialisation des variables
		nomUtilisateur = (EditText) findViewById(R.id.EditTextNomUtilisateur);
		Mdp = (EditText) findViewById(R.id.EditTextMDP);
		confirmerMDP = (EditText) findViewById(R.id.EditTextConfirmerMDP);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inscription, menu);
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
	
	public void Inscription(View v)
	{
		if (confirmerMDP.getText().toString().equals(Mdp.getText().toString())) //Si le mot de passe et sa confirmation sont les m�mes
		{
			
			//Ajout des donn�es dans la base
			//if( les donn�es ont bien �t� ajout�es � la base )
			//{
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.inscriptionSuccess), Toast.LENGTH_SHORT).show();
		    	Accueil();
			//}
		}
	}
	
	/**
	 * Lance l'activit� accueil
	 * @param v
	 */
	public void Accueil()
	{
		Intent intent = new Intent(this,Accueil.class); //D�fini l'intent
		startActivity(intent); //Lance l'intent
	}
}
