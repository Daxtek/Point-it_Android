package com.example.point_it_android;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.point_it_android.Profil.RecupDonnees;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Connexion extends Activity {

	//Déclaration des variables
	private EditText nomUtilisateur , Mdp;
	public static boolean connecte;
	Boolean erreur = false;
	
	// Progress Dialog
	private ProgressDialog progressDialog;
	
	//Variables en rapport avec le JSON   
	JSONParser jParser = new JSONParser();
	private JSONArray  donnees_element = null ; // Tableau JSON des donnŽes   
	private String url_connexion = "http://192.168.1.94/Point-it/index.php/api/login";
	ConnexionAsyncTask connexionAsyncTask;
	
	 public static String url_image , id , nom , login , mdp;
	
	// JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ERRORS = "errors";
    private static final String TAG_DATA ="data";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connexion);
		
		//Initialisation des variables
		nomUtilisateur = (EditText) findViewById(R.id.EditTextNomUtilisateur);
		Mdp = (EditText) findViewById(R.id.EditTextMDP);
	}
	
	/*public void login(View view){
	      if(nomUtilisateur.getText().toString().equals("admin") && Mdp.getText().toString().equals("admin"))
	      {
	    	  //Lance la connexion
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
		      }
	      }
	   }*/
	
	
	/**
	 * Lance la connexion
	 */
	public void lauchConnexion(View v)
	{
		connexionAsyncTask = new ConnexionAsyncTask(R.string.title_activity_connexion); //RŽcup�re la classe de la t‰che asynchrone avec le message de rŽcupŽration de donnŽes
		connexionAsyncTask.execute( ); // Lance la t‰che asynchrone
		Handler handler = new Handler();
		// Si au bout de 30 secondes la rŽcupŽration de donnŽes est encore en train de tourner ( RUNNING ) alors on l'arr�te
		handler.postDelayed(new Runnable()
		{
			public void run() 
			{
				if ( connexionAsyncTask.getStatus().equals(AsyncTask.Status.RUNNING) ) // VŽrification de si la t‰che est entrain de s'Žxecuter ( au statut RUNNING )
				{ 
					connexionAsyncTask.cancel(true); // Arr�te de la rŽcupŽration de donnŽe
					progressDialog.dismiss(); // Arr�te la progressDialog
					Toast.makeText(getApplicationContext(), R.string.toastConnexionFail, Toast.LENGTH_LONG ).show(); // Affiche un message d'erreur
					finish();
				}   
			}
		}, 30000 ); // DŽfinie la durŽe en milliseconde
	}
	
	
	
	/**
	 * Classe de t‰che asynchrone qui rŽcup�re ( en t‰che de fond ) les donnŽes de la base de donnŽes
	 * @author Lo•c DieudonnŽ
	 *
	 */
	class ConnexionAsyncTask extends AsyncTask<String,String , String>
	{
		int toastMessage;
		int success ;
        String errors ;
        JSONObject data;
		
		ConnexionAsyncTask(int toastMessage) //Constructeur avec le message seul
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
		protected String doInBackground(String... args) 
		{ 
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("login", nomUtilisateur.getText().toString()));
            params.add(new BasicNameValuePair("password", Mdp.getText().toString()));
            
            /*Log.d("Connexion", "login "+ nomUtilisateur.getText().toString());
            Log.d("Connexion", "password "+ Mdp.getText().toString());*/

            JSONObject json = jParser.makeHttpRequest(url_connexion, "POST", params);
            
           
         // check log cat fro response
            Log.d("Connexion", "json "+json.toString());
            
            // check for success tag
            try {
                
            	 success = json.getInt(TAG_SUCCESS);
            	 Log.d("Connexion", "sucess "+ success);
            	 if (success == 1) 
            	 {
            			 
	            	data = (JSONObject) json.get(TAG_DATA);
	                Log.d("Connexion", "data "+ data);
	                url_image = (String) data.get("profil_image");
	                id = (String) data.get("profil_id");
	                nom = (String) data.get("profil_nom");
	               
	                Log.d("Connexion", "url_image "+url_image);
	                Log.d("Connexion", "id "+id);
	                Log.d("Connexion", "nom "+nom);
	                
	                login = nomUtilisateur.getText().toString();
	                mdp = Mdp.getText().toString();
  
                   //Connection successed
                	connecte = true;
                	erreur = false;
                	Accueil();
                    // closing this screen
                    finish();
                } else {
                	errors = json.getString(TAG_ERRORS);
                	 Log.d("Connexion", "errors "+ errors);
                	erreur = true;
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
			
			Log.d("Connexion", "erreur "+ erreur);
			if(erreur )
			{	 
				Toast.makeText(getApplicationContext(),  errors, Toast.LENGTH_SHORT).show();
			}
			 
			
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
