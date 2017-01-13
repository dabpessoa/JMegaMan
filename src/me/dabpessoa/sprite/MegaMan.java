package me.dabpessoa.sprite;

import java.awt.Graphics2D;

import me.dabpessoa.game.World;
import me.dabpessoa.manager.ResourceManager;

public class MegaMan extends Sprite {

	private static final float JUMP_SPEED = -.77f;
	public static final float GRAVITY = 0.002f;
	
	private Animation runLeftAnimation;
    private Animation runRightAnimation;
    private Animation idleLeftAnimation;
	private Animation idleRightAnimation;
	private Animation jumpLeftAnimation;
    private Animation jumpRightAnimation;
    
    private boolean onGround;
	private boolean turnedRight;

	public MegaMan(World world) {
		super(world);
		turnedRight = true;
		onGround = true;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(getAnimation().getImage(), Math.round(getX()), Math.round(getY()), getWorld().getCanvas());
	}

	@Override
	public void init() {

		ResourceManager resourceManager = getWorld().getResourceManager();

		Animation runLeftAnimation = new Animation();
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run1.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run2.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run3.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run4.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run5.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run6.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run7.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run8.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run9.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run10.png"), 80);

		Animation runRightAnimation = new Animation();
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run1.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run2.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run3.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run4.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run5.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run6.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run7.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run8.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run9.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run10.png"), 80);

		Animation idleRightAnimation = new Animation();
		idleRightAnimation.addFrame(resourceManager.loadImage("megaman/idle/right/idle1.png"), 2000);
		idleRightAnimation.addFrame(resourceManager.loadImage("megaman/idle/right/idle2.png"), 350);
		idleRightAnimation.addFrame(resourceManager.loadImage("megaman/idle/right/idle3.png"), 200);
		idleRightAnimation.addFrame(resourceManager.loadImage("megaman/idle/right/idle4.png"), 230);
		idleRightAnimation.addFrame(resourceManager.loadImage("megaman/idle/right/idle5.png"), 350);
		idleRightAnimation.addFrame(resourceManager.loadImage("megaman/idle/right/idle6.png"), 1650);

		Animation idleLeftAnimation = new Animation();
		idleLeftAnimation.addFrame(resourceManager.loadImage("megaman/idle/left/idle1.png"), 2000);
		idleLeftAnimation.addFrame(resourceManager.loadImage("megaman/idle/left/idle2.png"), 350);
		idleLeftAnimation.addFrame(resourceManager.loadImage("megaman/idle/left/idle3.png"), 200);
		idleLeftAnimation.addFrame(resourceManager.loadImage("megaman/idle/left/idle4.png"), 230);
		idleLeftAnimation.addFrame(resourceManager.loadImage("megaman/idle/left/idle5.png"), 350);
		idleLeftAnimation.addFrame(resourceManager.loadImage("megaman/idle/left/idle6.png"), 1650);

		Animation jumpLeftAnimation = new Animation();
		jumpLeftAnimation.addFrame(resourceManager.loadImage("megaman/jump/left/jump1.png"), 75);
		jumpLeftAnimation.addFrame(resourceManager.loadImage("megaman/jump/left/jump2.png"), 75);
		jumpLeftAnimation.addFrame(resourceManager.loadImage("megaman/jump/left/jump3.png"), 75);
		jumpLeftAnimation.addFrame(resourceManager.loadImage("megaman/jump/left/jump4.png"), 75);
		jumpLeftAnimation.addFrame(resourceManager.loadImage("megaman/jump/left/jump5.png"), 75);
		jumpLeftAnimation.addFrame(resourceManager.loadImage("megaman/jump/left/jump6.png"), 75);
		jumpLeftAnimation.addFrame(resourceManager.loadImage("megaman/jump/left/jump7.png"), 75);
		jumpLeftAnimation.addFrame(resourceManager.loadImage("megaman/jump/left/jump8.png"), 75);
		jumpLeftAnimation.addFrame(resourceManager.loadImage("megaman/jump/left/jump9.png"), 75);
		jumpLeftAnimation.addFrame(resourceManager.loadImage("megaman/jump/left/jump10.png"), 75);

		Animation jumpRightAnimation = new Animation();
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump1.png"), 75);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump2.png"), 75);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump3.png"), 75);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump4.png"), 75);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump5.png"), 75);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump6.png"), 75);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump7.png"), 75);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump8.png"), 75);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump9.png"), 75);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump10.png"), 75);

		this.setIdleLeftAnimation(idleLeftAnimation);
		this.setIdleRightAnimation(idleRightAnimation);
		this.setRunLeftAnimation(runLeftAnimation);
		this.setRunRightAnimation(runRightAnimation);
		this.setJumpLeftAnimation(jumpLeftAnimation);
		this.setJumpRightAnimation(jumpRightAnimation);

		this.setX(668);
		this.setY(300);

		this.setAnimation(getIdleRightAnimation());

	}

	@Override
	public void update(long elapsedTime) {

		// Verifica se está no chão
		if (getVelocityY() == 0.0f) {
			onGround = true;
			getJumpLeftAnimation().initConfig();
			getJumpRightAnimation().initConfig();
		}

		// A gravidade afeta o sprite (atuação da gravidade).
		this.setVelocityY(this.getVelocityY() + (GRAVITY * elapsedTime));

		// Guarda uma referência para a animação anterior
		// para posterior verificação de diferença dos tamanhos das imagens.
		Animation oldAnimation = getAnimation();

		// Seleciona a animação correta
		if (isJumping()) {
			if (isTurnedRight()) this.setAnimation(getJumpRightAnimation());
			else this.setAnimation(getJumpLeftAnimation());
		} else if ( getVelocityX() == 0 ) {
			if (isTurnedRight()) this.setAnimation(getIdleRightAnimation());
			else this.setAnimation(getIdleLeftAnimation());
		} else if (getVelocityX() > 0) {
			this.setAnimation(getRunRightAnimation());
		} else if (getVelocityX() < 0) {
			this.setAnimation(getRunLeftAnimation());
		}

		// Atualiza imagem da animação
		getAnimation().update( elapsedTime );

		// calcula deslocamento x e y de acordo com a variação de tempo
		// x = V * t (espaço percorrido = velocidade * tempo)
		float novaPosicaoX = getX() + (getVelocityX() * elapsedTime);
		float novaPosicaoY = getY() + (getVelocityY() * elapsedTime);

		boolean colisaoX = getWorld().getTileMap().getSpriteTileCollision(this, novaPosicaoX, getY()) != null;
		boolean colisaoY = getWorld().getTileMap().getSpriteTileCollision(this, getX(), novaPosicaoY) != null;

		if (colisaoX) {

			setVelocityX(0.0f);

			// Verifica diferença do tamanho das imagens.
			float diff = getAnimation().getImage().getWidth(null) - oldAnimation.getImage().getWidth(null);
			setX(getX() - diff);

		} else setX(novaPosicaoX); // Se não colidir atualiza a nova posição X

		if (colisaoY) {

			setVelocityY(0.0f);

			// Verifica diferença do tamanho das imagens.
			float diff = getAnimation().getImage().getHeight(null) - oldAnimation.getImage().getHeight(null);
			setY(getY() - diff);

		} else setY(novaPosicaoY); // Se não colidir atualiza a nova posição Y

	}

	/**
     * Chamado antes de update() se a criatura colidiu com um tile
     * horizontalmente.
     */
    public void collideHorizontal() {
        setVelocityX(0.0f);
    }


    /**
     * Chamado antes de update() se a criatura colidiu com um tile
     * verticalmente.
     */
    public void collideVertical() {
        setVelocityY(0.0f);
    }
	
	/**
     * Faz o jogador pular se o mesmo estiver no ch�o ou ent�o se forceJump 
     * � true.
     */
    public void jump() {
        if ( onGround  ) {
            onGround = false;
            setVelocityY( JUMP_SPEED );
        }
    }

	public boolean isJumping() {
		return !onGround;
	}
	
	public Animation getRunLeftAnimation() {
		return runLeftAnimation;
	}
	
	public void setRunLeftAnimation(Animation runLeftAnimation) {
		this.runLeftAnimation = runLeftAnimation;
	}
	
	public Animation getRunRightAnimation() {
		return runRightAnimation;
	}
	
	public void setRunRightAnimation(Animation runRightAnimation) {
		this.runRightAnimation = runRightAnimation;
	}

	public Animation getIdleLeftAnimation() {
		return idleLeftAnimation;
	}

	public void setIdleLeftAnimation(Animation idleLeftAnimation) {
		this.idleLeftAnimation = idleLeftAnimation;
	}

	public Animation getIdleRightAnimation() {
		return idleRightAnimation;
	}

	public void setIdleRightAnimation(Animation idleRightAnimation) {
		this.idleRightAnimation = idleRightAnimation;
	}

	public Animation getJumpRightAnimation() {
		return jumpRightAnimation;
	}

	public void setJumpRightAnimation(Animation jumpRightAnimation) {
		this.jumpRightAnimation = jumpRightAnimation;
	}

	public Animation getJumpLeftAnimation() {
		return jumpLeftAnimation;
	}

	public void setJumpLeftAnimation(Animation jumpLeftAnimation) {
		this.jumpLeftAnimation = jumpLeftAnimation;
	}

	public boolean isTurnedRight() {
		return turnedRight;
	}

	public void setTurnedRight(boolean turnedRight) {
		this.turnedRight = turnedRight;
	}

}
