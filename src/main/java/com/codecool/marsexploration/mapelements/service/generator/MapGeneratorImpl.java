package com.codecool.marsexploration.mapelements.service.generator;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.calculators.service.DimensionCalculator;
import com.codecool.marsexploration.configuration.model.MapConfiguration;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapelements.model.MapElement;
import com.codecool.marsexploration.mapelements.service.builder.MapElementBuilder;
import com.codecool.marsexploration.mapelements.service.builder.MapElementBuilderImpl;
import com.codecool.marsexploration.mapelements.service.placer.MapElementPlacer;

import java.util.ArrayList;
import java.util.Arrays;

public class MapGeneratorImpl implements MapGenerator{

    private final DimensionCalculator dimensionCalculator;
    private final MapElementsGenerator mapElementsGenerator;
    private final CoordinateCalculator coordinateCalculator;
    private  final MapElementPlacer mapElementPlacer;

    public MapGeneratorImpl(DimensionCalculator dimensionCalculator, MapElementsGenerator mapElementsGenerator, CoordinateCalculator coordinateCalculator, MapElementPlacer mapElementPlacer){
        this.dimensionCalculator = dimensionCalculator;
        this.mapElementsGenerator = mapElementsGenerator;
        this.coordinateCalculator = coordinateCalculator;
        this.mapElementPlacer = mapElementPlacer;
    }
    public Map generate(MapConfiguration mapConfig) {
        int dimension = dimensionCalculator.calculateDimension(mapConfig.mapSize(), 0);
        String[][] myMapString = new String[dimension][dimension];
        myMapString = MapElementBuilderImpl.nullToSpaces(myMapString);
        ArrayList<MapElement> elements = (ArrayList<MapElement>) mapElementsGenerator.createAll(mapConfig);
        Coordinate cord = coordinateCalculator.getRandomCoordinate(dimension);
        for(MapElement element : elements){
            while(!mapElementPlacer.canPlaceElement(element, myMapString, cord)){
                cord = coordinateCalculator.getRandomCoordinate(dimension);
            }
            mapElementPlacer.placeElement(element, myMapString, cord);
        }
        Map map = new Map(myMapString);
        return map;
    }
}
