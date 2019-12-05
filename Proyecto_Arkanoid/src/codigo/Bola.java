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
		//Este if hace que cuando se va a empezar la partida la pelota tarde en empezar a moverse para que 
		// de tiempo al jugador a verla y no perder una vida a lo tonto
		if(Arkanoid.bola.getX() == 250 && Arkanoid.bola.getY() == 350 && pause == 0){
			pause(3000);
			pause++;
		}
		//La bola se mueve vx = velocidad de la x && vy = a velocidad de la y
		move(vx, vy);
	}

	public void chequeaRebote(Arkanoid arkanoid){
		mueveBola();
		//Para que rebote con el marco del programa
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
		if(auxiliar instanceof CursorLadI){
			//si entra aquí es porque el objeto que está
			//en la posición posX, posY es de tipo CursorLadI, el extremo izquierdo del cursor
			if(vx == 0){
				vx = -2;
			}
			if(vx == 2 || vx == 1){
				vx = 1;
			}
			if(vx == -2 || vx == -1){
				vx = -2;
			}
			
			vy *= -1;
			
			//Devueve falso
			noHaChocado = false;
		}
		
		if(auxiliar instanceof CursorCentI){
			//si entra aquí es porque el objeto que está
			//en la posición posX, posY es de tipo CursorCentI, la zona izquierda del centro del cursor
			if(vx == 0){
				vx = -1;
			}
			if(vx == 2 || vx == 1){
				vx = 1;
			}
			if(vx == -2 || vx == -1){
				vx = -2;
			}
			
			vy *= -1;
			
			//Devueve falso
			noHaChocado = false;
		}
		
		if(auxiliar instanceof CursorCentro){
			//si entra aquí es porque el objeto que está
			//en la posición posX, posY es de tipo CursorCentro, el centro del cursor
			if(vx == 0){
				vx = 1;
			}
			if(vx == 2 || vx == 1){
				vx = 1;
			}
			if(vx == -2 || vx == -1){
				vx = -1;
			}
			
			vy *= -1;
			
			//Devueve falso
			noHaChocado = false;
		}
		
		if(auxiliar instanceof CursorLadD){
			//si entra aquí es porque el objeto que está
			//en la posición posX, posY es de tipo CursorLadD, la zona derecha del centro del cursor
			if(vx == 0){
				vx = 2;
			}
			if(vx == 2 || vx == 1){
				vx = 2;
			}
			if(vx == -2 || vx == -1){
				vx = -1;
			}
			
			vy *= -1;
			
			//Devueve falso
			noHaChocado = false;
		}
		
		if(auxiliar instanceof CursorCentD){
			//si entra aquí es porque el objeto que está
			//en la posición posX, posY es de tipo CursorCentD, el extremo derecho del cursor
			if(vx == 0){
				vx = 1;
			}
			if(vx == 2 || vx == 1){
				vx = 1;
			}
			if(vx == -2 || vx == -1){
				vx = -2;
			}
			
			vy *= -1;
			
			//Devueve falso
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
			//Para que elimine bien los ladrillos que están encima de los otros
			golpes++;
			if(golpes > 0){
				arkanoid.remove(auxiliar);
				arkanoid.remove(((Ladrillo) auxiliar).bloque);
			}
			
			//Al romper un ladrillo te da 100 puntos y baja un ladrillo del contador
			//Devuelve que se ha chocado
			Arkanoid.puntuacion += 100;
			Arkanoid.marcador.setLabel(Arkanoid.puntuacion + " Puntos");
			Arkanoid.ladrillos--;
			//Devueve falso
			noHaChocado = false;
		}
		
		if(auxiliar instanceof Booster){
			//Realiza un Random para ver que booster te da. Si no te da ninguno de
			// los dos primeros boosters te da un bonus de puntos
			
			//Este te da más vidas y las aumenta al contador de vidas
			if(random(2, 7) == 4){
				vidas++;
				Arkanoid.conteovidas.setLabel("x " + vidas);
			}
			else{
				if(random(2, 7) == 6){
					//Este activa la barrera para que no se te vaya la pelota
					Arkanoid.barreraActiva = true;
				}
				else{
					//Este da bonus de puntos
					Arkanoid.puntuacion += 400;
				}
			}
			
			//si choca por la parte de abajo o por la parte de arriba de un larillo cambia la velocidad de la y
			if (auxiliar.getY() >= posY || auxiliar.getY() + auxiliar.getHeight() <= posY){
				vy *= -1;
			}
			//Si ha chocado por algun lateral, cambia la velocidad x
			else if(auxiliar.getX() == posX || auxiliar.getX() + auxiliar.getWidth() == posX){
				vx *= -1;
			}
			//Elimina el ladrillo y su imágen, da 100 puntos y baja un ladrillo del contador
			arkanoid.remove(auxiliar);
			arkanoid.remove(((Booster) auxiliar).bloque3);
			Arkanoid.puntuacion += 100;
			Arkanoid.marcador.setLabel(Arkanoid.puntuacion + " Puntos");
			Arkanoid.ladrillos--;
			//Devueve falso
			noHaChocado = false;
		}

		if(auxiliar instanceof Limite){
			//si entra aquí es porque el objeto que está
			//en la posición posX, posY es de tipo Limite
			
			//Si lo toca la velocidad de ambos puntos será cero
			vy = 0;
			vx = 0;
			
			//Carga game over, el cual solo funciona si vidas == 0
			gameOver();
			
			//Si hay más de 0 vidas volverá a aperecer una pelota en el punto de spawn
			if(vidas > 0){
				pause(2000);
				((arkanoid).bola).setLocation(250, 350);
				pause(2000);
				vy = 1;
				vx = 0;
			}
			//Devuelve falso
			noHaChocado = false;
		}
		
		if(auxiliar instanceof Barrera){
			//si entra aquí es porque el objeto que está
			//en la posición posX, posY es de tipo Barrera
			
			//Rebota directamente para arriba y elimina el booster
			vy *= -1;
			arkanoid.remove(auxiliar);
			//Devuelve falso
			noHaChocado = false;
		}
		//Devuelve noHaChocado
		return noHaChocado;
	}

	public void gameOver() {
		//Te quita una vida y lo devuelve en el contador de vidas
		vidas--;
		Arkanoid.conteovidas.setLabel("x " + vidas);
		
		//Si vidas es == 0 devuelve muerete como true y te muestra el mensaje de puntuación final
		//Además pone vx && vy a 0
		if (vidas == 0){
			muerete = true;
			JOptionPane.showMessageDialog(null, "Puntuación Final: " + Arkanoid.puntuacion);
			vx = 0;
			vy = 0;
		}
	}
	
	public static int random(int min, int max){
		//Genera la operacón del random
		Random random = new Random();
		return random.nextInt((max - min) + 1) + min;
	}
}
