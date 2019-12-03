package codigo;

import java.awt.Color;

import acm.graphics.GRect;

public class Barrera extends GRect{

	public Barrera(double x, double y, double width, double height) {
		super(x, y, width, height);
		setColor(Color.GRAY);
	}
	
}