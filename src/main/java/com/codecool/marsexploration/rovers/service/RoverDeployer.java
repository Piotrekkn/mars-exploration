package com.codecool.marsexploration.rovers.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.rovers.model.Rover;

public interface RoverDeployer {
   public Rover placeRover(Map map, Coordinate spaceshipCoord) ;

}
