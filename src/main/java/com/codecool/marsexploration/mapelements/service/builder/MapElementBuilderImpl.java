package com.codecool.marsexploration.mapelements.service.builder;

import com.codecool.marsexploration.calculators.service.DimensionCalculator;
import com.codecool.marsexploration.mapelements.model.MapElement;

import java.util.Random;

public class MapElementBuilderImpl implements MapElementBuilder{

    private final DimensionCalculator dimensionCalculator;
    public MapElementBuilderImpl(DimensionCalculator dimensionCalculator){
        this.dimensionCalculator = dimensionCalculator;
    }
    public MapElement build(int size, String symbol, String name, int dimensionGrowth, String preferredLocationSymbol){
        int dimension = dimensionCalculator.calculateDimension(size, dimensionGrowth);
        String[][] multiDimensionalString = nullToSpaces(new String[dimension][dimension]);
        Random rand = new Random();
        for (int i = 0; i < size; i ++){
            boolean a = true;
            while (a) {
                int x =rand.nextInt(dimension);
                int y=rand.nextInt(dimension);
                if(multiDimensionalString[x][y].equals(" ") || multiDimensionalString[x][y]==null)
                {
                    multiDimensionalString[x][y] = symbol;
                    a = false;
                }
            }
        }
        return new MapElement(multiDimensionalString, name, dimension);
    }

    public static String[][] nullToSpaces( String[][] array)
    {
        for (int i =0 ;i<array.length; i++)
        {
            for (int j =0 ;j<array[0].length; j++)
            {
                if(array[i][j]==null)
                {
                    array[i][j]=" ";
                }
            }
        }
        return array;
    }
}
