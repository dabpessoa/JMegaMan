package me.dabpessoa.game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import me.dabpessoa.manager.KeyManager;
import me.dabpessoa.sprite.MegaMan;
import me.dabpessoa.sprite.Sprite;


public class GameCore implements LoopSteps {

	private GameLoop gameLoop;
	private long previousTime;
	private KeyManager keyManager;
	private GameAction moveLeft;
	private GameAction moveRight;
	private GameAction jump;
	private GameAction down;
	private GameAction exit;
	private World world;

	public GameCore(World world) {
		this.world = world;
		 this.gameLoop = new GameLoop(this, 60);
		 this.previousTime = System.currentTimeMillis();
	}
	
	public void run() {
		/*
		 * Prepara o ambiente antes da inicializa��o do gameLoop;
		 */
		this.startLoop();
	}
	
	private void startLoop() {
		new Thread(gameLoop, "Main Loop").start();
	}
	
	@Override
	public void paintScreen() {
		/*
		 * Aqui � onde a tela ser� pintada.
		 */
		if (!world.getCanvas().getBufferStrategy().contentsLost())
			world.getCanvas().getBufferStrategy().show();
	}

	@Override
	public void processLogics() {
		/*
		 * Aqui ser� efetuado um update nos estados
		 * de todos o sprites.
		 */
		
		//Calcula o tempo entre dois updates.
		long elapsedTime = System.currentTimeMillis() - previousTime;
		
		checkInput(world.getPlayer());
		
		//Chama o update dos sprites passando a vari�vel
		//"elapsedTime" como par�metro.
		world.getPlayer().update(elapsedTime);
		Iterator<Sprite> iterator = world.getSprites();
        while ( iterator.hasNext() ) {
        	Sprite sprite = ( Sprite ) iterator.next();
        	sprite.update(elapsedTime);
		}
		
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
		Graphics g = world.getCanvas().getBufferStrategy().getDrawGraphics();
		
		//Criamos um contexto gr�fico que n�o leva em conta as bordas
		Graphics g2 = g.create(world.getCanvas().getInsets().right,
				world.getCanvas().getInsets().top,
				world.getCanvas().getWidth() - world.getCanvas().getInsets().left,
				world.getCanvas().getHeight() - world.getCanvas().getInsets().bottom);

		world.getTileMapRenderer().draw(g2, world.getTileMap(), world.getCanvas().getWidth(), world.getCanvas().getHeight());
		
		//Liberamos os contextos criados.
		g.dispose(); 
		g2.dispose();
		
	}

	@Override
	public void setup() {

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
		
		keyManager.setComponent(world.getCanvas());

	}

	@Override
	public void tearDown() {
		world.getCanvas().dispose();
	}

}
