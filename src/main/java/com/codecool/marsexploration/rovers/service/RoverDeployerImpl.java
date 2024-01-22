package com.codecool.marsexploration.rovers.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.configuration.respository.Config;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.rovers.model.Rover;

import java.util.Collections;
import java.util.List;

public class RoverDeployerImpl implements RoverDeployer {

    private final CoordinateCalculator coordinateCalculator;

    public RoverDeployerImpl(CoordinateCalculator coordinateCalculator) {
        this.coordinateCalculator = coordinateCalculator;
    }

    @Override
    public Rover placeRover(Map map, Coordinate spaceshipCoord) {
        Iterable<Coordinate> myCoordinates = coordinateCalculator.getAdjacentCoordinates(spaceshipCoord, 32);
        Coordinate placeHere=new Coordinate(0,0);
        for (Coordinate myCord : myCoordinates) {
            if (map.getRepresentation()[myCord.x()][myCord.y()].equals(" ")) {
                placeHere=myCord;
            }
        }
        List<Coordinate> mylist = Collections.<Coordinate>emptyList();
        return new Rover(placeHere, Config.SIGHT,mylist,Config.CYCLE);
    }
}
