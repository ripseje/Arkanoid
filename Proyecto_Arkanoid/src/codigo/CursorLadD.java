package codigo;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRect;

/*
 * Autor: Sergio Vilches y Enrique Amado
 * El cursor del juego Arkanoid
 */
public class CursorLadD extends GRect{
	
	GImage cursor;
	/**
	 * 
	 * @param posY posici�n Y del cursor. La x siempre ser� 300(aparecer� en el centro de la pantalla)
	 * @param ancho ancho del cursor
	 * @param alto alto del cursor
	 * @param color color del cursor
	 * @param viene con una im�gen ya precolocada
	 */
	public CursorLadD(int posY, double ancho, double alto, Color color){
		super (ancho, alto);
		setFilled(true);
		setFillColor(color);
		setLocation(300, posY);		
	}
}
