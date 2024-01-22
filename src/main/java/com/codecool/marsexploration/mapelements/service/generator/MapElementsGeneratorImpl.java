package com.codecool.marsexploration.mapelements.service.generator;

import com.codecool.marsexploration.configuration.model.ElementToSize;
import com.codecool.marsexploration.configuration.model.MapConfiguration;
import com.codecool.marsexploration.configuration.model.MapElementConfiguration;
import com.codecool.marsexploration.mapelements.model.MapElement;
import com.codecool.marsexploration.mapelements.service.builder.MapElementBuilder;


import java.util.ArrayList;
import java.util.List;

public class MapElementsGeneratorImpl implements MapElementsGenerator{

    private final MapElementBuilder mapElementBuilder;

    public MapElementsGeneratorImpl(MapElementBuilder mapElementBuilder){
        this.mapElementBuilder = mapElementBuilder;
    }
    public Iterable<MapElement> createAll(MapConfiguration mapConfig){
        List<MapElement> elements = new ArrayList<>();
        for (MapElementConfiguration element : mapConfig.mapElementConfigurations()){
            for (ElementToSize elementToSize : element.elementToSizes()){
                for(int i = 0; i< elementToSize.elementCount(); i++){
                    elements.add(mapElementBuilder.build(elementToSize.size(), element.symbol(), element.name(), element.dimensionGrowth(), element.preferredLocationSymbol()));
                }
            }
        }
        return elements;
    };
}
