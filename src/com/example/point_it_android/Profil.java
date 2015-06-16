package com.example.point_it_android;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;


public class Profil extends Activity {

	//Déclaration des variables
	private ListView ListeDesPoints;
	private TextView Nom_et_Img;
	private TableLayout TableauPoint;
	private SharedPreferences profilPreferences; // Preferences ( Sauvegarde) du profil
	SharedPreferences.Editor mEditor; 
	
	// Progress Dialog
	private ProgressDialog progressDialog;

	//Variables en rapport avec le JSON   
	JSONParser jParser = new JSONParser();
	private JSONArray  donnees_element = null ; // Tableau JSON des donnŽes   
	
	RecupDonnees asyncTaskRecupDonnees;
	private ArrayList<String> TypesDePoint , NombresDePoint;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profil);
		
		//Initialisation des variables
		ListeDesPoints = (ListView)findViewById(R.id.ListeDesPoints);
		Nom_et_Img = (TextView)findViewById(R.id.PseudoEtImgDuProfil);
		TableauPoint = (TableLayout)findViewById(R.id.TableauDuProfil);
		
		profilPreferences = getSharedPreferences("prefProfil", 0); //récupération des préférences du profil
		mEditor = profilPreferences.edit();
		
		TypesDePoint = new ArrayList<String>(); 
		NombresDePoint = new ArrayList<String>(); 
		
		if(isOnline() && profilPreferences.getAll().isEmpty()) //Si on est connecté et que les préférences sont vides
		{
			lauchRecupDonnee();
			
			
			
			
		}
		else if (!profilPreferences.getAll().isEmpty()) //Si les préférences ne sont pas vides on les places
		{
			
		}
		else
		{
			Toast.makeText(getApplicationContext(), R.string.toastNoConnexion, Toast.LENGTH_LONG ).show(); // Affiche qu'on est pas connecté
			finish();//Quitte l'activité
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
			progressDialog = new ProgressDialog(Profil.this);
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
    		
    		// getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(urlListe, "GET", parametres);
         try 
         { 	
         	if (!json.isNull("donnees"))//SŽcuritŽ si le json rŽcupŽrŽ n'est pas vide
         	{
         		donnees_element = json.getJSONArray("donnees"); 
         		for(int  i = 0 ; i<donnees_element.length() ; i++) //Parcours le tableau de donnees JSON
         		{
         			JSONObject c = donnees_element.getJSONObject(i); //RŽcup�re l'objet ˆ la position i du tableau 
         			int lastIndexProduit = 0 ;
     				int lastIndexNom  = 0;
         			for( int j = 0 ; j<tableau_tag_donnees.length ; j++) //Parcours l'intŽrieur d'un ŽlŽments du tableau
             		{            		
         				
         				if(tableau_tag_donnees[j].equals("produit") )
	            			{
	            				tableau_produit.add( c.getString(tableau_tag_donnees[j]));  //rŽcup�re la donnŽe de l'objet et la place dans la arraylist de donnŽe
	            				lastIndexProduit  =tableau_produit.size() -1;
	            				mEditor.putString("produit"+lastIndexProduit, tableau_produit.get(lastIndexProduit)).commit(); // Sauvegarde chaque donnees dans les prŽfŽrences
	            			}
	            			if(tableau_tag_donnees[j].equals("nom") )
	            			{
	            				tableau_nom.add( c.getString(tableau_tag_donnees[j]));  //rŽcup�re la donnŽe de l'objet et la place dans la arraylist de donnŽe
	            				lastIndexNom  =tableau_nom.size() -1;
	            				mEditor.putString("nom"+lastIndexNom, tableau_nom.get(lastIndexNom)).commit(); // Sauvegarde chaque donnees dans les prŽfŽrences
	            			}
             		}
         			Pair<String , String> Pair = new Pair<String , String>(tableau_nom.get(lastIndexNom) ,tableau_produit.get(lastIndexProduit)); //CrŽation de la paire d'objet
         			listPair.add(Pair);  //Ajout de la paire dans la liste
         			
         			
         		}
         	
         	}*/
			
			TypesDePoint.add("Moustache");
			NombresDePoint.add("3");
			TypesDePoint.add("Princesse");
			NombresDePoint.add("4");
		
			return null;
		}
		
		/**
		 * Une fois la t‰che de fond fini, place les donnŽes dans la vue principale
		 */
		@Override
		protected void onPostExecute(String result) 
		{
			progressDialog.dismiss(); // dismiss the dialog
				
			Nom_et_Img.setText("Thoumouuu");
			Nom_et_Img.setCompoundDrawables(null, null, null, null/*bottom*/);
			
			
			
			for(int i=0 ; i</*NB_types_de_point_*/TypesDePoint.size() ; i++)
			{
				TableRow tableRow = new TableRow(getApplicationContext()); //Créer une nouvelle ligne
				
				TextView typedepoint = new TextView(getApplicationContext()); // Créer le textView qui contient le type de point
				typedepoint.setText(TypesDePoint.get(i));
				typedepoint.setGravity(Gravity.CENTER);
				typedepoint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				typedepoint.setTextColor(Color.BLACK);
				
				TextView nombredepoint = new TextView(getApplicationContext()); // Créer le textView qui contient le nombre de point
				Log.v("Profil" , "NombresDePoint.get(i) " + NombresDePoint.get(i) );
				nombredepoint.setText(NombresDePoint.get(i));
				nombredepoint.setGravity(Gravity.CENTER);
				nombredepoint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				nombredepoint.setTextColor(Color.BLACK);
				
				tableRow.addView(typedepoint);		
				tableRow.addView(nombredepoint);		
				TableauPoint.addView(tableRow); 	//Ajout la ligne dans le tableau
				
			}
			
			
			
			
			
			//Ajout des descriptions point dans la liste
			//ListeDesPoints.addView(child);
			
			//Ajout des données dans les préférences
		}
	}
	
	
	/**
	 * Lance lˆ tache de fondqui va rŽcupŽrer les donnŽes
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profil, menu);
		return true;
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
