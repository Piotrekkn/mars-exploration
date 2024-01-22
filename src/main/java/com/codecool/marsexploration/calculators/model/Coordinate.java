package com.codecool.marsexploration.calculators.model;

public record Coordinate(int x, int y) {
    @Override
    public String toString(){
        return "x:" + this.x() + " y:" + this.y();
    }
}
