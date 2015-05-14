package com.example.point_it_android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AjoutPoint extends Activity {
	
	//Déclaration des variables
	private EditText EditTextPersonne , EditTextDescription;
	private CheckBox Epique ; 
	private Spinner TypeDePoint;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ajout_point);
		
		//Initialisation des variables
		EditTextPersonne = (EditText) findViewById(R.id.EditTextPersonne);
		EditTextDescription = (EditText) findViewById(R.id.EditTextDescription);
		Epique = (CheckBox) findViewById(R.id.CheckboxEpique);
		TypeDePoint = (Spinner) findViewById(R.id.SpinnerTypeDePoint);
		
		spinnerInitialisation();
		
	}
	
	/**
	 * Initialise le spinner avec la liste de point
	 */
	public void spinnerInitialisation()
	{
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.string_array_type_de_point, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears
		TypeDePoint.setAdapter(adapter); // Apply the adapter to the spinner
	}
	
	/**
	 * Ajout le point choisi 
	 * @param v
	 */
	public void AjoutPoint(View v)
	{
		if(!EditTextPersonne.getText().toString().isEmpty() && !EditTextDescription.getText().toString().isEmpty()) //Si les champs personne et description on bien été remplis
		{

			//Mettre un test pour vérifier que la personne existe dans la base ou créer un spinner qui se génere avec tout les nom de la base !
			
			Toast.makeText(getApplicationContext(), "Type de Point : "+ TypeDePoint.getSelectedItem(), Toast.LENGTH_SHORT).show();
			
			if(Epique.isChecked())
			{
				Toast.makeText(getApplicationContext(), "C'est un point EPIQUE ", Toast.LENGTH_SHORT).show();
				//C'est un point EPIQUE
			}
		}
		else
		{
			 Toast.makeText(getApplicationContext(), getResources().getString(R.string.toastFormulaireAjoutPointMalRemplis), Toast.LENGTH_LONG).show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ajout_point, menu);
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
