package me.dabpessoa.test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TileCollisionInfo {

        private List<CollisionType> collisionsType;
        private Rectangle notCollideRect;
        private boolean hasRightCollision;
        private boolean hasBottomCollision;
        private boolean hasLeftCollision;
        private boolean hasTopCollision;

        public TileCollisionInfo() {
            collisionsType = new ArrayList<>();
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

        public void addCollisionType(CollisionType collisionType) {
            if (!collisionsType.contains(collisionType)) {
                collisionsType.add(collisionType);
                switch (collisionType) {
                    case BOTTOM: hasBottomCollision = true; break;
                    case LEFT: hasLeftCollision = true; break;
                    case RIGHT: hasRightCollision = true; break;
                    case TOP: hasTopCollision = true; break;
                }
            }
        }

        public List<CollisionType> getCollisionsType() {
            return collisionsType;
        }

        public Rectangle getNotCollideRect() {
            return notCollideRect;
        }

        public void setNotCollideRect(Rectangle notCollideRect) {
            this.notCollideRect = notCollideRect;
        }

    }