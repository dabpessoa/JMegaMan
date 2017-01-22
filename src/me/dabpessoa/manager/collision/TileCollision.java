package me.dabpessoa.manager.collision;

import me.dabpessoa.map.Tile;

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

        /*
           A checagem das colisões não podem depender da direção para onde o jogador está indo,
           pois pode ser que as paredes (tiles) se desloquem também cansando colisão mesmo sem o jogador se mover.
         */

        // Check right collision
        checkTileCollision(CollisionType.RIGHT, topRight, bottomRight, tilesMatriz, tileWidth, tileHeight, tileCollisionInfo);

        // Check bottom collision
        checkTileCollision(CollisionType.BOTTOM, bottomLeft, bottomRight, tilesMatriz, tileWidth, tileHeight, tileCollisionInfo);

        // Check left collision
        checkTileCollision(CollisionType.LEFT, topLeft, bottomLeft, tilesMatriz, tileWidth, tileHeight, tileCollisionInfo);

        // Check top collision
        checkTileCollision(CollisionType.TOP, topLeft, topRight, tilesMatriz, tileWidth, tileHeight, tileCollisionInfo);

        // Calculate position for NotCollideRect
        calcPositionForNotCollideRect(tileCollisionInfo, rect, movimentDirection);

        return tileCollisionInfo;

    }

    public static void checkTileCollision(CollisionType collisionType, Point fromPoint, Point toPoint, Tile[][] tilesMatriz, int tileWidth, int tileHeight, TileCollisionInfo tileCollisionInfo) {
        int from, to; from = to = -1;
        if (collisionType == CollisionType.LEFT || collisionType == CollisionType.RIGHT) {
            from =  fromPoint.y;
            to = toPoint.y;
        } else if (collisionType == CollisionType.TOP || collisionType == CollisionType.BOTTOM) {
            from = fromPoint.x;
            to = toPoint.x;
        }

        while (from <= to) {
            Point point  = (collisionType == CollisionType.LEFT || collisionType == CollisionType.RIGHT ? new Point(fromPoint.x, from) :
                            collisionType == CollisionType.TOP || collisionType == CollisionType.BOTTOM ? new Point(from, fromPoint.y) :
                            null);

            Tile tile = findTileFromPixelPosition(tilesMatriz, point, tileWidth, tileHeight);

            if (collisionType == CollisionType.LEFT) {
                if (tile != null) {
                    tileCollisionInfo.addLeftTileCollision(tile);
                    tileCollisionInfo.addCollisionType(collisionType);
                }
                if (from == toPoint.y) break;
                from += tileHeight;
                if (from > toPoint.y) from = toPoint.y;
            } else if (collisionType == CollisionType.RIGHT) {
                if (tile != null) {
                    tileCollisionInfo.addRightTileCollision(tile);
                    tileCollisionInfo.addCollisionType(collisionType);
                }
                if (from == toPoint.y) break;
                from += tileHeight;
                if (from > toPoint.y) from = toPoint.y;
            } else if (collisionType == CollisionType.TOP) {
                if (tile != null) {
                    tileCollisionInfo.addTopTileCollision(tile);
                    tileCollisionInfo.addCollisionType(collisionType);
                }
                if (from == toPoint.x) break;
                from += tileWidth;
                if (from > toPoint.x) from = toPoint.x;
            } else if (collisionType == CollisionType.BOTTOM) {
                if (tile != null) {
                    tileCollisionInfo.addBottomTileCollision(tile);
                    tileCollisionInfo.addCollisionType(collisionType);
                }
                if (from == toPoint.x) break;
                from += tileWidth;
                if (from > toPoint.x) from = toPoint.x;
            } else throw new RuntimeException("Invalid Collision Type => "+collisionType);

        }
    }

    public static void calcPositionForNotCollideRect(TileCollisionInfo tileCollisionInfo, Rectangle rect, MovimentDirection movimentDirection) {
        // Caso haja colisão, redefinir posicionamento de um retângulo para o mesmo representar um objeto que não colide.
        if (tileCollisionInfo.hasAnyCollision()) {

            // Descobrindo as quinas do retângulo. OBS: Obrigatório a subtração por "-1" em alguns pontos.
            Point topLeft = new Point(rect.x, rect.y);
            Point topRight = new Point((rect.x + rect.width - 1), rect.y);
            Point bottomLeft = new Point(rect.x, (rect.y + rect.height -1));
            Point bottomRight = new Point((rect.x + rect.width - 1), (rect.y + rect.height -1));

            if (movimentDirection.isGoingToLeft()) {

                if (tileCollisionInfo.hasLeftCollision()) {

                    boolean isRealGoingLeftCollision = true;

                    if (tileCollisionInfo.hasTopCollision()) {
                        Tile biggestTileLeftY =  findBiggestTileY(tileCollisionInfo.getLeftTilesCollision());
                        if (biggestTileLeftY != null) {
                            List<Tile> topTiles = tileCollisionInfo.getBottomTilesCollisionWithout(biggestTileLeftY);
                            if (!topTiles.isEmpty() && biggestTileLeftY.getPixelY() <= topTiles.get(0).getPixelY()) {
                                isRealGoingLeftCollision = false;
                            }
                        }
                    } else if (tileCollisionInfo.hasBottomCollision()) {
                        Tile minimumTileLeftY =  findMinimumTileY(tileCollisionInfo.getLeftTilesCollision());
                        if (minimumTileLeftY != null) {
                            List<Tile> bottomTiles = tileCollisionInfo.getBottomTilesCollisionWithout(minimumTileLeftY);
                            if (!bottomTiles.isEmpty() && minimumTileLeftY.getPixelY() >= bottomTiles.get(0).getPixelY()) {
                                isRealGoingLeftCollision = false;
                            }
                        }
                    }

                    if (isRealGoingLeftCollision) {
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getLeftTilesCollision().get(0), CollisionType.LEFT);
                    }

                }

            } else if (movimentDirection.isGoingToRight()) {

                if (tileCollisionInfo.hasRightCollision()) {

                    boolean isRealGoingRightCollision = true;

                    if (tileCollisionInfo.hasTopCollision()) {
                        Tile biggestTileRightY =  findBiggestTileY(tileCollisionInfo.getRightTilesCollision());
                        if (biggestTileRightY != null) {
                            List<Tile> topTiles = tileCollisionInfo.getBottomTilesCollisionWithout(biggestTileRightY);
                            if (!topTiles.isEmpty() && biggestTileRightY.getPixelY() <= topTiles.get(0).getPixelY()) {
                                isRealGoingRightCollision = false;
                            }
                        }
                    } else if (tileCollisionInfo.hasBottomCollision()) {
                        Tile minimumTileRightY =  findMinimumTileY(tileCollisionInfo.getRightTilesCollision());
                        if (minimumTileRightY != null) {
                            List<Tile> bottomTiles = tileCollisionInfo.getBottomTilesCollisionWithout(minimumTileRightY);
                            if (!bottomTiles.isEmpty() && minimumTileRightY.getPixelY() >= bottomTiles.get(0).getPixelY()) {
                                isRealGoingRightCollision = false;
                            }
                        }
                    }

                    if (isRealGoingRightCollision) {
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getRightTilesCollision().get(0), CollisionType.RIGHT);
                    }

                }

            }

            if (movimentDirection.isGoingToTop()) {

                if (tileCollisionInfo.hasTopCollision()) {

                    boolean isRealGoingTopCollision = true;

                    if (tileCollisionInfo.hasLeftCollision()) {
                        Tile biggestTileTopX =  findBiggestTileX(tileCollisionInfo.getTopTilesCollision());
                        if (biggestTileTopX != null) {
                            List<Tile> leftTiles = tileCollisionInfo.getBottomTilesCollisionWithout(biggestTileTopX);
                            if (!leftTiles.isEmpty() && biggestTileTopX.getPixelX() <= leftTiles.get(0).getPixelX()) {
                                isRealGoingTopCollision = false;
                            }
                        }
                    } else if (tileCollisionInfo.hasRightCollision()) {
                        Tile minimumTileTopX =  findMinimumTileX(tileCollisionInfo.getTopTilesCollision());
                        if (minimumTileTopX != null) {
                            List<Tile> rightTiles = tileCollisionInfo.getBottomTilesCollisionWithout(minimumTileTopX);
                            if (!rightTiles.isEmpty() && minimumTileTopX.getPixelX() >= rightTiles.get(0).getPixelX()) {
                                isRealGoingTopCollision = false;
                            }
                        }
                    }

                    if (isRealGoingTopCollision) {
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getTopTilesCollision().get(0), CollisionType.TOP);
                    }

                }

            } else if (movimentDirection.isGoingToBottom()) {

                if (tileCollisionInfo.hasBottomCollision()) {

                    boolean isRealGoingBottomCollision = true;

                    if (tileCollisionInfo.hasLeftCollision()) {
                        Tile biggestTileBottomX =  findBiggestTileX(tileCollisionInfo.getBottomTilesCollision());
                        if (biggestTileBottomX != null) {
                            List<Tile> leftTiles = tileCollisionInfo.getBottomTilesCollisionWithout(biggestTileBottomX);
                            if (!leftTiles.isEmpty() && biggestTileBottomX.getPixelX() <= leftTiles.get(0).getPixelX()) {
                                isRealGoingBottomCollision = false;
                            }
                        }
                    } else if (tileCollisionInfo.hasRightCollision()) {
                        Tile minimumTileBottomX =  findMinimumTileX(tileCollisionInfo.getBottomTilesCollision());
                        if (minimumTileBottomX != null) {
                            List<Tile> rightTiles = tileCollisionInfo.getBottomTilesCollisionWithout(minimumTileBottomX);
                            if (!rightTiles.isEmpty() && minimumTileBottomX.getPixelX() >= rightTiles.get(0).getPixelX()) {
                                isRealGoingBottomCollision = false;
                            }
                        }
                    }

                    if (isRealGoingBottomCollision) {
                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getBottomTilesCollision().get(0), CollisionType.BOTTOM);
                    }

                }

            }

//            // Colidiu em uma quina do mapa.
//            if (tileCollisionInfo.hasAllSidesCollision()) {
//
//                List<Tile> biggestFirst = tileCollisionInfo.findBiggestTilesSizeCollisionSide(1);
//                List<Tile> biggestSecond = tileCollisionInfo.findBiggestTilesSizeCollisionSide(2);
//
//                if (biggestFirst.equals(tileCollisionInfo.getTopTilesCollision())) {
//                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getTopTilesCollision().get(0), CollisionType.TOP);
//                } else if (biggestFirst.equals(tileCollisionInfo.getRightTilesCollision())) {
//                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getRightTilesCollision().get(0), CollisionType.RIGHT);
//                } else if (biggestFirst.equals(tileCollisionInfo.getBottomTilesCollision())) {
//                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getBottomTilesCollision().get(0), CollisionType.BOTTOM);
//                } else if (biggestFirst.equals(tileCollisionInfo.getLeftTilesCollision())) {
//                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getLeftTilesCollision().get(0), CollisionType.LEFT);
//                }
//
//                if (biggestSecond.equals(tileCollisionInfo.getTopTilesCollision())) {
//                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getTopTilesCollision().get(0), CollisionType.TOP);
//                } else if (biggestSecond.equals(tileCollisionInfo.getRightTilesCollision())) {
//                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getRightTilesCollision().get(0), CollisionType.RIGHT);
//                } else if (biggestSecond.equals(tileCollisionInfo.getBottomTilesCollision())) {
//                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getBottomTilesCollision().get(0), CollisionType.BOTTOM);
//                } else if (biggestSecond.equals(tileCollisionInfo.getLeftTilesCollision())) {
//                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getLeftTilesCollision().get(0), CollisionType.LEFT);
//                }
//
//            } else {
//
//                if ((tileCollisionInfo.hasTopRightCornerCollision() && tileCollisionInfo.hasBottomRightCornerCollision()) || tileCollisionInfo.hasOnlyRightCollision()) {
//                    // rightCollision_WALL
//                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getRightTilesCollision().get(0), CollisionType.RIGHT);
//
//                    if (movimentDirection.isGoingToTop()) {
//                        Tile minimumTopX =  findMinimumTileX(tileCollisionInfo.getTopTilesCollision());
//                        if (minimumTopX != null) {
//                            if (minimumTopX.getPixelX() < tileCollisionInfo.getRightTilesCollision().get(0).getPixelX()) {
//                                // collisionTop
//                                updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getTopTilesCollision().get(0), CollisionType.TOP);
//                            }
//                        }
//                    } else if (movimentDirection.isGoingToBottom()) {
//                        Tile minimumTopX =  findMinimumTileX(tileCollisionInfo.getBottomTilesCollision());
//                        if (minimumTopX != null) {
//                            if (minimumTopX.getPixelX() < tileCollisionInfo.getRightTilesCollision().get(0).getPixelX()) {
//                                // collisionBottom
//                                updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getBottomTilesCollision().get(0), CollisionType.BOTTOM);
//                            }
//                        }
//                    }
//
//                } else if ((tileCollisionInfo.hasBottomLeftCornerCollision() && tileCollisionInfo.hasBottomRightCornerCollision()) || tileCollisionInfo.hasOnlyBottomCollision()) {
//                    // bottomCollision_WALL
//                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getBottomTilesCollision().get(0), CollisionType.BOTTOM);
//
//                    if (movimentDirection.isGoingToRight()) {
//                        Tile minimumRightY =  findMinimumTileY(tileCollisionInfo.getRightTilesCollision());
//                        if (minimumRightY != null) {
//                            if (minimumRightY.getPixelY() < tileCollisionInfo.getBottomTilesCollision().get(0).getPixelY()) {
//                                // collisionRight
//                                updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getRightTilesCollision().get(0), CollisionType.RIGHT);
//                            }
//                        }
//                    } else if (movimentDirection.isGoingToLeft()) {
//                        Tile minimumRightY =  findMinimumTileY(tileCollisionInfo.getLeftTilesCollision());
//                        if (minimumRightY != null) {
//                            if (minimumRightY.getPixelY() < tileCollisionInfo.getBottomTilesCollision().get(0).getPixelY()) {
//                                // collisionLeft
//                                updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getLeftTilesCollision().get(0), CollisionType.LEFT);
//                            }
//                        }
//                    }
//
//                } else if ((tileCollisionInfo.hasBottomLeftCornerCollision() && tileCollisionInfo.hasTopLeftCornerCollision()) || tileCollisionInfo.hasOnlyLeftCollision()) {
//                    // leftCollision_WALL
//                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getLeftTilesCollision().get(0), CollisionType.LEFT);
//
//                    if (movimentDirection.isGoingToTop()) {
//                        Tile biggestTopX =  findBiggestTileX(tileCollisionInfo.getTopTilesCollision());
//                        if (biggestTopX != null) {
//                            if (biggestTopX.getPixelX() > tileCollisionInfo.getLeftTilesCollision().get(0).getPixelX()) {
//                                // collisionTop
//                                updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getTopTilesCollision().get(0), CollisionType.TOP);
//                            }
//                        }
//                    } else if (movimentDirection.isGoingToBottom()) {
//                        Tile biggestBottomX =  findBiggestTileX(tileCollisionInfo.getBottomTilesCollision());
//                        if (biggestBottomX != null) {
//                            if (biggestBottomX.getPixelX() > tileCollisionInfo.getLeftTilesCollision().get(0).getPixelX()) {
//                                // collisionTop
//                                updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getBottomTilesCollision().get(0), CollisionType.BOTTOM);
//                            }
//                        }
//                    }
//
//                } else if ((tileCollisionInfo.hasTopLeftCornerCollision() && tileCollisionInfo.hasTopRightCornerCollision()) || tileCollisionInfo.hasOnlyTopCollision()) {
//                    // topCollision_WALL
//                    updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getTopTilesCollision().get(0), CollisionType.TOP);
//
//                    if (movimentDirection.isGoingToRight()) {
//                        Tile biggestRightY =  findBiggestTileY(tileCollisionInfo.getRightTilesCollision());
//                        if (biggestRightY != null) {
//                            if (biggestRightY.getPixelY() > tileCollisionInfo.getTopTilesCollision().get(0).getPixelY()) {
//                                // collisionRight
//                                updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getRightTilesCollision().get(0), CollisionType.RIGHT);
//                            }
//                        }
//                    } else if (movimentDirection.isGoingToLeft()) {
//                        Tile biggestRightY =  findBiggestTileY(tileCollisionInfo.getLeftTilesCollision());
//                        if (biggestRightY != null) {
//                            if (biggestRightY.getPixelY() > tileCollisionInfo.getTopTilesCollision().get(0).getPixelY()) {
//                                // collisionLeft
//                                updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getLeftTilesCollision().get(0), CollisionType.LEFT);
//                            }
//                        }
//                    }
//
//                }  else if (tileCollisionInfo.hasOnlyTopRightCornerCollision()) {
//
//                    // topRight_CORNER
//                    Tile tileCollision = tileCollisionInfo.getTopTilesCollision().get(0);
//                    int diffX = topRight.x - tileCollision.getPixelX();
//                    int diffY = (tileCollision.getPixelY() + tileCollision.getHeight() -1) - topRight.y;
//                    if (diffX < diffY) {
//                        // collide right
//                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getRightTilesCollision().get(0), CollisionType.RIGHT);
//                    } else {
//                        // collide top
//                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getTopTilesCollision().get(0), CollisionType.TOP);
//                    }
//
//                } else if (tileCollisionInfo.hasOnlyTopLeftCornerCollilsion()) {
//
//                    // topLeft_CORNER
//                    Tile tileCollision = tileCollisionInfo.getTopTilesCollision().get(0);
//                    int diffX = (tileCollision.getPixelX() + tileCollision.getWidth() - 1) - topLeft.x;
//                    int diffY = (tileCollision.getPixelY() + tileCollision.getHeight() -1) - topRight.y;
//                    if (diffX < diffY) {
//                        // collide left
//                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getLeftTilesCollision().get(0), CollisionType.LEFT);
//                    } else {
//                        // collide top
//                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getTopTilesCollision().get(0), CollisionType.TOP);
//                    }
//
//                } else if (tileCollisionInfo.hasOnlyBottomRightCornerCollision()) {
//
//                    // bottomRight_CONER
//                    Tile tileCollision = tileCollisionInfo.getBottomTilesCollision().get(0);
//                    int diffX = bottomRight.x - tileCollision.getPixelX();
//                    int diffY = bottomRight.y - tileCollision.getPixelY();
//                    if (diffX < diffY) {
//                        // collide right
//                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getRightTilesCollision().get(0), CollisionType.RIGHT);
//                    } else {
//                        // collide bottom
//                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getBottomTilesCollision().get(0), CollisionType.BOTTOM);
//                    }
//
//                } else if (tileCollisionInfo.hasOnlyBottomLeftCornerCollision()) {
//
//                    // bottomLeft_CORNER
//                    Tile tileCollision = tileCollisionInfo.getBottomTilesCollision().get(0);
//                    int diffX = (tileCollision.getPixelX() + tileCollision.getWidth() - 1) - bottomLeft.x;
//                    int diffY = bottomLeft.y - tileCollision.getPixelY();
//                    if (diffX < diffY) {
//                        // collide left
//                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getLeftTilesCollision().get(0), CollisionType.LEFT);
//                    } else {
//                        // collide bottom
//                        updateNotCollideRectPosition(tileCollisionInfo.getNotCollideRect(), tileCollisionInfo.getBottomTilesCollision().get(0), CollisionType.BOTTOM);
//                    }
//
//                }
//
//            }

        }
    }

    public static Tile findBiggestTileX(List<Tile> tiles) {
        Tile biggestOne = null;
        if (tiles != null) {
            for (Tile tile : tiles) {
                if (biggestOne == null || biggestOne.getPixelX() < tile.getPixelX()) {
                    biggestOne = tile;
                }
            }
        }
        return biggestOne;
    }

    public static Tile findBiggestTileY(List<Tile> tiles) {
        Tile biggestOne = null;
        if (tiles != null) {
            for (Tile tile : tiles) {
                if (biggestOne == null || biggestOne.getPixelY() < tile.getPixelY()) {
                    biggestOne = tile;
                }
            }
        }
        return biggestOne;
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

    public static Tile findMinimumTileX(List<Tile> tiles) {
        Tile minimumOne = null;
        if (tiles != null) {
            for (Tile tile : tiles) {
                if (minimumOne == null || tile.getPixelX() < minimumOne.getPixelX()) {
                    minimumOne = tile;
                }
            }
        }
        return minimumOne;
    }

    public static Tile findMinimumTileY(List<Tile> tiles) {
        Tile minimumOne = null;
        if (tiles != null) {
            for (Tile tile : tiles) {
                if (minimumOne == null || tile.getPixelY() < minimumOne.getPixelY()) {
                    minimumOne = tile;
                }
            }
        }
        return minimumOne;
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

