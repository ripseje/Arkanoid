package codigo;
/*
 * Autores: Sergio Vilches - Enrique Amado
 */
import java.awt.Color;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

import javax.swing.JOptionPane;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import acmx.export.javax.swing.JLabel;
import java.applet.AudioClip;

public class Bola extends GOval{

	public static double vx = 0;
	public static double vy = 1;
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
		if(auxiliar instanceof Cursor){
			//si entra aquí es porque el objeto que está
			//en la posición posX, posY es de tipo cursor
			vy *= -1;
			if(vx == 0){
				vx = 1;
			}
			
			
			noHaChocado = false;
		}

		if(auxiliar instanceof Ladrillo){
			//si entra aquí es porque el objeto que está
			//en la posición posX, posY es de tipo ladrillo
			
			//si choca por la parte de abajo o por la parte de arriba de un larillo cambia la velocidad de la y
			if (auxiliar.getY() >= posY || auxiliar.getY() + auxiliar.getHeight() <= posY){
				vy *= -1;
			}
			//Si ha chocado por algun lateral, cambia la velocidad x
			else if(auxiliar.getX() == posX || auxiliar.getX() + auxiliar.getWidth() == posX){
				vx *= -1;
			}
			
			//Se borran el ladrillo y la imágen que lleve.
			arkanoid.remove(auxiliar);
			arkanoid.remove(((Ladrillo) auxiliar).bloque2);
			arkanoid.remove(((Ladrillo) auxiliar).bloque3);
			golpes++;
			if(golpes > 0){
				arkanoid.remove(auxiliar);
				arkanoid.remove(((Ladrillo) auxiliar).bloque);
			}
			
			//Al romper un ladrillo te da 100 puntos y baja un ladrillo
			//Devuelve que se ha chocado
			Arkanoid.puntuacion += 100;
			Arkanoid.marcador.setLabel(Arkanoid.puntuacion + " Puntos");
			Arkanoid.ladrillos--;
			noHaChocado = false;
		}
		
		if(auxiliar instanceof Booster){
			if(random(2, 7) == 4){
				vidas++;
				Arkanoid.conteovidas.setLabel("x " + vidas);
			}
			if(random(2, 7) == 5){
				Arkanoid.puntuacion += 400;
			}
			if(random(2, 7) == 6){
				Arkanoid.barreraActiva = true;
			}
			vy *= -1;
			vx *= vy;
			arkanoid.remove(auxiliar);
			arkanoid.remove(((Booster) auxiliar).bloque3);
			Arkanoid.puntuacion += 100;
			Arkanoid.marcador.setLabel(Arkanoid.puntuacion + " Puntos");
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
		
		if(auxiliar instanceof Barrera){
			//si entra aquí es porque el objeto que está
			//en la posición posX, posY es de tipo limite
			vy *= -1;
			arkanoid.remove(auxiliar);
			noHaChocado = false;
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
	
	public static int random(int min, int max){
		Random random = new Random();
		return random.nextInt((max - min) + 1) + min;
	}
}
