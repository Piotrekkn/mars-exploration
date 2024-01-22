package com.codecool.marsexploration.rovers.model;

import com.codecool.marsexploration.calculators.model.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Rover {
    String id;
    Coordinate position;
    Integer sight;
    List<Coordinate> resources;
    Integer cycles;
    Routine routine;


    //TODO implement id
    public Rover(Coordinate position, Integer sight, List<Coordinate> resources, Integer cycles) {
        id = "jam jest rover";
        this.position = position;
        this.sight = sight;
        this.resources = resources;
        routine = Routine.EXPLORATION;
        this.cycles = cycles;
        resources = new ArrayList<>();
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

    public Integer getCycles() {
        return cycles;
    }

    public void setCycles(Integer cycles) {
        this.cycles = cycles;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public Coordinate getPosition() {
        return position;
    }

    public Integer getSight() {
        return sight;
    }

    public List<Coordinate> getResources() {
        return resources;
    }
    //An ID, rover-1.
    //
    //The current position.
    //
    //The sight, meaning how far away the rover can see.
    //
    //One or more collections containing the coordinates where it encountered the resources specified in the configuration.
}
