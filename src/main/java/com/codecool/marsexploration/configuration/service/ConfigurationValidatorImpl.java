package com.codecool.marsexploration.configuration.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.configuration.model.SimulationConfiguration;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapelements.service.loader.MapLoader;
import com.codecool.marsexploration.mapelements.service.loader.MapLoaderImpl;
import com.codecool.marsexploration.service.Log4j2Logger;

public class ConfigurationValidatorImpl implements ConfigurationValidator {
    Log4j2Logger logger = new Log4j2Logger (ConfigurationValidatorImpl.class);
    private final CoordinateCalculator coordinateCalculator;

    public ConfigurationValidatorImpl(CoordinateCalculator coordinateCalculator) {
        this.coordinateCalculator = coordinateCalculator;
    }

    @Override
    public boolean validate(SimulationConfiguration simulationConfiguration) {
       if( simulationConfiguration.pathToFile().equals(""))
       {
           logger.logError("No path to file has been specified");
           return false;
       }
       else
       {
           MapLoader mapLoader = new MapLoaderImpl();
           Map map = mapLoader.loadMap(simulationConfiguration.pathToFile());
           if(!map.getRepresentation()[simulationConfiguration.coordinate().x()][simulationConfiguration.coordinate().y()].equals(" "))
           {
               logger.logError("Specified coordinate isnt empty");
               return false;
           }
           Iterable<Coordinate> myCoordinates = coordinateCalculator.getAdjacentCoordinates(simulationConfiguration.coordinate(), 32);
           boolean flag = false;
           for(Coordinate myCord : myCoordinates){
               if(map.getRepresentation()[myCord.x()][myCord.y()].equals(" ")){
                  flag=true;
                  break;
               }
           }
           if(!flag)
           {
               logger.logError("There is no empty space to put rover in");
               return false;
           }
       }
        if( simulationConfiguration.resourcesToScanFor().isEmpty())
        {
            logger.logError("There are no resources to scan for");
            return false;
        }
        if(simulationConfiguration.steps()<=0)
        {
            logger.logError("There are no steps left");
            return false;
        }
        return true;
    }
}
