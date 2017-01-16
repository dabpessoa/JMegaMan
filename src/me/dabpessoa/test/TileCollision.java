package me.dabpessoa.test;

import me.dabpessoa.map.Tile;

import java.awt.Rectangle;
import java.awt.Point;

/**
 * Created by dabpessoa on 14/01/2017.
 */
public class TileCollision {

    public static <T extends Tile> T findTileFromPixelPosition(T[][] tilesMatriz, Point pixel, int tileWidth, int tileHeight) {
        int posX = pixel.x / tileWidth;
        int posY = pixel.y / tileHeight;
        return tilesMatriz[posX][posY];
    }

    public static <T extends Tile> Point findPixelFromTileMatrizPosition(int xPosition, int yPosition, int tileWidth, int tileHeight) {
        int pixelX = xPosition * tileWidth;
        int pixelY = yPosition * tileHeight;
        return new Point(pixelX, pixelY);
    }

    public static boolean isRectangleCollide(Rectangle rectangle1, Rectangle rectangle2) {
        return rectangle1.intersects(rectangle2);
    }

    public static <T extends Tile> TileCollisionInfo checkCollision(T[][] tilesMatriz, Rectangle rect, int tileWidth, int tileHeight) {

        Rectangle notCollideRect = new Rectangle(rect.x, rect.y, rect.width, rect.height);
        TileCollisionInfo tileCollisionInfo = new TileCollisionInfo();
        tileCollisionInfo.setNotCollideRect(notCollideRect);

        // Descobrindo as quinas do retângulo
        Point topLeft = new Point(rect.x, rect.y);
        Point topRight = new Point((rect.x + rect.width), rect.y);
        Point bottomLeft = new Point(rect.x, (rect.y + rect.height));
        Point bottomRight = new Point((rect.x + rect.width), (rect.y + rect.height));

        Point fromPoint, toPoint;
        int count;

        // Check right collision
        fromPoint = topRight;
        toPoint = bottomRight;
        count = fromPoint.y;
        while (count <= toPoint.y) {
            Point point = new Point(fromPoint.x, count);
            T tile = findTileFromPixelPosition(tilesMatriz, point, tileWidth, tileHeight);
            if (tile != null) {
                tileCollisionInfo.addCollisionType(CollisionType.RIGHT);
                tileCollisionInfo.getNotCollideRect().x = tile.getPixelX() - tileCollisionInfo.getNotCollideRect().width;
            }

            if (count == toPoint.y) break;
            count += tileHeight;
            if (count > toPoint.y) count = toPoint.y;
        }

        // Check bottom collision
        fromPoint = bottomLeft;
        toPoint = bottomRight;
        count = fromPoint.x;
        while (count <= toPoint.x) {
            Point point = new Point(count, fromPoint.y);
            T tile = findTileFromPixelPosition(tilesMatriz, point, tileWidth, tileHeight);
            if (tile != null) {
                tileCollisionInfo.addCollisionType(CollisionType.BOTTOM);
                tileCollisionInfo.getNotCollideRect().y = tile.getPixelY() - tileCollisionInfo.getNotCollideRect().height;
            }

            if (count == toPoint.x) break;
            count += tileWidth;
            if (count > toPoint.x) count = toPoint.x;
        }

        // Check left collision
        fromPoint = topLeft;
        toPoint = bottomLeft;
        count = fromPoint.y;
        while (count <= toPoint.y) {
            Point point = new Point(fromPoint.x, count);
            T tile = findTileFromPixelPosition(tilesMatriz, point, tileWidth, tileHeight);
            if (tile != null) {
                tileCollisionInfo.addCollisionType(CollisionType.LEFT);
                tileCollisionInfo.getNotCollideRect().x = tile.getPixelX() + tile.getWidth();
            }

            if (count == toPoint.y) break;
            count += tileHeight;
            if (count > toPoint.y) count = toPoint.y;
        }

        // Check top collision
        fromPoint = topLeft;
        toPoint = topRight;
        count = fromPoint.x;
        while (count <= toPoint.x) {
            Point point = new Point(count, fromPoint.y);
            T tile = findTileFromPixelPosition(tilesMatriz, point, tileWidth, tileHeight);
            if (tile != null) {
                tileCollisionInfo.addCollisionType(CollisionType.TOP);
                tileCollisionInfo.getNotCollideRect().y = tile.getPixelY() + tile.getHeight();
            }

            if (count == toPoint.x) break;
            count += tileWidth;
            if (count > toPoint.x) count = toPoint.x;
        }

        return tileCollisionInfo;

    }

}

