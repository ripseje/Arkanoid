package codigo;
/*
 * Autores: Sergio Vilches - Enrique Amado
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class Arkanoid extends GraphicsProgram{

	private static final long serialVersionUID = 1L;
	static int ANCHO_LADRILLO = 35;
	static int ALTO_LADRILLO = 15;
	static Bola bola = new Bola(10, Color.CYAN);
	Izquierda cursorI = new Izquierda(600, 20, 10, Color.GRAY);
	Derecha cursorD = new Derecha(600, 20, 10, Color.GRAY);
	CentroIzquierda cursorCI = new CentroIzquierda(600, 20, 10, Color.GRAY);
	CentroDerecha cursorCD = new CentroDerecha(600, 20, 10, Color.GRAY);
	Limite limite = new Limite(480, 1000, 2);
	GImage fondo;
	GImage fondo2;
	GImage fondo3;
	GImage royale;
	GImage muerte;
	GImage nigga;
	GImage corazon;
	GImage inicioI;
	public static boolean barreraActiva = false;
	static GLabel marcador = new GLabel("0" + " Puntos");
	static GLabel conteovidas = new GLabel("x " + "3");
	static int puntuacion = 0;
	public int inicio = 0;
	public int cuentabolas = 0;
	public int nivel = 0;
	public static int ladrillos = 0;


	public void init(){
		setSize(500, 700);
		addMouseListeners();
		addKeyListeners();
		//SoundTest.sonidoFondo();
		fondo = new GImage("imagenes/fondo.gif");
		add(fondo);
		add(cursorD);
		add(cursorI);
		add(cursorCI);
		add(cursorCD);
		add(cursorI.cursor);
		add(limite);
		add(marcador, 20, 20);
		marcador.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		marcador.setColor(Color.WHITE);
		corazon = new GImage("imagenes/corazon.png");
		add(corazon, 375, 6);
		add(conteovidas, 400, 20);
		conteovidas.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		conteovidas.setColor(Color.WHITE);
		inicioI = new GImage("imagenes/inicio.gif");
		add(inicioI);
		inicioI.sendToFront();
	}

	public void run(){
		while(true){
			pause(3);
			boosterBarrera();
			bola.chequeaRebote(this);
			if(Bola.muerete == true){
				muerte();
			}
			if(nivel == 1 && !(cuentabolas == 0)){
				cambiaSegundoNivel();
			}
			if(nivel == 2 && !(cuentabolas == 0)){
				cambiaTercerNivel();
			}
			if(nivel == 3 && !(cuentabolas == 0)){
				victoria();
			}
		}
	}

	public void mouseMoved(MouseEvent evento){
		cursorI.setLocation(evento.getX(), cursorI.getY());
		cursorI.cursor.setLocation(evento.getX()+1, cursorI.getY()+1);
		cursorCI.setLocation(evento.getX()+20, cursorI.getY());
		cursorCD.setLocation(evento.getX()+40, cursorI.getY());
		cursorD.setLocation(evento.getX()+60, cursorI.getY());
	}

	public void keyPressed(KeyEvent evento){
		if(cuentabolas == 0){
			add(bola, 250, 350);
			remove(inicioI);
			if(nivel == 0){
				creaPrimerNivel();
			}
		}
		cuentabolas++;
		nivel++;
	}

	public void muerte(){
		remove(bola);
		nigga = new GImage("imagenes/nigga.jpg"); 
		add(nigga);
		muerte = new GImage("imagenes/muerte.gif");
		add(muerte);
	}

	public void cambiaSegundoNivel(){
		if(ladrillos == 0){
			remove(bola);
			remove(fondo);
			fondo2 = new GImage("imagenes/fondo3.gif"); 
			add(fondo2);
			fondo2.sendToBack();
			add(bola, 250, 350);
			Bola.vx = 0;
			Bola.vy = 1;
			creaSegundoNivel();
			nivel++;
		}
	}

	public void cambiaTercerNivel(){
		if(ladrillos == 0){
			remove(bola);
			remove(fondo2);
			fondo3 = new GImage("imagenes/fondo4.gif"); 
			add(fondo3);
			fondo3.sendToBack();
			add(bola, 250, 350);
			Bola.vx = 0;
			Bola.vy = 1;
			creaTercerNivel();
			nivel++;
		}
	}
	
	public void boosterBarrera(){
		if(barreraActiva){
		Barrera barrera = new Barrera(0, 630, 500, 20);
		add(barrera);
		barrera.setFilled(true);
		barrera.setFillColor(Color.GRAY);
		barreraActiva = false;
		}
	}

	public void victoria(){
		if(ladrillos == 0){
			remove(bola);
			remove(fondo2);
			royale = new GImage("imagenes/fondovictoria.jpg");
			add(royale);
			royale.sendToFront();
			JOptionPane.showMessageDialog(null, "Puntuación Final: " + Arkanoid.puntuacion + " puntos" + " ¡FELICIDADES HAS GANADO!");
			ladrillos++;
		}
	}
	public void creaTercerNivel(){
		int numeroLadrillos = 14;
		int numeroLadrillos2 = 10;
		int numeroLadrillos3 = 6;
		int desplazamientoInicial = (getWidth() - numeroLadrillos * ANCHO_LADRILLO) /2;
		int desplazamientoInicial2 = (getWidth() - numeroLadrillos2 * ANCHO_LADRILLO) /2;
		int desplazamientoInicial3 = (getWidth() - 16 * ANCHO_LADRILLO) /2;
		int desplazamientoInicial4 = (getWidth() - -4 * ANCHO_LADRILLO) /2;
		for(int j=3; j<numeroLadrillos; j++){
			for (int i=j; i<numeroLadrillos; i++){
				int posicionX = desplazamientoInicial + ANCHO_LADRILLO * i - ANCHO_LADRILLO /2 * j;
				int posicionY = ALTO_LADRILLO * j + ALTO_LADRILLO;
				Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
				add(miLadrillo);
				add(miLadrillo.bloque, posicionX+1, posicionY+1);
				ladrillos++;
				pause(10);
			}
		}
		for(int j=3; j<numeroLadrillos2; j++){
			for (int i=j; i<numeroLadrillos2; i++){
				int posicionX = desplazamientoInicial2 + ANCHO_LADRILLO * i - ANCHO_LADRILLO /2 * j;
				int posicionY = ALTO_LADRILLO * j + ALTO_LADRILLO;
				Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
				add(miLadrillo);
				add(miLadrillo.bloque2, posicionX+1, posicionY+1);
				ladrillos++;
				pause(10);
			}
		}
		for(int j=3; j<numeroLadrillos3; j++){
			for (int i=j; i<numeroLadrillos3; i++){
				int posicionX = desplazamientoInicial3 + ANCHO_LADRILLO * i - ANCHO_LADRILLO /2 * j;
				int posicionY = ALTO_LADRILLO * j + 150;
				Booster booster = new Booster(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
				add(booster);
				add(booster.bloque3, posicionX+1, posicionY+1);
				ladrillos++;
				pause(10);
			}
		}
		for(int j=3; j<numeroLadrillos3; j++){
			for (int i=j; i<numeroLadrillos3; i++){
				int posicionX = desplazamientoInicial4 + ANCHO_LADRILLO * i - ANCHO_LADRILLO /2 * j;
				int posicionY = ALTO_LADRILLO * j + 150;
				Booster booster = new Booster(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
				add(booster);
				add(booster.bloque3, posicionX+1, posicionY+1);
				ladrillos++;
				pause(10);
			}
		}
	}
	public void creaPrimerNivel(){
		int numeroLadrillos1 = 9;
		int numeroLadrillos2 = 3;
		int numeroLadrillos3 = 16;
		int desplazamientoInicial = (getWidth() - numeroLadrillos1-15 * ANCHO_LADRILLO) /2;
		int desplazamientoInicial2 = (getWidth() - numeroLadrillos1 * ANCHO_LADRILLO) /2;
		for(int j=3; j<numeroLadrillos1; j++){
			int posicionX = desplazamientoInicial + ANCHO_LADRILLO * j - ANCHO_LADRILLO /2 * j;
			int posicionY = ALTO_LADRILLO * j + ALTO_LADRILLO;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos1; j++){
			int posicionX = desplazamientoInicial+324 + ANCHO_LADRILLO * -j - ANCHO_LADRILLO /2 * -j;
			int posicionY = ALTO_LADRILLO * j + ALTO_LADRILLO;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos1; j++){
			int posicionX = desplazamientoInicial+324 + ANCHO_LADRILLO * -j - ANCHO_LADRILLO /2 * -j;
			int posicionY = ALTO_LADRILLO * j + ALTO_LADRILLO;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos1; j++){
			int posicionX = desplazamientoInicial+198 + ANCHO_LADRILLO * -j - ANCHO_LADRILLO /2 * -j;
			int posicionY = ALTO_LADRILLO * j + 105+ALTO_LADRILLO;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos1; j++){
			int posicionX = desplazamientoInicial+198 + ANCHO_LADRILLO * -j - ANCHO_LADRILLO /2 * -j;
			int posicionY = ALTO_LADRILLO * j + 105+ALTO_LADRILLO;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos1; j++){
			int posicionX = desplazamientoInicial+126 + ANCHO_LADRILLO * j - ANCHO_LADRILLO /2 * j;
			int posicionY = ALTO_LADRILLO * j + 105+ALTO_LADRILLO;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=2; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+126 + ANCHO_LADRILLO * j - ANCHO_LADRILLO /2 * j;
			int posicionY = ALTO_LADRILLO * j + 105+ALTO_LADRILLO;
			Booster booster = new Booster(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(booster);
			add(booster.bloque3, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos3; j++){
			int posicionX = desplazamientoInicial2+274;
			int posicionY = ALTO_LADRILLO * j + ALTO_LADRILLO;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=2; j<numeroLadrillos1; j++){
			int posicionX = desplazamientoInicial2+274;
			int posicionY = (ALTO_LADRILLO * j)*2;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
		}
	}
	public void creaSegundoNivel(){
		int numeroLadrillos1 = 9;
		int numeroLadrillos2 = 5;
		int numeroLadrillos3 = 10;
		int numeroLadrillos4 = 14;
		int desplazamientoInicial = (getWidth() - numeroLadrillos1-13 * ANCHO_LADRILLO) /2;
		int desplazamientoInicial2 = (getWidth() - numeroLadrillos1 * ANCHO_LADRILLO) /2;
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+29 + ANCHO_LADRILLO * j - ANCHO_LADRILLO /2;
			int posicionY = ALTO_LADRILLO * j + ALTO_LADRILLO;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+449 + ANCHO_LADRILLO * -j - ANCHO_LADRILLO /2;
			int posicionY = ALTO_LADRILLO * j + ALTO_LADRILLO;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos3; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j;
			int posicionY = ALTO_LADRILLO + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j - ANCHO_LADRILLO;
			int posicionY = ALTO_LADRILLO*2 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2+1; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO*2;
			int posicionY = ALTO_LADRILLO*2 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO*6;
			int posicionY = ALTO_LADRILLO*2 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos4; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j - ANCHO_LADRILLO*2;
			int posicionY = ALTO_LADRILLO*3 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO;
			int posicionY = ALTO_LADRILLO*6 + ALTO_LADRILLO * j;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO*11;
			int posicionY = ALTO_LADRILLO*6 + ALTO_LADRILLO * j;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos3; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j;
			int posicionY = ALTO_LADRILLO * 4 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2-1; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j;
			int posicionY = ALTO_LADRILLO*5 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2-1; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO*6;
			int posicionY = ALTO_LADRILLO*5 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO;
			int posicionY = ALTO_LADRILLO*6 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO*4;
			int posicionY = ALTO_LADRILLO*6 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+29 + ANCHO_LADRILLO * j - ANCHO_LADRILLO /2;
			int posicionY = ALTO_LADRILLO * j + ALTO_LADRILLO;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+449 + ANCHO_LADRILLO * -j - ANCHO_LADRILLO /2;
			int posicionY = ALTO_LADRILLO * j + ALTO_LADRILLO;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos3; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j;
			int posicionY = ALTO_LADRILLO + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j - ANCHO_LADRILLO;
			int posicionY = ALTO_LADRILLO*2 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2+1; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO*2;
			int posicionY = ALTO_LADRILLO*2 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO*6;
			int posicionY = ALTO_LADRILLO*2 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos4; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j - ANCHO_LADRILLO*2;
			int posicionY = ALTO_LADRILLO*3 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO;
			int posicionY = ALTO_LADRILLO*6 + ALTO_LADRILLO * j;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO*11;
			int posicionY = ALTO_LADRILLO*6 + ALTO_LADRILLO * j;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos3; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j;
			int posicionY = ALTO_LADRILLO * 4 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2-1; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j;
			int posicionY = ALTO_LADRILLO*5 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2-1; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO*6;
			int posicionY = ALTO_LADRILLO*5 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO;
			int posicionY = ALTO_LADRILLO*6 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO*4;
			int posicionY = ALTO_LADRILLO*6 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
		}
		for(int j=3; j<numeroLadrillos2-1; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO;
			int posicionY = ALTO_LADRILLO*2 + ALTO_LADRILLO + 60;
			Booster booster = new Booster(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(booster);
			add(booster.bloque3, posicionX+1, posicionY+1);
			ladrillos++;
		}
	}
}
