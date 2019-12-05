package codigo;

import java.awt.Color;

import acm.graphics.GRect;

public class CursorCentI extends GRect{
	
	public CursorCentI(int posY, double ancho, double alto, Color color){
		super (ancho, alto);
		setFilled(true);
		setFillColor(color);
		setLocation(320, posY);

	}
}
