package codigo;

import java.awt.Color;

import acm.graphics.GRect;

public class CursorCentro extends GRect{
	
	public CursorCentro(int posY, double ancho, double alto, Color color){
		super (ancho, alto);
		setFilled(true);
		setFillColor(color);
		setLocation(330, posY);

	}
}
