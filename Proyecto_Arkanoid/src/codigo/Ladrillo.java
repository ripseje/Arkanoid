package codigo;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRect;

/*
 * Autores: Sergio Vilches - Enrique Amado
 */
public class Ladrillo extends GRect{
	
	GImage bloque;
	GImage bloque2;
	GImage bloque3;
	
	public Ladrillo(int posX, int posY, double ancho, double alto, Color color){
		super(posX, posY, ancho, alto);
		setFilled(true);
		setFillColor(color);
		bloque = new GImage("imagenes/bloque.jpg");
		bloque.setSize(ancho-2, alto-2);
		
		bloque2 = new GImage("imagenes/bloque2.jpg");
		bloque2.setSize(ancho-2, alto-2);
		
		bloque3 = new GImage("imagenes/bloque3.jpg");
		bloque3.setSize(ancho-2, alto-2);
	}
}
