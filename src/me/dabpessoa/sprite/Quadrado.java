package me.dabpessoa.sprite;

import me.dabpessoa.game.World;
import me.dabpessoa.manager.ResourceManager;
import me.dabpessoa.map.TileMapRenderer;
import me.dabpessoa.test.TileCollisionInfo;
import me.dabpessoa.test.TileCollision;

import java.awt.*;

/**
 * Created by dabpessoa on 12/01/2017.
 */
public class Quadrado extends Sprite {

    public Quadrado(World world) {
        super(world);
    }

    @Override
    public void update(long elapsedTime) {

        Animation oldAnimation = getAnimation();
        // Atualiza imagem da animação
        getAnimation().update( elapsedTime );

        // calcula deslocamento x e y de acordo com a variação de tempo
        // x = V * t (espaço percorrido = velocidade * tempo)
        float novaPosicaoX = getX() + (getVelocityX() * elapsedTime);
        float novaPosicaoY = getY() + (getVelocityY() * elapsedTime);

        boolean colisaoX = getWorld().getTileMap().getSpriteTileCollisionPoint(this, novaPosicaoX, getY()) != null;
        boolean colisaoY = getWorld().getTileMap().getSpriteTileCollisionPoint(this, getX(), novaPosicaoY) != null;


        Rectangle rect = new Rectangle(Math.round(novaPosicaoX), Math.round(novaPosicaoY), getAnimation().getImage().getWidth(null), getAnimation().getImage().getHeight(null));
        TileCollisionInfo tileCollisionInfo = TileCollision.checkCollision(getWorld().getTileMap().getTiles(), rect, TileMapRenderer.TILE_SIZE, TileMapRenderer.TILE_SIZE);

        System.out.println("Colidiu: "+ tileCollisionInfo.hasAnyCollision());
        if (tileCollisionInfo.hasLeftCollision() || tileCollisionInfo.hasRightCollision()) {
            setVelocityX(0.0f);
            setX(tileCollisionInfo.getNotCollideRect().x);
        } else {
            setX(novaPosicaoX);
        }

        if (tileCollisionInfo.hasTopCollision() || tileCollisionInfo.hasBottomCollision()) {
            setVelocityY(0.0f);
            setY(tileCollisionInfo.getNotCollideRect().y);
        } else {
            setY(novaPosicaoY);
        }

        System.out.println("X: "+getX());
        System.out.println("Y: "+getY());

    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(getAnimation().getImage(), Math.round(getX()), Math.round(getY()), getWorld().getCanvas());
    }

    @Override
    public void init() {

        ResourceManager resourceManager = getWorld().getResourceManager();

        Animation animation = new Animation();
        animation.addFrame(resourceManager.loadImage("quadrado/quadrado.png"), 100);

        this.setAnimation(animation);

        this.setX(668);
        this.setY(300);
    }
}
