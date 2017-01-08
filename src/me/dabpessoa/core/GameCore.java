package me.dabpessoa.core;

import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import me.dabpessoa.game.LoopSteps;
import me.dabpessoa.game.MainLoop;
import me.dabpessoa.map.TileMap;
import me.dabpessoa.map.TileMapRenderer;
import me.dabpessoa.sprite.MegaMan;
import me.dabpessoa.sprite.Sprite;


public class GameCore implements LoopSteps {

	private MainLoop loop;
	private long previousTime;
//	private List<Sprite> sprites;
	private TileMapRenderer mapRenderer;
	private Window canvas;
	private TileMap map;
	private KeyManager keyManager;
	private GameAction moveLeft;
	private GameAction moveRight;
	private GameAction jump;
	private GameAction down;
	private GameAction exit;
	
	public GameCore(TileMapRenderer renderer, TileMap map, Window canvas) {
		 this.loop = new MainLoop(this, 60);
		 this.canvas = canvas;
		 this.mapRenderer = renderer;
		 this.previousTime = System.currentTimeMillis();
		 this.canvas.setIgnoreRepaint(true);
		 this.map = map;
	}
	
	public void run() {
		/*
		 * Prepara o ambiente antes da inicializa��o do loop;
		 */
		this.startLoop();
	}
	
	private void startLoop() {
		new Thread(loop, "Main Loop").start();
	}
	
	@Override
	public void paintScreen() {
		/*
		 * Aqui � onde a tela ser� pintada.
		 */
		if (!canvas.getBufferStrategy().contentsLost())
			canvas.getBufferStrategy().show();
	}

	@Override
	public void processLogics() {
		/*
		 * Aqui ser� efetuado um update nos estados
		 * de todos o sprites.
		 */
		
		//Calcula o tempo entre dois updates.
		long elapsedTime = System.currentTimeMillis() - previousTime;
		
		checkInput(map.getPlayer());
		
		//Chama o update dos sprites passando a vari�vel
		//"elapsedTime" como par�metro.
		map.getPlayer().update(elapsedTime);
		Iterator<Sprite> iterator = map.getSprites();
        while ( iterator.hasNext() ) {
        	Sprite sprite = ( Sprite ) iterator.next();
        	sprite.update(elapsedTime);
		}
		System.out.println(elapsedTime);
		
		//Grava o tempo na sa�da do m�todo.
		previousTime = System.currentTimeMillis();
		
	}
	
	public void checkInput(Sprite sprite) {
		
		if (jump.isPressed()) {
			((MegaMan)sprite).jump();
		}
		
		sprite.setVelocityX(0);
		
		if (moveRight.isPressed()) {
			sprite.setVelocityX(0.13f);
		} else if (moveLeft.isPressed()) {
			sprite.setVelocityX(-0.13f);
		}
		
	}

	@Override
	public void renderGraphics() {
		
		/*
		 * Aqui todos os sprites ser�o pintados na tela.
		 */
		Graphics g = canvas.getBufferStrategy().getDrawGraphics();
		
		//Criamos um contexto gr�fico que n�o leva em conta as bordas
		Graphics g2 = g.create(canvas.getInsets().right, 
				   canvas.getInsets().top, 
				   canvas.getWidth() - canvas.getInsets().left, 
				   canvas.getHeight() - canvas.getInsets().bottom);
		
		mapRenderer.draw(g2, map, canvas.getWidth(), canvas.getHeight());
		
		//Liberamos os contextos criados.
		g.dispose(); 
		g2.dispose();
		
	}

	@Override
	public void setup() {
		//Criamos a estrat�gia de double buffering
		canvas.createBufferStrategy(2);
		
		
		
		moveLeft = new GameAction("moveLeft");
		moveRight = new GameAction("moveRight");
		jump = new GameAction("jump");
		down = new GameAction("down");
		exit = new GameAction("exit");

		keyManager = new KeyManager();
		
		keyManager.map(KeyEvent.VK_LEFT, moveLeft);
		keyManager.map(KeyEvent.VK_RIGHT, moveRight);
		keyManager.map(KeyEvent.VK_DOWN, down);
		keyManager.map(KeyEvent.VK_ESCAPE, exit);
		keyManager.map(KeyEvent.VK_SPACE, jump);
		
		keyManager.setComponent(canvas);
		
		
		
		
	}

	@Override
	public void tearDown() {
		canvas.dispose();
	}

}
