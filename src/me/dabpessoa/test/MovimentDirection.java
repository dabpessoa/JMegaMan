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
