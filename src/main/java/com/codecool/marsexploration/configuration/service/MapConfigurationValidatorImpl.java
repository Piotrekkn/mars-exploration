package com.codecool.marsexploration.configuration.service;

import com.codecool.marsexploration.configuration.model.MapConfiguration;
import com.codecool.marsexploration.configuration.model.MapElementConfiguration;

public class MapConfigurationValidatorImpl implements MapConfigurationValidator{
    public boolean validate(MapConfiguration mapConfig){
        if (mapConfig.elementToSpaceRatio() > 0.5){
            return false;
        }
        for(MapElementConfiguration element: mapConfig.mapElementConfigurations()){
            if(element.symbol() == "#" && element.dimensionGrowth() != 3){
                return false;
            }
            else if(element.symbol() == "&" && element.dimensionGrowth() != 10){
                return false;
            }
            else if(element.symbol() == "%" && element.dimensionGrowth() != 0){
                return false;
            }
            else if(element.symbol() == "*" && element.dimensionGrowth() != 0){
                return false;
            }
        }
        return true;
    }
}
