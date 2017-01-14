package me.dabpessoa.test;

import me.dabpessoa.map.Tile;
import me.dabpessoa.map.TileMap;
import me.dabpessoa.map.TileMapRenderer;
import org.w3c.dom.css.Rect;

import java.awt.*;

/**
 * Created by dabpessoa on 14/01/2017.
 */
public class Collision {

    public static void main(String[] args) {


        Rectangle rect1 = new Rectangle(9,0, 10,10);
        Rectangle rect2 = new Rectangle(0,0, 10,10);

        System.out.println(isRectangleCollide(rect1, rect2));

    }

    public <T> T findTileFromCoordinate(T[][] tilesMatriz, Point pixel, int tileWidth, int tileHeight) {
        int posX = pixel.x / tileWidth;
        int posY = pixel.y / tileHeight;
        return tilesMatriz[posX][posY];
    }

    public <T> void checkRightCollision(T[][] tilesMatriz, Rectangle rect, int tileWidth, int tileHeight) {

        // Descobrindo as quinas do retângulo
        Point topLeft = new Point(rect.x, rect.y);
        Point topRight = new Point((rect.x + rect.width), rect.y);
        Point bottomLeft = new Point(rect.x, (rect.y + rect.height));
        Point bottomRight = new Point((rect.x + rect.width), (rect.y + rect.height));

        Point fromPoint, toPoint;

        fromPoint = topRight;
        toPoint = bottomRight;

        int x = fromPoint.x;
        for(int i = fromPoint.y ; i < toPoint.y ; i++) {
            int y = i;
            Point point = new Point(x, y);
            T tile = findTileFromCoordinate(tilesMatriz, point, tileWidth, tileHeight);
            if (tile != null) {
                // collide right
            }
        }

        // TODO


    }

    public static boolean isRectangleCollide(Rectangle rectangle1, Rectangle rectangle2) {
        return rectangle1.intersects(rectangle2);
    }

}
