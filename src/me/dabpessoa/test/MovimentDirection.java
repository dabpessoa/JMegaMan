package me.dabpessoa.test;

import java.awt.*;

/**
 * Created by dabpessoa on 15/01/2017.
 */
public enum MovimentDirection {
    TOP_TO_BOTTOM,
    BOTTOM_TO_TOP,
    LEFT_TO_RIGHT,
    RIGHT_TO_LEFT,
    LEFT_TO_RIGHT_AND_BOTTOM_TO_TOP,
    LEFT_TO_RIGHT_AND_TOP_TO_BOTTOM,
    RIGHT_TO_LEFT_AND_BOTTOM_TO_TOP,
    RIGHT_TO_LEFT_AND_TOP_TO_BOTTOM,
    NO_MOVIMENT;

    public boolean isGoingToRight() {
        return isGoingToRight(this);
    }

    public boolean isGoingToLeft() {
        return isGoingToLeft(this);
    }

    public boolean isGoingToBottom() {
        return isGoingToBottom(this);
    }

    public boolean isGointToTop() {
        return isGointToTop(this);
    }

    public boolean isGoingToTopRight() {
        return isGoingToTopRight(this);
    }

    public boolean isGoingToTopLeft() {
        return isGoingToTopLeft(this);
    }

    public boolean isGoingToBottomRight() {
        return isGoingToBottomRight(this);
    }

    public boolean isGoingToBottomLeft() {
        return isGoingToBottomLeft(this);
    }

    public static boolean isGoingToRight(MovimentDirection movimentDirection) {
        return movimentDirection == LEFT_TO_RIGHT || isGoingToBottomRight(movimentDirection) || isGoingToTopRight(movimentDirection);
    }

    public static boolean isGoingToLeft(MovimentDirection movimentDirection) {
        return movimentDirection == RIGHT_TO_LEFT || isGoingToBottomLeft(movimentDirection) || isGoingToTopLeft(movimentDirection);
    }

    public static boolean isGoingToBottom(MovimentDirection movimentDirection) {
        return movimentDirection == TOP_TO_BOTTOM || isGoingToBottomRight(movimentDirection) || isGoingToBottomLeft(movimentDirection);
    }

    public static boolean isGointToTop(MovimentDirection movimentDirection) {
        return movimentDirection == BOTTOM_TO_TOP || isGoingToTopLeft(movimentDirection) || isGoingToTopRight(movimentDirection);
    }

    public static boolean isGoingToTopRight(MovimentDirection movimentDirection) {
        return movimentDirection == LEFT_TO_RIGHT_AND_BOTTOM_TO_TOP;
    }

    public static boolean isGoingToTopLeft(MovimentDirection movimentDirection) {
        return movimentDirection == RIGHT_TO_LEFT_AND_BOTTOM_TO_TOP;
    }

    public static boolean isGoingToBottomRight(MovimentDirection movimentDirection) {
        return movimentDirection == LEFT_TO_RIGHT_AND_TOP_TO_BOTTOM;
    }

    public static boolean isGoingToBottomLeft(MovimentDirection movimentDirection) {
        return movimentDirection == RIGHT_TO_LEFT_AND_TOP_TO_BOTTOM;
    }

    public static MovimentDirection findMovimentDirection(Point oldPoint, Point newPoint) {
        MovimentDirection movimentDirection = null;
        if (oldPoint.x < newPoint.x) {
            if (oldPoint.y < newPoint.y) {
                movimentDirection = LEFT_TO_RIGHT_AND_TOP_TO_BOTTOM;
            } else if (oldPoint.y > newPoint.y) {
                movimentDirection = LEFT_TO_RIGHT_AND_BOTTOM_TO_TOP;
            } else {
                movimentDirection = LEFT_TO_RIGHT;
            }
        } else if (oldPoint.x > newPoint.x) {
            if (oldPoint.y < newPoint.y) {
                movimentDirection = RIGHT_TO_LEFT_AND_TOP_TO_BOTTOM;
            } else if (oldPoint.y > newPoint.y) {
                movimentDirection = RIGHT_TO_LEFT_AND_BOTTOM_TO_TOP;
            } else {
                movimentDirection = RIGHT_TO_LEFT;
            }
        }

        if (movimentDirection == null) {
            if (oldPoint.y < newPoint.y) {
                movimentDirection = TOP_TO_BOTTOM;
            } else if (oldPoint.y > newPoint.y) {
                movimentDirection = BOTTOM_TO_TOP;
            }
        }

        return  movimentDirection != null ? movimentDirection : NO_MOVIMENT;
    }

}
