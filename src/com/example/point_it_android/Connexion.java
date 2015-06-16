package com.example.point_it_android;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Connexion extends Activity {

	//Déclaration des variables
	private EditText nomUtilisateur , Mdp;
	public static boolean connecte;
	
	// Progress Dialog
	private ProgressDialog progressDialog;
	
	//Variables en rapport avec le JSON   
	JSONParser jParser = new JSONParser();
	private JSONArray  donnees_element = null ; // Tableau JSON des donnŽes   
	//private ArrayList<String> tableau_produit , tableau_nom , tableau_recup_produit , tableau_recup_nom ;
	//private String[] tableau_tag_donnees = {"produit" , "nom"}; 
	
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
	    	  connecte = true;
	    	  Toast.makeText(getApplicationContext(), getResources().getString(R.string.connexionSuccess), Toast.LENGTH_SHORT).show();
	    	  Accueil();
	    	  
	      }	
	      else
	      {
	    	  Toast.makeText(getApplicationContext(),  getResources().getString(R.string.connexionFailed), Toast.LENGTH_SHORT).show();

	    	  //Si on veut mettre un nombre d'essaie maximum
	    	  /*
		      counter--;
		      attempts.setText(Integer.toString(counter));
		      if(counter==0){
		         login.setEnabled(false);
		      }*/
	      }
	   }
	
	
	
	/**
	 * Classe de t‰che asynchrone qui rŽcup�re ( en t‰che de fond ) les donnŽes de la base de donnŽes
	 * @author Lo•c DieudonnŽ
	 *
	 */
	class RecupDonnees extends AsyncTask<String,String , String>
	{
		int toastMessage;
		int NB_Item_preference;
		boolean comingFromUpdate;
		
		RecupDonnees(int toastMessage) //Constructeur avec le message seul
		{
			this.toastMessage = toastMessage;
		}
		
		/**
		 * Avant de lancŽ la t‰che de fond, lance un dialog de chargement.
		 */
		@Override
		protected void onPreExecute() {
			//Progress Dialog 
			progressDialog = new ProgressDialog(Connexion.this);
            progressDialog.setMessage(getString(toastMessage));
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
		}
		
		/**
		 * RŽcup�re et stock les diffŽrentes donnŽes de la base
		 */
		@Override
		protected String doInBackground(String... params) 
		{ 
			/*tableau_produit = new ArrayList<String>();//initialisation de la arrayliste
    		tableau_nom = new ArrayList<String>();//initialisation de la arrayliste
    		
    		List<NameValuePair> parametres = new ArrayList<NameValuePair>();
			if(pages !=null)
			{
				for(int x = 0; x<pages.length ; x++) //Parcours des diffŽrentes pages
				{
					
					if ( famille) //Si on vient d'une page famille
					{					
						NameValuePair NVPid = new BasicNameValuePair("page", pages[x]); //DŽfini le param�tre "page=la_valeur_page"
						parametres.add(NVPid);
						
						NameValuePair NVPfamille = new BasicNameValuePair("famille", ""+famille); //DŽfini le param�tre "famille=true"
						parametres.add(NVPfamille);
					
					}
					else //Si on nous demande juste une page ou un ensemble de pages
					{
						NameValuePair NVPid = new BasicNameValuePair("page", pages[x]); //DŽfini le param�tre "page=la_valeur_page"
						parametres.add(NVPid);
					}
				
					recuperation_des_donnees(parametres);
				}
			}
			else //Si le tableau de pages n'est pas dŽfini
			{
				if (activitesInterieur) //Si on nous demandes toute les activites interieurs
				{
					NameValuePair NVPactivitesInterieur = new BasicNameValuePair("activitesInterieur", ""+activitesInterieur); //DŽfini le param�tre "activitesInterieur=true"
					parametres.add(NVPactivitesInterieur);
				}
				else if (activitesExterieur) //Si on nous demandes toute les activites exterieurs
				{
					NameValuePair NVPactivitesExterieur = new BasicNameValuePair("activitesExterieur", ""+activitesExterieur); //DŽfini le param�tre "activitesExterieur=true"
					parametres.add(NVPactivitesExterieur);
				}
				
				recuperation_des_donnees(parametres);
				
			}*/
		
			return null;
		}
		
		/**
		 * Une fois la t‰che de fond fini, place les donnŽes dans la vue principale
		 */
		@Override
		protected void onPostExecute(String result) 
		{
			progressDialog.dismiss(); // dismiss the dialog
			
			View view = new View(getApplicationContext());
			login(view);
			
		}
	}
	

	/**
	 * Lance l'activité accueil
	 * @param v
	 */
	public void Accueil()
	{
		Intent intent = new Intent(this,Accueil.class); //Défini l'intent
		startActivity(intent); //Lance l'intent
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
