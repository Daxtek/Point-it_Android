package com.example.point_it_android;

import android.app.Activity;

/**
 * Classe repr�sentant les �l�ments d'une liste
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
     * R�cupre le text de l'�l�ment
     * @return
     */
    public String getText(){
        return this.text;
    }
    
    /**
     * D�finit le text de l'�l�ment
     * @param text
     */
    public void setText(String text){
        this.text = text;
    }
}
