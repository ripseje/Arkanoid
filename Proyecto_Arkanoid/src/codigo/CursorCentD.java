package codigo;

import java.awt.Color;

import acm.graphics.GRect;

public class CursorCentD extends GRect{
	
	public CursorCentD(int posY, double ancho, double alto, Color color){
		super (ancho, alto);
		setFilled(true);
		setFillColor(color);
		setLocation(345, posY);

	}
}
