package codigo;
/*
 * Autores: Sergio Vilches - Enrique Amado
 */
import java.awt.Color;
import java.util.concurrent.BlockingQueue;

import javax.swing.JOptionPane;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;
import acmx.export.javax.swing.JLabel;
import java.applet.AudioClip;

public class Bola extends GOval{

	public static int vx = 0;
	public static int vy = 1;
	public int golpes = 0;
	public int vidas = 3;
	public int pause = 0;
	public static boolean muerete = false;

	public Bola(double width, double height) {
		super(width, height);
	}
	//creamos un segundo constructor
	/**
	 * 
	 * @param width : el ancho y alto de la bola
	 * @param color : el color de la bola
	 */
	public Bola(double width, Color color){
		super(width, width); //sólo van a ser círculos
		setFilled(true);
		setFillColor(color);
	}

	private void mueveBola(){
		if(Arkanoid.bola.getX() == 250 && Arkanoid.bola.getY() == 350 && pause == 0){
			pause(3000);
			pause++;
		}
		move(vx, vy);
	}

	public void chequeaRebote(Arkanoid arkanoid){
		mueveBola();
		if(this.getX() > arkanoid.getWidth() || this.getX() < 0){
			vx *= -1;
		}

		if(this.getY() > arkanoid.getHeight() || this.getY() < 0){
			vy *= -1;
		}

		//chequeo si la bola ha chocado con un cursor
		if(chequeaColision(getX(), getY(), arkanoid)){
			if(chequeaColision(getX() + getWidth() / 4, getY(), arkanoid)){
				if(chequeaColision(getX() + getWidth() / 2, getY(), arkanoid)){
					if(chequeaColision(getX() + getWidth() * (3/4), getY(), arkanoid)){
						if(chequeaColision(getX() + getWidth(), getY(), arkanoid)){
							if(chequeaColision(getX() , getY() + getHeight() / 4, arkanoid)){
								if(chequeaColision(getX() , getY() + getHeight() / 2, arkanoid)){
									if(chequeaColision(getX() , getY() + getHeight() * (3/4), arkanoid)){
										if(chequeaColision(getX() , getY() + getHeight(), arkanoid)){
											if(chequeaColision(getX() + getWidth() / 4 , getY() + getHeight(), arkanoid)){
												if(chequeaColision(getX() + getWidth() / 2 , getY() + getHeight(), arkanoid)){
													if(chequeaColision(getX() + getWidth() * (3/4), getY() + getHeight(), arkanoid)){
														if(chequeaColision(getX() + getWidth(), getY() + getHeight(), arkanoid)){
															if(chequeaColision(getX() + getWidth(), getY() + getHeight() / 4, arkanoid)){
																if(chequeaColision(getX() + getWidth(), getY() + getHeight() / 2, arkanoid)){
																	if(chequeaColision(getX() + getWidth(), getY() + getHeight() * (3/4), arkanoid)){

																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private boolean chequeaColision(double posX, double posY, Arkanoid arkanoid){
		boolean noHaChocado = true;
		GObject auxiliar = arkanoid.getElementAt(posX, posY);
		if(auxiliar instanceof Derecha){
			//si entra aquí es porque el objeto que está
			//en la posición posX, posY es de tipo cursor
			vy *= -1;
			if(vx == -1 || vx == -2){
				vx = -2;
			}
			else{ 
				vx = 2;
			}
			noHaChocado = false;
		}
		
		if(auxiliar instanceof Izquierda){
			//si entra aquí es porque el objeto que está
			//en la posición posX, posY es de tipo cursor
			vy *= -1;
			if(vx == 1 || vx == 2){
				vx = 2;
			}
			else {
				vx = -2;
			}
			noHaChocado = false;
		}
		
		if(auxiliar instanceof CentroDerecha){
			//si entra aquí es porque el objeto que está
			//en la posición posX, posY es de tipo cursor
			vy *= -1;
			if(vx == -1 || vx == -2){
				vx = -1;
			}
			else {
				vx = 1;
			}
			noHaChocado = false;
		}
		
		if(auxiliar instanceof CentroIzquierda){
			//si entra aquí es porque el objeto que está
			//en la posición posX, posY es de tipo cursor
			vy *= -1;
			if(vx == 1 || vx == 2){
				vx = 1;
			}
			else {
				vx = -1;
			}
			noHaChocado = false;
		}

		if(auxiliar instanceof Ladrillo){
			//si entra aquí es porque el objeto que está
			//en la posición posX, posY es de tipo ladrillo
			vy *= -1;
			vx *= vy;
			
			arkanoid.remove(auxiliar);
			arkanoid.remove(((Ladrillo) auxiliar).bloque2);
			arkanoid.remove(((Ladrillo) auxiliar).bloque3);
			golpes++;
			if(golpes > 0){
				arkanoid.remove(auxiliar);
				arkanoid.remove(((Ladrillo) auxiliar).bloque);
			}
			
			Arkanoid.puntuacion += 100;
			Arkanoid.marcador.setLabel(Arkanoid.puntuacion + " Puntos");
			Arkanoid.ladrillos--;
			noHaChocado = false;
		}
		
		if(auxiliar instanceof Booster){
			//si entra aquí es porque el objeto que está
			//en la posición posX, posY es de tipo ladrillo
			vy *= -1;
			vx *= vy;
			arkanoid.remove(auxiliar);
			arkanoid.remove(((Booster) auxiliar).bloque3);
			Arkanoid.puntuacion += 500;
			Arkanoid.marcador.setLabel(Arkanoid.puntuacion + " Puntos");
			//((Ladrillo) auxiliar).setFillColor(Color.PINK);
			Arkanoid.ladrillos--;
			noHaChocado = false;
		}

		if(auxiliar instanceof Limite){
			//si entra aquí es porque el objeto que está
			//en la posición posX, posY es de tipo limite
			vy = 0;
			vx = 0;
			noHaChocado = false;
			gameOver();
			if(vidas > 0){
				pause(2000);
				((arkanoid).bola).setLocation(250, 350);
				pause(2000);
				vy = 1;
				vx = 0;
			}
		}
		return noHaChocado;
	}

	public void gameOver() {
		vidas--;
		Arkanoid.conteovidas.setLabel("x " + vidas);
		if (vidas == 0){
			muerete = true;
			JOptionPane.showMessageDialog(null, "Puntuación Final: " + Arkanoid.puntuacion);
			vx = 0;
			vy = 0;
		}
	}
}
