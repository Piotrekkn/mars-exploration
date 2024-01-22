package com.codecool.marsexploration;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.*;
import com.codecool.marsexploration.configuration.model.*;
import com.codecool.marsexploration.configuration.respository.Config;
import com.codecool.marsexploration.configuration.service.*;
import com.codecool.marsexploration.exploration.service.ExplorationSimulatorImpl;
import com.codecool.marsexploration.mapelements.service.builder.*;
import com.codecool.marsexploration.mapelements.service.generator.*;
import com.codecool.marsexploration.mapelements.service.placer.*;
import com.codecool.marsexploration.output.service.MapFileWriter;
import com.codecool.marsexploration.output.service.MapFileWriterImpl;
import com.codecool.marsexploration.service.Log4j2Logger;

import java.util.*;

public class Application {
    // You can change this to any directory you like
    private static final String WorkDir = "src/main";

    public static void main(String[] args) {
        Log4j2Logger logger = new Log4j2Logger(Application.class);
        logger.logInfo("Mars Exploration");
        MapConfiguration mapConfig = getConfiguration();

        DimensionCalculator dimensionCalculator = new DimensionCalculatorImpl();
        CoordinateCalculator coordinateCalculator = new CoordinateCalculatorImpl();

        MapElementBuilder mapElementFactory = new MapElementBuilderImpl(dimensionCalculator);
        MapElementsGenerator mapElementsGenerator = new MapElementsGeneratorImpl(mapElementFactory);

        MapConfigurationValidator mapConfigValidator = null;
        MapElementPlacer mapElementPlacer = new MapElementPlacerImpl(coordinateCalculator);

        MapGenerator mapGenerator = new MapGeneratorImpl(dimensionCalculator, mapElementsGenerator, coordinateCalculator, mapElementPlacer);
//        System.out.println(mapGenerator.generate(mapConfig));

        createAndWriteMaps(Config.MAPS_TO_CREATE, mapGenerator, mapConfig);

        logger.logInfo("Mars maps successfully generated.");
        final String fileName = "/1.txt";
        final String filePath=WorkDir+fileName;
        List<String> resourcesToScan = new ArrayList<String>();
        resourcesToScan.add("*");//water

        SimulationConfiguration simulationConfiguration = new SimulationConfiguration(filePath, Config.LAND_HERE, resourcesToScan, Config.STEPS);

        ExplorationSimulatorImpl explorationSimulator = new ExplorationSimulatorImpl(simulationConfiguration);
        introScreen();

        explorationSimulator.StartSimulation();
        // ------ TESTS ---------

//        System.out.println(mapConfig);
//        MapConfigurationValidator myValidation = new MapConfigurationValidatorImpl();
//        System.out.println(myValidation.validate(mapConfig));
//        CoordinateCalculator myCalc = new CoordinateCalculatorImpl();
//        System.out.println(myCalc.getAdjacentCoordinates(new Coordinate(2,2), 1));
//        System.out.println(myCalc.getAdjacentCoordinates(new Coordinate(2,2), 2));
//        Iterable<Coordinate> myCalcIterable = Arrays.asList(new Coordinate(1,1), new Coordinate(2,2));
//        System.out.println(myCalc.getAdjacentCoordinates(myCalcIterable, 1));
//        System.out.println(myCalc.getAdjacentCoordinates(myCalcIterable, 2));
//        Coordinate randomCoordinate = myCalc.getRandomCoordinate(5);
//        Iterable<Coordinate> my3RandomCoords = Arrays.asList(randomCoordinate, randomCoordinate, randomCoordinate);
//        Iterable<Coordinate> myIterableOfCords = myCalc.getAdjacentCoordinates(my3RandomCoords, 2);
//        ArrayList<Coordinate> myListOfCords = (ArrayList<Coordinate>) myIterableOfCords;
//        System.out.println(myListOfCords);
//        String[][] myMultidimensionalString = {{"a", "b"}, {"c", "d"}, {"g", "z"}};
//        Map myMap = new Map(myMultidimensionalString);
//        System.out.println(myMap);
////        Map myMap2 = new Map(myListOfCords);
//
//
//        String[][] randomArea3 = {{"", "", ""},{"", "", ""},{"", "", ""}};
//        String[][] randomArea8 = nullToSpaces(new String[8][8]);
//        System.out.println(Arrays.deepToString(randomArea8));
//        Random rand = new Random();
//        for (int i = 0; i < 20; i ++){
//            randomArea8[rand.nextInt(8)][rand.nextInt(8)] = "f";
//        }
//        Map myMap8 = new Map(randomArea8);
//        System.out.println(myMap8);
//        System.out.println(Arrays.deepToString(randomArea3));
//        System.out.println(Arrays.deepToString(randomArea8));
//        System.out.println(mapElementFactory.build(20, "#", "mountains", 3, null));
//        System.out.println("---------");
//        System.out.println(mapElementsGenerator.createAll(mapConfig));
//        System.out.println(mapGenerator.generate(mapConfig));


    }
    private static void clearScreen() {
        if (Config.CLEAR_SCREEN) {
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
        }
    }
    private static void introScreen() {
        clearScreen();
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("====================================");
        System.out.println("           MARS EXPLORATOR");
        System.out.println("====================================");
        System.out.println("⠀⠀⠀⠀⠀⢀⣤⣶⣶⣤⡀⠀⠀⢀⣤⣶⣦⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⣷⣶⣶⣿⣿⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⢀⣼⣿⣿⣿⡿⠋⠉⠉⠙⢿⣷⡶⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
        System.out.println("⠀⣠⣴⣶⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
        System.out.println("⢸⣿⣿⣿⣿⣧⠀⠀⠀⢀⣤⣶⣾⣿⣿⣿⣿⣶⣤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
        System.out.println("⠘⢿⣿⣿⣿⡇⠀⠀⣰⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⠀⠀⠀⠀⠀⠀⠀⠀");
        System.out.println("⠀⠀⠉⠉⠻⣿⣦⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣶⣄⠀⠀⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⢸⣿⠏⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⢹⣿⡆⠀⠀⠀");
        System.out.println("⠀⠀⠀⢀⣤⣾⣿⣤⣀⠀⠀⠀⣀⣤⣤⣤⣤⡀⠀⠀⠀⣠⣼⣿⣧⣄⡀⠀⠀");
        System.out.println("⠀⠀⣰⣿⣿⣿⣿⣿⣿⣧⠀⣼⣿⣿⣿⣿⣿⣿⣆⠀⣾⣿⣿⣿⣿⣿⣿⡄⠀");
        System.out.println("⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣶⣿⣿⣿⣿⣿⣿⣿⣿⣾⣿⣿⣿⣿⣿⣿⣿⡇⠀");
        System.out.println("⠀⠀⠘⢿⣿⣿⣿⣿⣿⠟⠉⠹⣿⣿⣿⣿⣿⣿⠋⠉⢻⣿⣿⣿⣿⣿⡿⠁⠀");
        System.out.println("⠀⠀⠀⠉⠛⠛⠋⠀⠀⠀⠀⠀⠙⠛⠛⠉⠀⠀⠀⠀⠈⠙⠛⠛⠉⠀⠀⠀");
        System.out.println("");
        System.out.println("Config:");
        System.out.println("Rover, steps: " + Config.STEPS + " cycles: " + Config.CYCLE + " sight: " + Config.SIGHT);
        System.out.println("Sim, frame time: " + Config.TURN_TIME_MS + " easier condition: " + Config.EASIER_CONDITION + " place winning condition: " + Config.FORCE_WINNING_CONDITION);
        System.out.println("Spaceship, landing place : " + Config.LAND_HERE);
        System.out.println("");
        System.out.println("Press ENTER to Start");
        System.out.println("=========================================================");
        //wait for enter
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
    }

    private static void createAndWriteMaps(int count, MapGenerator mapGenerator, MapConfiguration mapConfig) {
        for (int i = 0; i < count; i++) {
            MapFileWriter writer = new MapFileWriterImpl();
            writer.writeMapFile(mapGenerator.generate(mapConfig), WorkDir + "/" + (i + 1) + ".txt");
        }
    }

    private static MapConfiguration getConfiguration() {
        final String mountainSymbol = "#";
        final String pitSymbol = "&";
        final String mineralSymbol = "%";
        final String waterSymbol = "*";

        MapElementConfiguration mountainsCfg = new MapElementConfiguration(
                mountainSymbol,
                "mountain",
                List.of(
                        new ElementToSize(2, 20),
                        new ElementToSize(1, 30)
                ),
                3,
                ""
        );

        MapElementConfiguration pitsCfg = new MapElementConfiguration(
                pitSymbol,
                "pit",
                List.of(
                        new ElementToSize(2, 10),
                        new ElementToSize(1, 20)
                ), 10,
                ""

        );

        MapElementConfiguration mineralCfg = new MapElementConfiguration(
                mineralSymbol,
                "mineral",
                List.of(new ElementToSize(10, 1)),
                0,
                "#"
        );

        MapElementConfiguration waterCfg = new MapElementConfiguration(
                waterSymbol,
                "water",
                List.of(new ElementToSize(10, 1)),
                0,
                "&"
        );

        List<MapElementConfiguration> elementsCfg = List.of(mountainsCfg, pitsCfg, mineralCfg, waterCfg);
        return new MapConfiguration(Config.MAP_SIZE, 0.5, elementsCfg);
    }
}

