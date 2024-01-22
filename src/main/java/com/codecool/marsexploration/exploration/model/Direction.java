package com.codecool.marsexploration.exploration.model;

import com.codecool.marsexploration.rovers.model.Routine;

public enum Direction {
    //0-left,1-right,2-down,3-up
    LEFT, RIGHT, DOWN, UP;

    public Direction opposite() {
        switch (this) {
            case LEFT -> {
                return RIGHT;
            }
            case RIGHT -> {
                return LEFT;
            }
            case DOWN -> {
                return UP;
            }
            default -> {
                return DOWN;
            }
        }

    }
    }
