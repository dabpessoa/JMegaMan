package me.dabpessoa.test;

import me.dabpessoa.map.Tile;
import me.dabpessoa.util.ListUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileCollisionInfo {

    private List<CollisionType> collisionsType;
    private List<Tile> rightTilesCollision;
    private List<Tile> bottomTilesCollision;
    private List<Tile> leftTilesCollision;
    private List<Tile> topTilesCollision;
    private Rectangle notCollideRect;
    private boolean hasRightCollision;
    private boolean hasBottomCollision;
    private boolean hasLeftCollision;
    private boolean hasTopCollision;

    public TileCollisionInfo() {
        collisionsType = new ArrayList<>();
        rightTilesCollision = new ArrayList<>();
        bottomTilesCollision = new ArrayList<>();
        leftTilesCollision = new ArrayList<>();
        topTilesCollision = new ArrayList<>();
    }

    public boolean hasAnyCollision() {
        return !collisionsType.isEmpty();
    }

    public boolean hasRightCollision() {
        return hasRightCollision;
    }

    public boolean hasBottomCollision() {
        return hasBottomCollision;
    }

    public boolean hasLeftCollision() {
        return hasLeftCollision;
    }

    public boolean hasTopCollision() {
        return hasTopCollision;
    }

    public boolean hasTopRightCornerCollision() {
        return hasTopCollision() && hasRightCollision();
    }

    public boolean hasTopLeftCornerCollision() {
        return hasTopCollision() && hasLeftCollision();
    }

    public boolean hasBottomRightCornerCollision() {
        return hasBottomCollision() && hasRightCollision();
    }

    public boolean hasBottomLeftCornerCollision() {
        return hasBottomCollision() && hasLeftCollision();
    }

    public boolean hasOnlyBottomCollision() {
        return hasBottomCollision() && !hasRightCollision() && !hasLeftCollision() && !hasTopCollision();
    }

    public boolean hasOnlyTopCollision() {
        return !hasBottomCollision() && !hasRightCollision() && !hasLeftCollision() && hasTopCollision();
    }

    public boolean hasOnlyLeftCollision() {
        return !hasBottomCollision() && !hasRightCollision() && hasLeftCollision() && !hasTopCollision();
    }

    public boolean hasOnlyRightCollision() {
        return !hasBottomCollision() && hasRightCollision() && !hasLeftCollision() && !hasTopCollision();
    }

    public boolean hasOnlyTopRightCornerCollision() {
        return !hasBottomLeftCornerCollision() && !hasBottomRightCornerCollision() && !hasTopLeftCornerCollision() && hasTopRightCornerCollision();
    }

    public boolean hasOnlyTopLeftCornerCollilsion() {
        return !hasBottomLeftCornerCollision() && !hasBottomRightCornerCollision() && hasTopLeftCornerCollision() && !hasTopRightCornerCollision();
    }

    public boolean hasOnlyBottomRightCornerCollision() {
        return !hasBottomLeftCornerCollision() && hasBottomRightCornerCollision() && !hasTopLeftCornerCollision() && !hasTopRightCornerCollision();
    }

    public boolean hasOnlyBottomLeftCornerCollision() {
        return hasBottomLeftCornerCollision() && !hasBottomRightCornerCollision() && !hasTopLeftCornerCollision() && !hasTopRightCornerCollision();
    }

    public boolean hasAllSidesCollision() {
        return hasTopCollision() && hasRightCollision() && hasBottomCollision() && hasLeftCollision();
    }

    public List<Tile> findBiggestTilesSizeCollisionSide(int place) {
        return (List<Tile>) ListUtils.biggerSizeList(place, getRightTilesCollision(), getBottomTilesCollision(), getLeftTilesCollision(), getTopTilesCollision());
    }

    public void addCollisionType(CollisionType collisionType) {
        if (!collisionsType.contains(collisionType)) {
            collisionsType.add(collisionType);
            switch (collisionType) {
                case BOTTOM:
                    hasBottomCollision = true;
                    break;
                case LEFT:
                    hasLeftCollision = true;
                    break;
                case RIGHT:
                    hasRightCollision = true;
                    break;
                case TOP:
                    hasTopCollision = true;
                    break;
            }
        }
    }

    public List<CollisionType> getCollisionsType() {
        return Collections.unmodifiableList(collisionsType);
    }

    public void addRightTileCollision(Tile tile) {
        if (!rightTilesCollision.contains(tile)) {
            rightTilesCollision.add(tile);
        }
    }

    public void addBottomTileCollision(Tile tile) {
        if (!bottomTilesCollision.contains(tile)) {
            bottomTilesCollision.add(tile);
        }
    }

    public void addLeftTileCollision(Tile tile) {
        if (!leftTilesCollision.contains(tile)) {
            leftTilesCollision.add(tile);
        }
    }

    public void addTopTileCollision(Tile tile) {
        if (!topTilesCollision.contains(tile)) {
            topTilesCollision.add(tile);
        }
    }

    public List<Tile> getRightTilesCollision() {
        return Collections.unmodifiableList(rightTilesCollision);
    }

    public List<Tile> getBottomTilesCollision() {
        return Collections.unmodifiableList(bottomTilesCollision);
    }

    public List<Tile> getLeftTilesCollision() {
        return Collections.unmodifiableList(leftTilesCollision);
    }

    public List<Tile> getTopTilesCollision() {
        return Collections.unmodifiableList(topTilesCollision);
    }

    public Rectangle getNotCollideRect() {
        return notCollideRect;
    }

    public void setNotCollideRect(Rectangle notCollideRect) {
        this.notCollideRect = notCollideRect;
    }

}