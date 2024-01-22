package com.codecool.marsexploration.calculators.service;


public class DimensionCalculatorImpl implements DimensionCalculator{
    public int calculateDimension(int size, int dimensionGrowth){
        int dimension = (int) Math.ceil(Math.sqrt(size));
        return dimension + dimensionGrowth;
    }
}
