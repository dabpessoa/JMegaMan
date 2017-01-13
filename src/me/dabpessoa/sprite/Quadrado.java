package me.dabpessoa.sprite;

import me.dabpessoa.game.World;
import me.dabpessoa.manager.ResourceManager;

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
