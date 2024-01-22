package com.codecool.marsexploration.configuration.respository;

import com.codecool.marsexploration.calculators.model.Coordinate;

public interface Config {

    int MAPS_TO_CREATE = 3;
    Coordinate LAND_HERE= new Coordinate(10,10);
    int STEPS = 20;
    int CYCLE = 5;
    int SIGHT = 4;
    int TURN_TIME_MS = 200;
    boolean FORCE_WINNING_CONDITION = false;
    boolean EASIER_CONDITION = true;
    boolean CLEAR_SCREEN = true;
    int MAP_SIZE = 1000; //def: 1000
    int MAP_DIMENSION = 32; //def: 32
}
