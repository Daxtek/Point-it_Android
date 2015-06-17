package com.example.point_it_android;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class Accueil extends Activity 
{
	//Déclaration des variables
	private Button boutonVersProfil , boutonVersClassement , boutonVersInscription , boutonVersConnexion , boutonVersAjoutPoint;
	private ArrayList<Button> BoutonsCacheListe; //Liste des boutons à faire apparaitre quand on est connecté, les boutons qui sont cachés au départ
	private TextView connecte;
	private Boolean estConnecte = false;
	private LinearLayout layoutCommentaires;
	
	//Variables en rapport avec la liste
	private	ArrayAdapter<String> adapter;
	private ListView listePoint;
	private	ArrayList<ListItem> listItems;
	private String[] listString ;

	//Variables en rapport avec le JSON   
	//JSONParser jParser = new JSONParser();
	private JSONArray  donnees_element = null ; // Tableau JSON des données   
	private ArrayList<String> liste_descriptionPoint , liste_typeDePoint , liste_attribueA ;
	
	// Progress Dialog
	private ProgressDialog progressDialog;

	//Variables en rapport avec le JSON   
	JSONParser jParser = new JSONParser();   

	private String url_points = "http://192.168.1.94/Point-it/index.php/api/getPoints"; //Premier argument = nb de point voulu , deuxieme limite ( à partir de ou) , dernier id de la personne
	RecupDonnees asyncTaskRecupDonnees;
	
	private static final String TAG_DATA = "data";
	private static final String TAG_POINTS = "points";
	private static final String TAG_POINT_DESCRIPTION = "point_description";
	private static final String TAG_TYPEPT_NOM = "typept_nom";
	private static final String TAG_RECOIT = "recoit";
	private static final String TAG_PROFIL_NOM = "profil_nom";
	
	ArrayList<String> descriptions = new ArrayList<String>();
	ArrayList<String> typepts = new ArrayList<String>();
	ArrayList<String> attribuea = new ArrayList<String>();
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accueil);
		
		//Initialisation des variables
		connecte = (TextView)findViewById(R.id.connecte);
		
		boutonVersInscription  = (Button)findViewById(R.id.boutonVersInscription);
		boutonVersConnexion  = (Button)findViewById(R.id.boutonVersConnexion);
		boutonVersProfil= (Button)findViewById(R.id.boutonVersProfil);
		boutonVersClassement = (Button)findViewById(R.id.boutonVersClassement);
		boutonVersAjoutPoint  = (Button)findViewById(R.id.boutonVersAjoutPoint);
		
		BoutonsCacheListe = new ArrayList<Button>();
		BoutonsCacheListe.add(boutonVersClassement );
		BoutonsCacheListe.add(boutonVersProfil );
		BoutonsCacheListe.add(boutonVersAjoutPoint );

		liste_descriptionPoint = new ArrayList<String>();
		liste_typeDePoint = new ArrayList<String>();
		liste_attribueA = new ArrayList<String>();
		listePoint = (ListView) findViewById(R.id.ListePoint);
		Interface(true);
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
		JSONArray points , recoit;
		JSONObject data;
		
		
		RecupDonnees(int toastMessage) //Constructeur avec le message seul
		{
			this.toastMessage = toastMessage;
			data = new JSONObject();
		}
		
		/**
		 * Avant de lancŽ la t‰che de fond, lance un dialog de chargement.
		 */
		@Override
		protected void onPreExecute() {
			//Progress Dialog 
			progressDialog = new ProgressDialog(Accueil.this);
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
			List<NameValuePair> parametres = new ArrayList<NameValuePair>();

			 Log.d("Accueil" , "url_profil "+url_points);
			
			 url_points += "/10";
			 // getting JSON string from URL
	         JSONObject json = jParser.makeHttpRequest(url_points, "POST", parametres);
	         Log.d("Accueil" , "json "+json);
	         
	         try {
	        	 
	        	 	data = (JSONObject) json.get(TAG_DATA);
	        	 	 Log.d("Accueil" , "data "+data);
	        		if (!data.isNull(TAG_POINTS))//SŽcuritŽ si le json rŽcupŽrŽ n'est pas vide
		         	{
	        			points = data.getJSONArray(TAG_POINTS);
	        			//Log.d("Accueil" , "points.size() "+ points.length() );
	        			
	        			for(int  i = 0 ; i<points.length() ; i++) //Parcours le tableau de donnees JSON
		         		{
		         			JSONObject c = points.getJSONObject(i); //RŽcup�re l'objet ˆ la position i du tableau 
		         			
		         			descriptions.add( c.getString(TAG_POINT_DESCRIPTION));  //rŽcup�re la donnŽe de l'objet et la place dans la arraylist de donnŽe
		         			//Log.d("Accueil", "descriptions "+ descriptions);
		         			/*lastIndex  =NombresDePoint.size()-1;
			            	mEditor.putString(TAG_NOMBRE+lastIndex, NombresDePoint.get(lastIndex)).commit(); // Sauvegarde chaque donnees dans les prŽfŽrences*/
							
			            	typepts.add( c.getString(TAG_TYPEPT_NOM));  //rŽcup�re la donnŽe de l'objet et la place dans la arraylist de donnŽe
			            	//Log.d("Accueil", "typepts "+ typepts);
			            	//mEditor.putString(TAG_TYPEPT_NOM+lastIndex, TypesDePoint.get(lastIndex)).commit(); // Sauvegarde chaque donnees dans les prŽfŽrences*/
				
			            	recoit = c.getJSONArray(TAG_RECOIT);
			            	String noms_recoit = "";
			            	for(int j = 0 ; j<recoit.length() ; j++) //Parcours le tableau de donnees JSON
			         		{
			            		
			            		JSONObject d = recoit.getJSONObject(j); //RŽcup�re l'objet ˆ la position i du tableau 
			            		if(j == 0)
			            		{
			            			noms_recoit += d.getString(TAG_PROFIL_NOM);
			            		}
			            		else
			            		{
			            			noms_recoit += " , "+ d.getString(TAG_PROFIL_NOM);
			            		}
			            		
			         		}
			            	attribuea.add(noms_recoit);
		         		}
		         	}
		        } catch (JSONException e) {
				
					e.printStackTrace();
				}
	              
	        
				
			return null;
			
			}
		
		/**
		 * Une fois la t‰che de fond fini, place les donnŽes dans la vue principale
		 */
		@Override
		protected void onPostExecute(String result) 
		{
			progressDialog.dismiss(); // dismiss the dialog
				
			for(int  i = 0 ; i<descriptions.size() ; i++) //Parcours le tableau de donnees JSON
     		{
				liste_descriptionPoint.add(descriptions.get(i));
				liste_typeDePoint.add(typepts.get(i));
				liste_attribueA.add(attribuea.get(i));
     		}
			
			listInitialisation(liste_descriptionPoint , liste_typeDePoint , liste_attribueA);
			//typepts
		}
	}
	
	/**
	 * Lance la tache de fondqui va rŽcupŽrer les donnŽes
	 */
	public void lauchRecupDonnee()
	{
		asyncTaskRecupDonnees = new RecupDonnees(R.string.toastRecupDonnee); //RŽcup�re la classe de la t‰che asynchrone avec le message de rŽcupŽration de donnŽes
		asyncTaskRecupDonnees.execute( ); // Lance la t‰che asynchrone
		Handler handler = new Handler();
		// Si au bout de 30 secondes la rŽcupŽration de donnŽes est encore en train de tourner ( RUNNING ) alors on l'arr�te
		handler.postDelayed(new Runnable()
		{
			public void run() 
			{
				if ( asyncTaskRecupDonnees.getStatus().equals(AsyncTask.Status.RUNNING) ) // VŽrification de si la t‰che est entrain de s'Žxecuter ( au statut RUNNING )
				{ 
					asyncTaskRecupDonnees.cancel(true); // Arr�te de la rŽcupŽration de donnŽe
					progressDialog.dismiss(); // Arr�te la progressDialog
					Toast.makeText(getApplicationContext(), R.string.toastUpdateFail, Toast.LENGTH_LONG ).show(); // Affiche un message d'erreur
					finish();
				}   
			}
		}, 30000 ); // DŽfinie la durŽe en milliseconde
	}
	
	public void onResume()
	{
		
		
		Interface(false);
		super.onResume();
	}
	
	/**
	 * Vérifie si on est connecté et adapte l'interface en fonction
	 */
	public void Interface(Boolean launchRecupDonnees)
	{
		if(isConnected() ) //Si on est connecté au site Point-it
		{
			connecte.setText(getResources().getString(R.string.connexionSuccess));
			//Affiche les boutons
			for(int i = 0 ; i<BoutonsCacheListe.size() ; i++)
			{
				BoutonsCacheListe.get(i).setVisibility(View.VISIBLE);
			}
			boutonVersConnexion.setVisibility(View.GONE); //Cache le bouton de connexion
			boutonVersInscription.setVisibility(View.GONE); //Cache le bouton d'inscription
			
			if(launchRecupDonnees)
			{
				lauchRecupDonnee();
			}
			
			
			listePoint.setVisibility(View.VISIBLE);
			
			
			
		
		}
		else //Si on est pas connecté
		{
			connecte.setText(getResources().getString(R.string.notConnected));
			//Cache les boutons autres que celui de connection et inscription
			for(int i = 0 ; i<BoutonsCacheListe.size() ; i++)
			{
				BoutonsCacheListe.get(i).setVisibility(View.GONE);
			}
		}
	}
	
	/**
	 * Initialise la liste des points
	 */
	public void listInitialisation( ArrayList<String> descriptionPoint , ArrayList<String> typeDePoint , ArrayList<String> attribueA)
	{
		listePoint = (ListView) findViewById(R.id.ListePoint);
		listItems = new ArrayList<ListItem>();
		
		for ( int i = 0 ; i<descriptionPoint.size() ; i++) // Ajout des contenus ( texte ) dans les élements de la liste
		{
			//Log.v("ListeViaBDD" , "nom["+i+"] "+nom.get(i));
			listItems.add(new ListItem( descriptionPoint.get(i) , typeDePoint.get(i) , attribueA.get(i)  ) );
		}
		listePoint.setAdapter(new CustomListViewAdapter(this, listItems)); // Défini l'adapter personnaliser
	}
	
	/**
	 * Approuve le point
	 */
	public void Approuver(View v)
	{
		if(Connexion.connecte)
		{
			
		}
		else
		{
			
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.toastNonConnecterApprouver), Toast.LENGTH_SHORT).show();
			Connexion(v);
		}
	}
	
	/**
	 * Vérifie si on est connecté au site Point-it
	 * @return
	 */
	public boolean isConnected()
	{
		Log.v("Accueil", "Connexion.connecte" + Connexion.connecte);
		if(Connexion.connecte ) //Si on est connecté au site Point-it
		{
			return true;
		}
		else //Si on est pas connecté
		{
			return false;
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
		Intent intent = new Intent(this,Classement.class); //Défini l'intent
		startActivity(intent); //Lance l'intent
	}
	
	/**
	 * Lance l'activité AjoutPoint
	 * @param v
	 */
	public void AjoutPoint(View v)
	{
		Intent intent = new Intent(this,AjoutPoint.class); //Défini l'intent
		startActivity(intent); //Lance l'intent
	}
	
	
	/**
	 * Lance l'activité connexion
	 * @param v
	 */
	public void Connexion(View v)
	{
		if(isOnline())
		{
			Intent intent = new Intent(this,Connexion.class); //Défini l'intent
			startActivity(intent); //Lance l'intent
		}
		else
		{
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.toastNoConnexion), Toast.LENGTH_SHORT).show();
		}
		
	}
	
	/**
	 * Lance l'activité inscription
	 * @param v
	 */
	public void Inscription(View v)
	{
		if(isOnline())
		{
			Intent intent = new Intent(this,Inscription.class); //Défini l'intent
			startActivity(intent); //Lance l'intent
		}
		else
		{
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.toastNoConnexion), Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * Lance l'activité commenter
	 * @param v
	 */
	public void Commenter(View v)
	{
		if(Connexion.connecte)
		{
			Intent intent = new Intent(this,Commenter.class); //Défini l'intent
			startActivity(intent); //Lance l'intent
		}
		else
		{
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.toastNonConnecterApprouver), Toast.LENGTH_SHORT).show();
			Connexion(v);
		}
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
