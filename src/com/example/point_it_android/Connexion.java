package com.example.point_it_android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Connexion extends Activity {

	//Déclarations des variables
	EditText nomUtilisateur , Mdp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connexion);
		
		//Initialisation des variables
		nomUtilisateur = (EditText) findViewById(R.id.EditTextNomUtilisateur);
		Mdp = (EditText) findViewById(R.id.EditTextMDP);
	}
	
	public void login(View view){
	      if(nomUtilisateur.getText().toString().equals("admin") && Mdp.getText().toString().equals("admin"))
	      {
	    	  //Lancé la connexion
	      }	
	      else
	      {
	    	  Toast.makeText(getApplicationContext(), "Le login ou le mot de passe est faux", Toast.LENGTH_SHORT).show();

	    	  //Si on veut mettre un nombre d'essaie maximum
	    	  /*
		      counter--;
		      attempts.setText(Integer.toString(counter));
		      if(counter==0){
		         login.setEnabled(false);
		      }*/
	      }
	   }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.connexion, menu);
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
}
