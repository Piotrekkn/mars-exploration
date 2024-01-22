package com.codecool.marsexploration.configuration.model;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.rovers.model.Rover;

import java.util.List;

public class SimulationContext {

    int steps;
    int stepsToTimeout;
    Rover rover;
    Coordinate spaceshipLocation;
    Map map;
    List<String> symbolsToMonitor;

    public SimulationContext(int steps, int stepsToTimeout, Rover rover, Coordinate spaceshipLocation, Map map, List<String> symbolsToMonitor) {
        this.steps = steps;
        this.stepsToTimeout = stepsToTimeout;
        this.rover = rover;
        this.spaceshipLocation = spaceshipLocation;
        this.map = map;
        this.symbolsToMonitor = symbolsToMonitor;
    }

    public Map getMap() {
        return map;
    }

    public Rover getRover() {
        return rover;
    }
    //TODO: implement outcome
}
