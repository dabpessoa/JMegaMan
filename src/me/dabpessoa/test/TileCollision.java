package me.dabpessoa.test;

import me.dabpessoa.map.Tile;
import me.dabpessoa.util.MapUtils;

import java.awt.Rectangle;
import java.awt.Point;
import java.util.*;

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

    public static <T extends Tile> TileCollisionInfo checkCollision(T[][] tilesMatriz, Rectangle rect, int tileWidth, int tileHeight, MovimentDirection movimentDirection) {

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
                tileCollisionInfo.addRightTileCollision(tile);
                System.out.println("RIGHT COLLISION");
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
                tileCollisionInfo.addBottomTileCollision(tile);
                System.out.println("BOTTOM COLLISION");
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
                tileCollisionInfo.addLeftTileCollision(tile);
                System.out.println("LEFT COLLISION");
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
                tileCollisionInfo.addTopTileCollision(tile);
                System.out.println("TOP COLLISION");
            }

            if (count == toPoint.x) break;
            count += tileWidth;
            if (count > toPoint.x) count = toPoint.x;
        }

        // Caso haja colisão, redefinir posicionamento de um retângulo para o mesmo representar um objeto que não colide.
        if (tileCollisionInfo.hasAnyCollision()) {

            // Colidiu em uma quina do mapa.
            if (tileCollisionInfo.hasAllSidesCollision()) {

                List<Tile> biggestFirst = tileCollisionInfo.findBiggestTilesSizeCollisionSide(1);
                List<Tile> biggestSecond = tileCollisionInfo.findBiggestTilesSizeCollisionSide(2);

                if (biggestFirst.equals(tileCollisionInfo.getTopTilesCollision())) {
                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getTopTilesCollision().get(0), CollisionType.TOP);
                } else if (biggestFirst.equals(tileCollisionInfo.getRightTilesCollision())) {
                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getRightTilesCollision().get(0), CollisionType.RIGHT);
                } else if (biggestFirst.equals(tileCollisionInfo.getBottomTilesCollision())) {
                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getBottomTilesCollision().get(0), CollisionType.BOTTOM);
                } else if (biggestFirst.equals(tileCollisionInfo.getLeftTilesCollision())) {
                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getLeftTilesCollision().get(0), CollisionType.LEFT);
                }

                if (biggestSecond.equals(tileCollisionInfo.getTopTilesCollision())) {
                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getTopTilesCollision().get(0), CollisionType.TOP);
                } else if (biggestSecond.equals(tileCollisionInfo.getRightTilesCollision())) {
                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getRightTilesCollision().get(0), CollisionType.RIGHT);
                } else if (biggestSecond.equals(tileCollisionInfo.getBottomTilesCollision())) {
                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getBottomTilesCollision().get(0), CollisionType.BOTTOM);
                } else if (biggestSecond.equals(tileCollisionInfo.getLeftTilesCollision())) {
                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getLeftTilesCollision().get(0), CollisionType.LEFT);
                }

            } else {

                if ((tileCollisionInfo.hasTopRightCornerCollision() && tileCollisionInfo.hasBottomRightCornerCollision()) || tileCollisionInfo.hasOnlyRightCollision()) {
                    // rightCollision_WALL
                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getRightTilesCollision().get(0), CollisionType.RIGHT);
                } else if ((tileCollisionInfo.hasBottomLeftCornerCollision() && tileCollisionInfo.hasBottomRightCornerCollision()) || tileCollisionInfo.hasOnlyBottomCollision()) {
                    // bottomCollision_WALL
                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getBottomTilesCollision().get(0), CollisionType.BOTTOM);
                } else if ((tileCollisionInfo.hasBottomLeftCornerCollision() && tileCollisionInfo.hasTopLeftCornerCollision()) || tileCollisionInfo.hasOnlyLeftCollision()) {
                    // leftCollision_WALL
                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getLeftTilesCollision().get(0), CollisionType.LEFT);
                } else if ((tileCollisionInfo.hasTopLeftCornerCollision() && tileCollisionInfo.hasTopRightCornerCollision()) || tileCollisionInfo.hasOnlyTopCollision()) {
                    // topCollision_WALL
                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getTopTilesCollision().get(0), CollisionType.TOP);
                }  else if (tileCollisionInfo.hasOnlyTopRightCornerCollision()) {
                    // topRight_CORNER
                    if (movimentDirection == MovimentDirection.LEFT_TO_RIGHT_AND_BOTTOM_TO_TOP) {

                    } else if (movimentDirection == MovimentDirection.LEFT_TO_RIGHT) {
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getRightTilesCollision().get(0), CollisionType.RIGHT);
                    } else if (movimentDirection == MovimentDirection.BOTTOM_TO_TOP) {
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getTopTilesCollision().get(0), CollisionType.TOP);
                    }
                } else if (tileCollisionInfo.hasOnlyTopLeftCornerCollilsion()) {
                    // topLeft_CORNER
                    if (movimentDirection == MovimentDirection.RIGHT_TO_LEFT_AND_BOTTOM_TO_TOP) {

                    } else if (movimentDirection == MovimentDirection.RIGHT_TO_LEFT) {
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getLeftTilesCollision().get(0), CollisionType.LEFT);
                    } else if (movimentDirection == MovimentDirection.BOTTOM_TO_TOP) {
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getTopTilesCollision().get(0), CollisionType.TOP);
                    }
                } else if (tileCollisionInfo.hasOnlyBottomRightCornerCollision()) {
                    // bottomRight_CONER
                    if (movimentDirection == MovimentDirection.LEFT_TO_RIGHT_AND_TOP_TO_BOTTOM) {

                    } else if (movimentDirection == MovimentDirection.LEFT_TO_RIGHT) {
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getRightTilesCollision().get(0), CollisionType.RIGHT);
                    } else if (movimentDirection == MovimentDirection.TOP_TO_BOTTOM) {
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getBottomTilesCollision().get(0), CollisionType.BOTTOM);
                    }
                } else if (tileCollisionInfo.hasOnlyBottomLeftCornerCollision()) {
                    // bottomLeft_CORNER
                    if (movimentDirection == MovimentDirection.RIGHT_TO_LEFT_AND_TOP_TO_BOTTOM) {

                    } else if (movimentDirection == MovimentDirection.RIGHT_TO_LEFT) {
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getLeftTilesCollision().get(0), CollisionType.LEFT);
                    } else if (movimentDirection == MovimentDirection.TOP_TO_BOTTOM) {
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getBottomTilesCollision().get(0), CollisionType.BOTTOM);
                    }
                }

            }

        }

        return tileCollisionInfo;

    }

    public static void updateNotCollideRectPosition(Rectangle notCollideRect, Tile collisionTile, CollisionType collisionType) {
        switch (collisionType) {
            case TOP: {
                notCollideRect.y = collisionTile.getPixelY() + collisionTile.getHeight();
                break;
            }
            case RIGHT: {
                notCollideRect.x = collisionTile.getPixelX() - notCollideRect.width;
                break;
            }
            case BOTTOM: {
                notCollideRect.y = collisionTile.getPixelY() - notCollideRect.height;
                break;
            }
            case LEFT: {
                notCollideRect.x = collisionTile.getPixelX() + collisionTile.getWidth();
                break;
            }
            default: throw new RuntimeException("Tipo de colisão inválida. Tipo: "+collisionType.name());
        }
    }

}

