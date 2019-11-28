package codigo;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRect;

/*
 * Autor: Sergio Vilches
 * El cursor del juego Arkanoid
 */
public class Izquierda extends GRect{
	
	GImage cursor;
	/**
	 * 
	 * @param posY posición Y del cursor. La x siempre será 0(aparecerá a la izquierda de la pantalla
	 * @param ancho ancho del cursor
	 * @param alto alto del cursor
	 * @param color color del cursor
	 */
	public Izquierda(int posY, double ancho, double alto, Color color){
		super (ancho, alto);
		setFilled(true);
		setFillColor(color);
		setLocation(300, posY);
		cursor = new GImage("imagenes/cursor.jpg");
		cursor.setSize(ancho*4, alto);
		cursor.setLocation(300, posY);
		
	}
}
