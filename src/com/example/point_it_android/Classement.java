package com.example.point_it_android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TextView;

public class Classement extends Activity {
	
	//D�claration des variables
	private TextView typeDeClassement , TextView1erClassement , TextView2emeClassement , TextView3emeClassement;
	private TableLayout TableauClassement;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classement);
		
		//Initialisation des variables
		typeDeClassement = (TextView) findViewById(R.id.typeDeClassement);
		TextView1erClassement = (TextView) findViewById(R.id.TextView1erClassement);
		TextView2emeClassement = (TextView) findViewById(R.id.TextView2emeClassement);
		TextView3emeClassement = (TextView) findViewById(R.id.TextView3emeClassement);
		TableauClassement = (TableLayout)findViewById(R.id.TableauClassement);
		
		
		//Recherche des donnees dans la BDD
		
		//Ajout des donn�es dans le tableau
		
		//Ajout des donn�es dans les pr�f�rences
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.classement, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.classementGeneral:
			//Cr�er la fonction de r�cup�ration du classement classementGeneral
			return true;
		case R.id.moustache:
			//Cr�er la fonction de r�cup�ration du classement moustache
			return true;
		case R.id.nazi:
			//Cr�er la fonction de r�cup�ration du classement nazi
			return true;
		case R.id.princesse:
			//Cr�er la fonction de r�cup�ration du classement princesse
			return true;
		case R.id.vietnam:	
			//Cr�er la fonction de r�cup�ration du classement vietnam
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}	
