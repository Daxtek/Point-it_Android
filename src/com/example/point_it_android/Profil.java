package com.example.point_it_android;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;



public class Profil extends Activity {

	//Déclaration des variables
	private ListView ListeDesPoints;
	private TextView Nom_et_Img;
	private TableLayout TableauPoint;
	private LinearLayout LinearLayoutImgetTxt;
	private ImageView ImgProfil;
	private SharedPreferences profilPreferences; // Preferences ( Sauvegarde) du profil
	SharedPreferences.Editor mEditor; 
	
	// Progress Dialog
	private ProgressDialog progressDialog;

	//Variables en rapport avec le JSON   
	JSONParser jParser = new JSONParser();
	private JSONArray  donnees_element = null ; // Tableau JSON des donnŽes   
	
	RecupDonnees asyncTaskRecupDonnees;
	private ArrayList<String> TypesDePoint , NombresDePoint;
	
	//DŽfinition du spinner via son layout
	ProgressBar loadingSpinner ;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profil);
		
		//Initialisation des variables
		ListeDesPoints = (ListView)findViewById(R.id.ListeDesPoints);
		Nom_et_Img = (TextView)findViewById(R.id.PseudoEtImgDuProfil);
		TableauPoint = (TableLayout)findViewById(R.id.TableauDuProfil);
		LinearLayoutImgetTxt = (LinearLayout)findViewById(R.id.LinearLayoutImgetTitre);
		ImgProfil = (ImageView)findViewById(R.id.ImgDuProfil);
		
		profilPreferences = getSharedPreferences("prefProfil", 0); //récupération des préférences du profil
		mEditor = profilPreferences.edit();
		
		mEditor.clear().commit();
		
		TypesDePoint = new ArrayList<String>(); 
		NombresDePoint = new ArrayList<String>(); 
		
		 loadingSpinner = new ProgressBar(this);
			loadingSpinner = (ProgressBar)(getLayoutInflater().inflate(R.layout.loading_spinner, null));
			loadingSpinner.setVisibility(View.GONE);
			LinearLayoutImgetTxt.addView(loadingSpinner);
		
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
			
			
			//Place les données
			for(int i=0 ; i</*NB_types_de_point_*/TypesDePoint.size() ; i++)
			{
				TableRow tableRow = new TableRow(getApplicationContext()); //Créer une nouvelle ligne
				tableRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				tableRow.setGravity(Gravity.CENTER);
				
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
			
			//DŽfinition du texte view de l'img indisponible
			TextView TVImgIndisponible = new TextView(getApplicationContext());
			TVImgIndisponible.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			TVImgIndisponible.setGravity(Gravity.CENTER);
			TVImgIndisponible.setText(getString(R.string.imgNonDisponible));
			TVImgIndisponible.setTextColor(Color.RED);
			TVImgIndisponible.setVisibility(View.GONE);
			
			//Lance le téléchargement de l'img
			new DownloadImageTask(ImgProfil ,  loadingSpinner , TVImgIndisponible  ).execute("http://pointit.fr/assets/images/member.png");
			
			
			
			
			
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
	 * T‰che asynchrone qui tŽlŽcharge l'image venant de l'url passŽ en param�tre et la place dans l'ImageView passŽ en param�tre
	 * @author DieudonnŽ Lo•c
	 *
	 */
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView imgProfil;
	    ProgressBar loadingSpinner;
	    TextView TVImgIndisponible;
	    Boolean ErreurImage = false;
	    
	    //constructor
	    public DownloadImageTask(ImageView imgProfil , ProgressBar loadingSpinner ,  TextView TVImgIndisponible  ) {
	        this.imgProfil = imgProfil;
	        this.loadingSpinner = loadingSpinner;
	        this.TVImgIndisponible = TVImgIndisponible;
	    
	    }
	    
	   //Avant le tŽlŽchargement de l'image affiche la roue de chargment
	    protected void onPreExecute() {
	    	loadingSpinner.setVisibility(View.VISIBLE);
	    	super.onPreExecute();
	    }
	    
	    // laoding picture and put it into bitmap 
	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0]; //RŽcup�re l'url passŽ en argument
	        Bitmap bitmapImage = null; //Initialise le Bitmap
	        InputStream in = null;
	
	          try {
				in = new java.net.URL(urldisplay).openStream(); //Initialise et ouvre l'input Stream
			} catch (MalformedURLException e2) {
				e2.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			} 
	          
	        
		      final BitmapFactory.Options options = new BitmapFactory.Options();
		  	  options.inJustDecodeBounds = false;
		  	  /*if (screenWidth <= 320) // dŽfini ...
	          {
		  	    	options.inSampleSize = 12;
	          } 
		  	  else if (screenWidth <= 480)
		  	  {
		  		  options.inSampleSize = 8;
		  	  }
		  	  else
		  	  {
		  		  options.inSampleSize = 4;
		  	  }*/
		  	try {
	          bitmapImage = BitmapFactory.decodeStream(in , null, options);
	          
	          
	        } catch (Error e) {
	           
	            try {
					in.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            ErreurImage = true;
	            e.printStackTrace();
	            return null;
	        } catch (Exception e) {
	            try {
					in.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            ErreurImage = true;
	            e.printStackTrace();
	            return null;
	        }
	        
	        
	        //Sauvegarde de l'image au format Base64 , pour pouvoir la stocker sous forme de string. car on ne peut pas stocker directement des Bitmaps
	        /*try{
		        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //Conversion du bitmap en string a l'aide de base64
		        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);   
		        byte[] b = baos.toByteArray(); 
		        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
	        	mEditor.putString("image_profil",encodedImage).commit(); //Sauvegarde l'image du profil sous forme de string
		    }catch(Error e){ //G�re les exception et erreurs et met une image "Img non disponible"
		    	
		    }
			catch(Exception e){
		    }
	        try {
				in.close(); //Ferme l'input Stream
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
	        return bitmapImage;
	    }
	    
	    //after downloading
	    protected void onPostExecute(Bitmap result) 
	    {	
	    	loadingSpinner.setVisibility(View.GONE);
	    	Log.v("Profil", "result " + result);
	    	if(ErreurImage || result == null )
	    	{
				TVImgIndisponible.setVisibility(View.VISIBLE);
	    	}
	    	else
	    	{
	    		imgProfil.setImageBitmap(result); //Attribut l'image au TextView
	    	}
	       
	       
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
