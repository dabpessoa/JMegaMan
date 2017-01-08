package me.dabpessoa.game;

import me.dabpessoa.manager.ResourceManager;
import me.dabpessoa.map.TileMap;
import me.dabpessoa.map.TileMapRenderer;
import me.dabpessoa.sprite.Cowboy;
import me.dabpessoa.sprite.MegaMan;
import me.dabpessoa.sprite.Sprite;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by dabpessoa on 08/01/2017.
 */
public class World {

    private ResourceManager resourceManager;
    private LinkedList<Sprite> sprites;
    private TileMap tileMap;
    private TileMapRenderer tileMapRenderer;
    private JFrame canvas;
    private Sprite player;

    public World() {
        sprites = new LinkedList<Sprite>();
        resourceManager = new ResourceManager();
        tileMapRenderer = new TileMapRenderer(this);
        canvas = createCanvas();
        try {
            tileMap = createMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initWorld() {

        tileMapRenderer.setBackground(resourceManager.loadImage("background.png"));

        MegaMan megaMan = new MegaMan(this);
        Cowboy cowboy = new Cowboy(this);

        setPlayer(megaMan);
        addSprite(cowboy);
//        setPlayer(cowboy);
//        addSprite(megaMan);



    }

    public void start() {
        GameCore core = new GameCore(this);
        core.run();
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    public TileMap createMap() throws IOException {
        tileMap = new TileMap(50, 25);
        tileMap = resourceManager.loadMap("map.txt");
        return tileMap;
    }

    private JFrame createCanvas() {
        JFrame canvas = new JFrame();
        canvas.setSize(500, 500);
        canvas.setIgnoreRepaint(true);
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setLocationRelativeTo(null);
        canvas.setBackground(Color.BLUE);
        canvas.setVisible(true);

        // Activate invisible cursor.
        Cursor invisibleCursor =
                Toolkit.getDefaultToolkit().createCustomCursor(
                        Toolkit.getDefaultToolkit().getImage( "" ),
                        new Point( 0, 0 ),
                        "invisible" );
        canvas.setCursor(invisibleCursor);

        //Criamos a estrat�gia de double buffering
        canvas.createBufferStrategy(2);

        return canvas;
    }

    public Sprite getPlayer() {
        return player;
    }

    public void setPlayer(Sprite player) {
        this.player = player;
    }

    public JFrame getCanvas() {
        return canvas;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public TileMapRenderer getTileMapRenderer() {
        return tileMapRenderer;
    }

    /**
     * Obt�m o Iterator de todas as Sprites desse mapa, menos a do jogador.
     */
    public Iterator<Sprite> getSprites() {
        return sprites.iterator();
    }

}
