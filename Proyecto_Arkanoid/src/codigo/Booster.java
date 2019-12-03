package codigo;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRect;

/*
 * Autores: Sergio Vilches - Enrique Amado
 */
public class Booster extends GRect{
	
	GImage bloque3;
	
	public int golpes = 0;
	
	public Booster(int posX, int posY, double ancho, double alto, Color color){
		super(posX, posY, ancho, alto);
		setFilled(true);
		setFillColor(color);
		bloque3 = new GImage("imagenes/bloque3.jpg");
		bloque3.setSize(ancho-2, alto-2);
	}
}