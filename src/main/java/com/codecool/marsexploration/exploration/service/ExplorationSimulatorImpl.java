package com.codecool.marsexploration.exploration.service;

import com.codecool.marsexploration.Application;
import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculatorImpl;
import com.codecool.marsexploration.configuration.model.SimulationConfiguration;
import com.codecool.marsexploration.configuration.model.SimulationContext;
import com.codecool.marsexploration.configuration.respository.Config;
import com.codecool.marsexploration.configuration.service.ConfigurationValidator;
import com.codecool.marsexploration.configuration.service.ConfigurationValidatorImpl;
import com.codecool.marsexploration.exploration.model.Direction;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapelements.service.loader.MapLoader;
import com.codecool.marsexploration.mapelements.service.loader.MapLoaderImpl;
import com.codecool.marsexploration.rovers.model.Routine;
import com.codecool.marsexploration.rovers.model.Rover;
import com.codecool.marsexploration.rovers.service.RoverDeployerImpl;
import com.codecool.marsexploration.service.Log4j2Logger;

import java.util.*;

public class ExplorationSimulatorImpl {
    //TODO interface
    //Exploration simulator
    //Everything is in place to start working on the simulation engine. The simulator must receive a configuration object, contact the necessary services, and use the necessary methods to run the simulation. You need to make some decisions about the implementation details of the simulator class. We recommend the following:
    //Generate the context first, then simulate the rover exploration runfter. (SLAP)
    //Generating the context implies loading the chart (reading the file), validating the landing coordinates for the spaceship and deploying the rover in an empty coordinate adjacent to the spaceship. Each one of these responsibilities can be in different classes (SLAP & SRP)
    //The simulation of the rover exploration run can be arranged as a loop that repeats until an outcome is found. Each iteration of the loop can run a series of ordered simulation steps. (SRP & OCP)
    //The Exploration Simulator class design is decided and the class is partially implemented.
    SimulationConfiguration simulationConfiguration;
    ConfigurationValidator configurationValidator = new ConfigurationValidatorImpl(new CoordinateCalculatorImpl());
    MapLoader mapLoader = new MapLoaderImpl();
    SimulationContext simulationContext;
    private String[][] mapMemory;
    Log4j2Logger logger = new Log4j2Logger(Application.class);
    Deque<Direction> deque = new ArrayDeque<Direction>();
    private boolean foundWater = false;
    private Coordinate water = null;

    public ExplorationSimulatorImpl(SimulationConfiguration simulationConfiguration) {
        this.simulationConfiguration = simulationConfiguration;
    }

    public void StartSimulation() {
        //TODO logggggg me plox plox plox
        if (!configurationValidator.validate(simulationConfiguration)) {
            logger.logError("Couldnt validate map");
            return;
        }
        Map map = mapLoader.loadMap(simulationConfiguration.pathToFile());
        Rover rover = new RoverDeployerImpl(new CoordinateCalculatorImpl()).placeRover(map, simulationConfiguration.coordinate());
        simulationContext = new SimulationContext(simulationConfiguration.steps(), 3, rover, simulationConfiguration.coordinate(), map, simulationConfiguration.resourcesToScanFor());
        createNewMemoryMap();
        if (Config.FORCE_WINNING_CONDITION) {
            simulationContext.getMap().getRepresentation()[2][2] = "*";
            simulationContext.getMap().getRepresentation()[2][4] = "%";
        }
        SimulationLoop();
    }

    private void SimulationLoop() {
        boolean running = true;
        Integer cycles = simulationContext.getRover().getCycles();
        while (running) {
            if (simulationContext.getRover().getRoutine() == Routine.EXPLORATION) {
                int step = simulationConfiguration.steps();
                scanning();
                log();
                while (step > 0) {
                    clearScreen();
                    logger.logInfo("State: EXPLORATION, Steps left: " + step + ", Cycles left: " + cycles);
                    movement();
                    scanning();
                    log();
                    step--;
                    sleep(Config.TURN_TIME_MS);
                }
                simulationContext.getRover().setRoutine(Routine.RETURN);
            } else if (simulationContext.getRover().getRoutine() == Routine.RETURN) {
                Iterator<Direction> iterator = deque.iterator();
                while (iterator.hasNext()) {
                    clearScreen();
                    logger.logInfo("State: RETURNING, Cycles left: " + cycles);
                    Direction direction = iterator.next();
                    moveTo(direction.opposite());
                    log();
                    sleep(Config.TURN_TIME_MS);
                }
                deque.clear();
                if (foundWater) {
                    running = false;
                }
                if (cycles-- > 0) {
                    simulationContext.getRover().setRoutine(Routine.EXPLORATION);
                } else {
                    running = false;
                }
            }
        }
        if (!foundWater) {
            logger.logInfo("MISSION OVER: Failed");

        } else {
            logger.logInfo("MISSION OVER: Success");
            logger.logInfo("Found water nearby mineral at: " + water);
            logger.logInfo("HUMANITY IS READY TO COLONIZE THE MARS, pls bring a lot of movies");
        }
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void movement() {
        //0-left,1-right,2-down,3-up
        int[] placesToAddToMap = {-1, -1, -1, -1};

        if (canMoveTo(Direction.LEFT)) {
            placesToAddToMap[0] = countFieldsToReveal(Direction.LEFT);
        }
        if (canMoveTo(Direction.RIGHT)) {
            placesToAddToMap[1] = countFieldsToReveal(Direction.RIGHT);
        }
        if (canMoveTo(Direction.DOWN)) {
            placesToAddToMap[2] = countFieldsToReveal(Direction.DOWN);
        }
        if (canMoveTo(Direction.UP)) {
            placesToAddToMap[3] = countFieldsToReveal(Direction.UP);
        }
        //TODO maybe add random?
        //find maximum places to reveal
        int max = placesToAddToMap[0];
        int index = 0;
        for (int i = 0; i < placesToAddToMap.length; i++) {
            if (max < placesToAddToMap[i]) {
                max = placesToAddToMap[i];
                index = i;
            }

        }


        //if there is no optimal step go back
        Direction direction = Direction.values()[index];
        if (!deque.isEmpty() && max == -1) {
            direction = deque.getFirst().opposite();
        }
        //find
        else if (deque.isEmpty() && max == 0) {
            direction = Direction.RIGHT;
        }
        //move to
        moveTo(direction);
        deque.push(direction);
    }

    ////TODO move somewhere else///////////////////////////////////////////
//    private boolean CanMoveTo(Coordinate coord)
//    {
//        return false;
//    }
    private int countFieldsToReveal(Direction direction) {
        Coordinate coord = simulationContext.getRover().getPosition();
        int unknows = 0;
        switch (direction) {
            case UP:
                return countUnknows(new Coordinate(coord.x() - 1, coord.y()));
            case DOWN:
                return countUnknows(new Coordinate(coord.x() + 1, coord.y()));
            case RIGHT:
                return countUnknows(new Coordinate(coord.x(), coord.y() + 1));
            case LEFT:
                return countUnknows(new Coordinate(coord.x(), coord.y() - 1));
            default:
                return 0;
        }
    }

    private int countUnknows(Coordinate coordinate) {
        int unknows = 0;
        int sight = simulationContext.getRover().getSight();
        int boundsX = simulationContext.getMap().getRepresentation().length;
        int boundsY = simulationContext.getMap().getRepresentation()[0].length;
        for (int i = coordinate.x() - sight; i <= coordinate.x() + sight; i++) {
            for (int j = coordinate.y() - sight; j <= coordinate.y() + sight; j++) {
                //check if its in bounds
                if (i < 0 || j < 0 || i >= boundsX || j >= boundsY) {
                    continue;
                }
                if (mapMemory[i][j].equals("x")) {
                    unknows++;
                }
            }

        }
        return unknows;
    }

    private void moveTo(Direction direction) {
        switch (direction) {
            case UP:
                simulationContext.getRover().setPosition(new Coordinate(simulationContext.getRover().getPosition().x() - 1, simulationContext.getRover().getPosition().y()));
                break;
            case DOWN:
                simulationContext.getRover().setPosition(new Coordinate(simulationContext.getRover().getPosition().x() + 1, simulationContext.getRover().getPosition().y()));
                break;
            case RIGHT:
                simulationContext.getRover().setPosition(new Coordinate(simulationContext.getRover().getPosition().x(), simulationContext.getRover().getPosition().y() + 1));
                break;
            case LEFT:
                if (simulationContext.getRover().getPosition().y() > 0) {
                    simulationContext.getRover().setPosition(new Coordinate(simulationContext.getRover().getPosition().x(), simulationContext.getRover().getPosition().y() - 1));
                }
                break;
            default:
                break;
        }
    }

    private void createNewMemoryMap() {
        mapMemory = new String[simulationContext.getMap().getRepresentation().length][simulationContext.getMap().getRepresentation()[0].length];
        for (int i = 0; i < mapMemory.length; i++) {
            for (int j = 0; j < mapMemory[i].length; j++) {
                mapMemory[i][j] = "x";
            }
        }
    }

    private boolean canMoveTo(Direction direction) {
        Coordinate currPos = simulationContext.getRover().getPosition();
        switch (direction) {
            case UP:
                //check for boundries || check for going back and forth
                if (currPos.x() - 1 < 0 || !deque.isEmpty() && deque.getFirst().opposite() == Direction.UP) {
                    return false;
                }
                return simulationContext.getMap().getRepresentation()[currPos.x() - 1][currPos.y()].equals(" ");

            case DOWN:
                if (currPos.x() + 1 > simulationContext.getMap().getRepresentation().length || !deque.isEmpty() && deque.getFirst().opposite() == Direction.DOWN) {
                    return false;
                }
                return simulationContext.getMap().getRepresentation()[currPos.x() + 1][currPos.y()].equals(" ");

            case RIGHT:
                if (currPos.y() + 1 > simulationContext.getMap().getRepresentation()[0].length || !deque.isEmpty() && deque.getFirst().opposite() == Direction.RIGHT) {
                    return false;
                }
                return simulationContext.getMap().getRepresentation()[currPos.x()][currPos.y() + 1].equals(" ");

            case LEFT:
                if (currPos.y() - 1 < 0 || !deque.isEmpty() && deque.getFirst().opposite() == Direction.LEFT) {
                    return false;
                }
                return simulationContext.getMap().getRepresentation()[currPos.x()][currPos.y() - 1].equals(" ");
            default:
                //System.out.println("wymyslec tresc loga by trzeba ale cs2 wyszedl i sie nie da");
                return false;
        }
    }

    /////////////////////////////////////////////////////////
    private void scanning() {
        //cuurent locat
        //z niego adjecent wrzucic na mapmemory
        Rover rover = simulationContext.getRover();
        Coordinate roverPos = rover.getPosition();
        int sight = rover.getSight();
        int boundsX = simulationContext.getMap().getRepresentation().length;
        int boundsY = simulationContext.getMap().getRepresentation()[0].length;
        ///wyciagnac do voida
        for (int i = roverPos.x() - sight; i <= roverPos.x() + sight; i++) {
            for (int j = roverPos.y() - sight; j <= roverPos.y() + sight; j++) {
                //check if its in bounds
                if (i < 0 || j < 0 || i >= boundsX || j >= boundsY) {
                    continue;
                }
                mapMemory[i][j] = simulationContext.getMap().getRepresentation()[i][j];
                analysis(new Coordinate(i, j));
            }
        }
    }

    private static void clearScreen() {
        if (Config.CLEAR_SCREEN) {
            for (int i = 0; i < 20; i++) {
                System.out.println();
            }
        }

    }

    private void analysis(Coordinate coordinate) {
        Map map = simulationContext.getMap();
        Rover rover = simulationContext.getRover();
        //aqua
        if (mapMemory[coordinate.x()][coordinate.y()].equals("*")) {
            ArrayList<Coordinate> adjacentCoordinates;
            if (Config.EASIER_CONDITION) {
                adjacentCoordinates = (ArrayList<Coordinate>) new CoordinateCalculatorImpl().getAdjacentCoordinates(
                        (ArrayList<Coordinate>) new CoordinateCalculatorImpl()
                                .getAdjacentCoordinates(coordinate, 32), 32);
            } else {
                adjacentCoordinates = (ArrayList<Coordinate>) new CoordinateCalculatorImpl().getAdjacentCoordinates(coordinate, 32);
            }
            for (Coordinate myCord : adjacentCoordinates) {
                if (mapMemory[myCord.x()][myCord.y()].equals("%")) {

                    water = myCord;
                    foundWater = true;
                    logger.logInfo("FOUND WATER NEARBY MINERAL!");
                    break;
                }
            }

        }

    }


    private void log() {
        System.out.println();
        String[][] tempmap = simulationContext.getMap().GetCopy();
        //show spaceship
        tempmap[simulationConfiguration.coordinate().x()][simulationConfiguration.coordinate().y()] = "■";
        //show rover
        Rover rover = simulationContext.getRover();
        tempmap[rover.getPosition().x()][rover.getPosition().y()] = "¤";
        //hide terrian
        for (int i = 0; i < tempmap.length; i++) {
            for (int j = 0; j < tempmap[i].length; j++) {
                if (mapMemory[i][j].equals("x")) {
                    tempmap[i][j] = "▓";
                }
            }
        }

        System.out.println(Map.createStringRepresentation(tempmap));
    }


}

