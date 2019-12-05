package codigo;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRect;

/*
 * Autor: Sergio Vilches y Enrique Amado
 * El cursor del juego Arkanoid
 */
public class CursorLadI extends GRect{
	
	GImage cursor;
	/**
	 * 
	 * @param posY posición Y del cursor. La x siempre será 300(aparecerá en el centro de la pantalla)
	 * @param ancho ancho del cursor
	 * @param alto alto del cursor
	 * @param color color del cursor
	 * @param viene con una imágen ya precolocada
	 */
	public CursorLadI(int posY, double ancho, double alto, Color color){
		super (ancho, alto);
		setFilled(true);
		setFillColor(color);
		setLocation(300, posY);
		cursor = new GImage("imagenes/cursor.jpg");
		cursor.setSize(ancho*4, alto);
		cursor.setLocation(300, posY);
		
	}
}
