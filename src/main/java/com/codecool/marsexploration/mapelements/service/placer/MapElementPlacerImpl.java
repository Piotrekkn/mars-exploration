package com.codecool.marsexploration.mapelements.service.placer;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.calculators.service.CoordinateCalculatorImpl;
import com.codecool.marsexploration.configuration.model.MapConfiguration;
import com.codecool.marsexploration.configuration.respository.Config;
import com.codecool.marsexploration.mapelements.model.MapElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapElementPlacerImpl implements MapElementPlacer{

    private final CoordinateCalculator coordinateCalculator;

    public MapElementPlacerImpl(CoordinateCalculator coordinateCalculator) {
        this.coordinateCalculator = coordinateCalculator;
    }

    public boolean canPlaceElement(MapElement element, String[][] map, Coordinate coordinate){
        String[][] representation = element.getRepresentation();
        if(representation.length > map.length - coordinate.x() || representation[0].length > map[0].length - coordinate.y()){
//            System.out.println("Not enough space");
            return false;
        }
        for( int x = 0; x < representation.length; x++) {
            for( int y = 0; y < representation[0].length; y++) {
                if(representation[x][y] != " " && map[x+coordinate.x()][y+coordinate.y()] != " "){
//                    System.out.println("I want to place " + representation[x][y] + " " + x + " " + y + " on " + map[x+coordinate.x()][y+coordinate.y()] + " " + x+coordinate.x() + " " + y+coordinate.y());
                    return false;
                }
            }
        }
        return true;
    }
    public void placeElement(MapElement element, String[][] map, Coordinate coordinate){
            if(element.getName().equals("mountain") || element.getName().equals("pit")){
                String[][] representation = element.getRepresentation();
                for( int x = 0; x < representation.length; x++){
                    for(int y = 0; y < representation[0].length; y++){
                        map[x + coordinate.x()][y + coordinate.y()] = representation[x][y];
                    }
                }
            }
            else if(element.getName().equals("mineral") || element.getName().equals("water")){
                placeMineralOrWater(element, map);
            }
    }

    private void placeMineralOrWater(MapElement element, String[][] map){
//        for (int i = 0; i < 10 ; i++) {
        Coordinate cord = new Coordinate(0, 0);
        if(element.getName().equals("mineral")) {
            cord = getRandomMountainOrPit(map, "#");
        }
        else if (element.getName().equals("water")){
            cord = getRandomMountainOrPit(map, "&");
        }
            Iterable<Coordinate> myCoordinates = coordinateCalculator.getAdjacentCoordinates(cord, Config.MAP_DIMENSION);
            boolean flag = true;
            for(Coordinate myCord : myCoordinates){
                if (map[myCord.x()][myCord.y()].equals(" ") && flag){
                    if(element.getName().equals("mineral")) {
                        map[myCord.x()][myCord.y()] = "%";
                    }
                    else if (element.getName().equals("water")){
                        map[myCord.x()][myCord.y()] = "*";
                    }
                    flag = false;
                }
            }
        }
//    }

    private Coordinate getRandomMountainOrPit(String[][] map, String symbol){
        Random random = new Random();
        while(true){
            int x = random.nextInt(map.length);
            int y = random.nextInt(map[0].length);
            if(map[x][y].equals(symbol)){
                return new Coordinate(x, y);
            }
        }
    }
}
