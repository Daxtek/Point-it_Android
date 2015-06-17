package com.example.point_it_android;

import android.app.Activity;

/**
 * Classe représentant les éléments d'une liste
 */
public class ListItem 
{
	
	Activity context;
	private String text;
	private String typeDePoint;
	private String AttribueA;
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
     * Constructeur pour un element texte et le type de point
     * @String text
     */
    public ListItem(String text , String typeDePoint , String AttribueA )
    {
        this.text = text;
        this.typeDePoint = typeDePoint;
        this.AttribueA = AttribueA;
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
    
    /**
     * Récupre le type de point
     * @return
     */
    public String getTypeDePoint(){
        return this.typeDePoint;
    }
    
    /**
     * Définit le type de point
     * @param text
     */
    public void setTypeDePoint(String typeDePoint){
        this.typeDePoint = typeDePoint;
    }
    
    /**
     * Récupre la personne a qui le point est attribué
     * @return
     */
    public String getAttribueA(){
        return this.AttribueA;
    }
    
    /**
     * Définit le type de point
     * @param text
     */
    public void setAttribueA(String AttribueA){
        this.AttribueA = AttribueA;
    }
}
