package codigo;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRect;

/*
 * Autor: Sergio Vilches
 * El cursor del juego Arkanoid
 */
public class CentroIzquierda extends GRect{
	/**
	 * 
	 * @param posY posici�n Y del cursor. La x siempre ser� 0(aparecer� a la izquierda de la pantalla
	 * @param ancho ancho del cursor
	 * @param alto alto del cursor
	 * @param color color del cursor
	 */
	public CentroIzquierda(int posY, double ancho, double alto, Color color){
		super (ancho, alto);
		setFilled(true);
		setFillColor(color);
		setLocation(300, posY);		
	}
}
