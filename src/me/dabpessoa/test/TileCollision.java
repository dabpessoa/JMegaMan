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

        // Descobrindo as quinas do retângulo. OBS: Obrigatório a subtração por "-1" em alguns pontos.
        Point topLeft = new Point(rect.x, rect.y);
        Point topRight = new Point((rect.x + rect.width - 1), rect.y);
        Point bottomLeft = new Point(rect.x, (rect.y + rect.height -1));
        Point bottomRight = new Point((rect.x + rect.width - 1), (rect.y + rect.height -1));

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

                    if (movimentDirection.isGoingToRight()) {
                        Rectangle minimumRightY =  findMinimumY(Tile.createRectangles(tileCollisionInfo.getRightTilesCollision()));
                        if (minimumRightY.y < tileCollisionInfo.getBottomTilesCollision().get(0).getPixelY()) {
                            // collisionRight
                            System.out.println("foi... funfou.. "); // TODO implementar nas outras colisões
                            updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getRightTilesCollision().get(0), CollisionType.RIGHT);
                        }

                    }

                } else if ((tileCollisionInfo.hasBottomLeftCornerCollision() && tileCollisionInfo.hasTopLeftCornerCollision()) || tileCollisionInfo.hasOnlyLeftCollision()) {
                    // leftCollision_WALL
                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getLeftTilesCollision().get(0), CollisionType.LEFT);
                } else if ((tileCollisionInfo.hasTopLeftCornerCollision() && tileCollisionInfo.hasTopRightCornerCollision()) || tileCollisionInfo.hasOnlyTopCollision()) {
                    // topCollision_WALL
                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getTopTilesCollision().get(0), CollisionType.TOP);
                }  else if (tileCollisionInfo.hasOnlyTopRightCornerCollision()) {

                    // topRight_CORNER
                    Tile tileCollision = tileCollisionInfo.getTopTilesCollision().get(0);
                    int diffX = topRight.x - tileCollision.getPixelX();
                    int diffY = (tileCollision.getPixelY() + tileCollision.getHeight() -1) - topRight.y;
                    if (diffX < diffY) {
                        // collide right
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getRightTilesCollision().get(0), CollisionType.RIGHT);
                    } else {
                        // collide top
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getTopTilesCollision().get(0), CollisionType.TOP);
                    }

                } else if (tileCollisionInfo.hasOnlyTopLeftCornerCollilsion()) {

                    // topLeft_CORNER
                    Tile tileCollision = tileCollisionInfo.getTopTilesCollision().get(0);
                    int diffX = (tileCollision.getPixelX() + tileCollision.getWidth() - 1) - topLeft.x;
                    int diffY = (tileCollision.getPixelY() + tileCollision.getHeight() -1) - topRight.y;
                    if (diffX < diffY) {
                        // collide left
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getLeftTilesCollision().get(0), CollisionType.LEFT);
                    } else {
                        // collide top
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getTopTilesCollision().get(0), CollisionType.TOP);
                    }

                } else if (tileCollisionInfo.hasOnlyBottomRightCornerCollision()) {

                    // bottomRight_CONER
                    Tile tileCollision = tileCollisionInfo.getBottomTilesCollision().get(0);
                    int diffX = bottomRight.x - tileCollision.getPixelX();
                    int diffY = bottomRight.y - tileCollision.getPixelY();
                    if (diffX < diffY) {
                        // collide right
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getRightTilesCollision().get(0), CollisionType.RIGHT);
                    } else {
                        // collide bottom
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getBottomTilesCollision().get(0), CollisionType.BOTTOM);
                    }

                } else if (tileCollisionInfo.hasOnlyBottomLeftCornerCollision()) {

                    // bottomLeft_CORNER
                    Tile tileCollision = tileCollisionInfo.getBottomTilesCollision().get(0);
                    int diffX = (tileCollision.getPixelX() + tileCollision.getWidth() - 1) - bottomLeft.x;
                    int diffY = bottomLeft.y - tileCollision.getPixelY();
                    if (diffX < diffY) {
                        // collide left
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getLeftTilesCollision().get(0), CollisionType.LEFT);
                    } else {
                        // collide bottom
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getBottomTilesCollision().get(0), CollisionType.BOTTOM);
                    }

                }

            }

        }

        return tileCollisionInfo;

    }

    public static Rectangle findBiggestX(List<Rectangle> rectangles) {
        Rectangle biggestOne = null;
        if (rectangles != null) {
            for (Rectangle rectangle : rectangles) {
                if (biggestOne == null || biggestOne.x < rectangle.x) {
                    biggestOne = rectangle;
                }
            }
        }
        return biggestOne;
    }

    public static Rectangle findBiggestY(List<Rectangle> rectangles) {
        Rectangle biggestOne = null;
        if (rectangles != null) {
            for (Rectangle rectangle : rectangles) {
                if (biggestOne == null || biggestOne.y < rectangle.y) {
                    biggestOne = rectangle;
                }
            }
        }
        return biggestOne;
    }

    public static Rectangle findMinimumX(List<Rectangle> rectangles) {
        Rectangle minimumOne = null;
        if (rectangles != null) {
            for (Rectangle rectangle : rectangles) {
                if (minimumOne == null || rectangle.x < minimumOne.x) {
                    minimumOne = rectangle;
                }
            }
        }
        return minimumOne;
    }

    public static Rectangle findMinimumY(List<Rectangle> rectangles) {
        Rectangle minimumOne = null;
        if (rectangles != null) {
            for (Rectangle rectangle : rectangles) {
                if (minimumOne == null || rectangle.y < minimumOne.y) {
                    minimumOne = rectangle;
                }
            }
        }
        return minimumOne;
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

