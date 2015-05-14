package com.example.point_it_android;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomListViewAdapter extends BaseAdapter 
{

	List<ListItem> items;
	Activity context;
	
	/**
     * Constructeur de la classe
     * @param context
     * @param items
     */
   public CustomListViewAdapter(Activity context, List<ListItem> items) 
   {
       this.context = context;
       this.items = items;
   }
	
   /**
    * R�cupre le nombre d'�l�ment de la liste
    */
	public int getCount() {
		return items.size();
	}

	 /**
	  * Renvoie l'item en fonction de sa position
	  */
	public Object getItem(int position) {
		return items.get(position);
	}

	/**
	 * Renvoie la position de l'item
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * R�cup�re la vue et attribut les elements de la liste
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) 
		{
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_point_custom_view, null); 
        }
		
		//Cr�er dynamiquement les image iew en fonction du nombre de personne � qui est attribuer le point
		
		TextView txtText = (TextView) convertView.findViewById(R.id.TextViewDescriptionPoint);
		txtText.setText(items.get(position).getText()); //D�fini le texte
		
		
		
		
		//Bouton approuver
		
		//Bouton commentaire ...
		
		return convertView;
	}

}
