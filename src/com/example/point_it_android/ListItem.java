package com.example.point_it_android;

import android.app.Activity;

/**
 * Classe repr�sentant les �l�ments d'une liste
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
    
    /**
     * R�cupre le type de point
     * @return
     */
    public String getTypeDePoint(){
        return this.typeDePoint;
    }
    
    /**
     * D�finit le type de point
     * @param text
     */
    public void setTypeDePoint(String typeDePoint){
        this.typeDePoint = typeDePoint;
    }
    
    /**
     * R�cupre la personne a qui le point est attribu�
     * @return
     */
    public String getAttribueA(){
        return this.AttribueA;
    }
    
    /**
     * D�finit le type de point
     * @param text
     */
    public void setAttribueA(String AttribueA){
        this.AttribueA = AttribueA;
    }
}
