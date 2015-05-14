package com.example.point_it_android;

import android.app.Activity;

/**
 * Classe représentant les éléments d'une liste
 */
public class ListItem 
{
	
	Activity context;
	private String text;
	private Boolean voirCommentaire;

	/**
     * Constructeur vide
     */
    public ListItem(){}
    
   
    
    /**
     * Constructeur pour un element texte 
     * @String text
     */
    public ListItem(String text)
    {
        this.text = text;
    }
    
    /**
     * Constructeur pour un element texte et boolean
     * @String text
     */
    public ListItem(String text , Boolean voirCommentaire )
    {
        this.text = text;
    }
    
    /**
     * Récupre le text de l'élément
     * @return
     */
    public String getText(){
        return this.text;
    }
    
    /**
     * Définit le text de l'élément
     * @param text
     */
    public void setText(String text){
        this.text = text;
    }
}
