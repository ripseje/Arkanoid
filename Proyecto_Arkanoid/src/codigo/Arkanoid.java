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
	CursorLadI cursorIzq = new CursorLadI(600, 15, 10, Color.GRAY);
	CursorCentI cursorCIzq = new CursorCentI(600, 15, 10, Color.GRAY);
	CursorLadD cursorDer = new CursorLadD(600, 15, 10, Color.GRAY);
	CursorCentD cursorCDer= new CursorCentD(600, 15, 10, Color.GRAY);
	CursorCentro cursorCent = new CursorCentro(600, 15, 10, Color.GRAY);
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
		//Añadimos la imagen de fondo, que es un gif
		fondo = new GImage("imagenes/fondo.gif");
		add(fondo);
		//Añadimos todas las partes del cursor y su imágen
		add(cursorIzq);
		add(cursorCIzq);
		add(cursorCent);
		add(cursorCDer);
		add(cursorDer);
		add(cursorIzq.cursor);
		//Añadimos el límite, que es dónde la bola se va a morir
		add(limite);
		//Añadimos el marcador de puntos arriba a la izquierda con la fuente Comic Sans color blanco
		add(marcador, 20, 20);
		marcador.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		marcador.setColor(Color.WHITE);
		//Añadimos el contador de vidas en comic sans color blanco y la ímagen del corazón
		corazon = new GImage("imagenes/corazon.png");
		add(corazon, 375, 6);
		add(conteovidas, 400, 20);
		conteovidas.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		conteovidas.setColor(Color.WHITE);
		//Añadimos la imágen de inicio y la mandamos delante del todo, la cual tendremos que pulsar para empezar la partida
		inicioI = new GImage("imagenes/inicio.gif");
		add(inicioI);
		inicioI.sendToFront();
	}

	public void run(){
		while(true){
			pause(1);
			cursorIzq.setLocation(bola.getX(), cursorIzq.getY());
			cursorCIzq.setLocation(bola.getX()+15, cursorCIzq.getY());
			cursorCent.setLocation(bola.getX()+30, cursorCent.getY());
			cursorCDer.setLocation(bola.getX()+45, cursorCDer.getY());
			cursorDer.setLocation(bola.getX()+60, cursorDer.getY());
			cursorIzq.cursor.setLocation(bola.getX()+1, cursorIzq.getY()+1);
			//Si el booleano barreraActiva es true se activará este booster, el cual hace que la bola 
			//no pueda irse del campo hasta dar con este booster
			//una sola vez
			boosterBarrera();
			//Chequeamos el rebote
			bola.chequeaRebote(this);
			//Si nos quedamos a 0 vidas Bola.muerete será true y se cargará muerte();
			if(Bola.muerete == true){
				muerte();
			}
			//Se inicia el segundo nivel al romper todas los ladrillos del primer nivel
			if(nivel == 1 && !(cuentabolas == 0)){
				cambiaSegundoNivel();
			}
			//Se inicia el tercer nivel al romper todas los ladrillos del segundo nivel
			if(nivel == 2 && !(cuentabolas == 0)){
				cambiaTercerNivel();
			}
			//Si acabas con todos los ladrillos del tercer nivel se cargará la pantalla de victoria
			if(nivel == 3 && !(cuentabolas == 0)){
				victoria();
			}
		}
	}

	public void mouseMoved(MouseEvent evento){
		cursorIzq.setLocation(evento.getX(), cursorIzq.getY());
		cursorCIzq.setLocation(evento.getX()+15, cursorCIzq.getY());
		cursorCent.setLocation(evento.getX()+30, cursorCent.getY());
		cursorCDer.setLocation(evento.getX()+45, cursorCDer.getY());
		cursorDer.setLocation(evento.getX()+60, cursorDer.getY());
		cursorIzq.cursor.setLocation(evento.getX()+1, cursorIzq.getY()+1);
	}

	public void keyPressed(KeyEvent evento){
		//Al presionar una tecla se iniciará la partida
		if(cuentabolas == 0){
			add(bola, 250, 350);
			remove(inicioI);
			if(nivel == 0){
				//Se crea el primer nivel
				creaPrimerNivel();
			}
		}
		//Añado estos contadores para que solo funcione una vez el KeyPressed
		cuentabolas++;
		nivel++;
	}

	public void muerte(){
		//Al morir se borra la bola y se añaden la imagen negra del fondo y encima 
		//un game over. Además se carga un has perdido con tu puntuación en la clase Bola.
		remove(bola);
		nigga = new GImage("imagenes/nigga.jpg"); 
		add(nigga);
		muerte = new GImage("imagenes/muerte.gif");
		add(muerte);
	}

	public void cambiaSegundoNivel(){
		//Cuando se han roto todos los ladrillos del nivel 1 se cambian el fondo y 
		// la bola y se crea el segundo nivel. Se aumenta el contador de nivel
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
		//Cuando se han roto todos los ladrillos del nivel 2 se cambian el fondo y 
		// la bola y se crea el tercer nivel. Se aumenta el contador de nivel
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
		//Crea la barrera si barreraActiva es igual a true y la cambia a false para que no cree
		// barreras infinitas
		if(barreraActiva){
		Barrera barrera = new Barrera(0, 630, 500, 20);
		add(barrera);
		barrera.setFilled(true);
		barrera.setFillColor(Color.GRAY);
		barreraActiva = false;
		}
	}

	public void victoria(){
		//Si se termina el tercer nivel se eliminan la bola y el fondo y se carga la imágen de victoria. Además
		// sale un mensaje de la cantidad de puntos finales. Añade un ladrillo para que no se genere nada más.
		if(ladrillos == 0){
			remove(bola);
			remove(fondo3);
			royale = new GImage("imagenes/fondovictoria.jpg");
			add(royale);
			royale.sendToFront();
			JOptionPane.showMessageDialog(null, "Puntuación Final: " + Arkanoid.puntuacion + " puntos");
			ladrillos++;
		}
	}
	public void creaTercerNivel(){
		//Nivel que crea tres pirámides inversas. Una grande blanca, una un poco más pequeña y naranja encima de la blanca
		// y dos de boosters pequeñitas a los lados. Cada creación de ladrillos tiene un pause(10) para que se vea
		// como se crea y que no haya un impacto muy fuerte al cambiar de nivel
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
		//Nivel que crea una X grande y una I o un palo lo mismo es que lo mismo da.
		//En el centro de la X hay un booster y hay repartidos en la I y en la X varios
		// ladrillos naranjas
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
		//Te crea un marcianito del space invaders de dos capas, una de ladrillos blancos y una encima de ladrillos naranjas.
		//Además hay un booster en el ojo izquierdo del marcianito. Cada creación de ladrillos tiene un pause(10) para que se vea
		// como se crea y que no haya un impacto muy fuerte al cambiar de nivel
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
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+449 + ANCHO_LADRILLO * -j - ANCHO_LADRILLO /2;
			int posicionY = ALTO_LADRILLO * j + ALTO_LADRILLO;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos3; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j;
			int posicionY = ALTO_LADRILLO + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j - ANCHO_LADRILLO;
			int posicionY = ALTO_LADRILLO*2 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2+1; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO*2;
			int posicionY = ALTO_LADRILLO*2 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO*6;
			int posicionY = ALTO_LADRILLO*2 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos4; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j - ANCHO_LADRILLO*2;
			int posicionY = ALTO_LADRILLO*3 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO;
			int posicionY = ALTO_LADRILLO*6 + ALTO_LADRILLO * j;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO*11;
			int posicionY = ALTO_LADRILLO*6 + ALTO_LADRILLO * j;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos3; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j;
			int posicionY = ALTO_LADRILLO * 4 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2-1; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j;
			int posicionY = ALTO_LADRILLO*5 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2-1; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO*6;
			int posicionY = ALTO_LADRILLO*5 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO;
			int posicionY = ALTO_LADRILLO*6 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO*4;
			int posicionY = ALTO_LADRILLO*6 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+29 + ANCHO_LADRILLO * j - ANCHO_LADRILLO /2;
			int posicionY = ALTO_LADRILLO * j + ALTO_LADRILLO;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+449 + ANCHO_LADRILLO * -j - ANCHO_LADRILLO /2;
			int posicionY = ALTO_LADRILLO * j + ALTO_LADRILLO;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos3; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j;
			int posicionY = ALTO_LADRILLO + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j - ANCHO_LADRILLO;
			int posicionY = ALTO_LADRILLO*2 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2+1; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO*2;
			int posicionY = ALTO_LADRILLO*2 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO*6;
			int posicionY = ALTO_LADRILLO*2 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos4; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j - ANCHO_LADRILLO*2;
			int posicionY = ALTO_LADRILLO*3 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO;
			int posicionY = ALTO_LADRILLO*6 + ALTO_LADRILLO * j;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO*11;
			int posicionY = ALTO_LADRILLO*6 + ALTO_LADRILLO * j;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos3; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j;
			int posicionY = ALTO_LADRILLO * 4 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2-1; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j;
			int posicionY = ALTO_LADRILLO*5 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2-1; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO*6;
			int posicionY = ALTO_LADRILLO*5 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO;
			int posicionY = ALTO_LADRILLO*6 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO*4;
			int posicionY = ALTO_LADRILLO*6 + ALTO_LADRILLO + 60;
			Ladrillo miLadrillo = new Ladrillo(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(miLadrillo);
			add(miLadrillo.bloque2, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
		for(int j=3; j<numeroLadrillos2-1; j++){
			int posicionX = desplazamientoInicial+12 + ANCHO_LADRILLO * j + ANCHO_LADRILLO;
			int posicionY = ALTO_LADRILLO*2 + ALTO_LADRILLO + 60;
			Booster booster = new Booster(posicionX, posicionY, ANCHO_LADRILLO, ALTO_LADRILLO, Color.DARK_GRAY);
			add(booster);
			add(booster.bloque3, posicionX+1, posicionY+1);
			ladrillos++;
			pause(10);
		}
	}
}
