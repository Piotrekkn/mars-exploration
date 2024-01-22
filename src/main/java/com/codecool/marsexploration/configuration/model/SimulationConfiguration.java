package com.codecool.marsexploration.configuration.model;

import com.codecool.marsexploration.calculators.model.Coordinate;

import java.util.List;

public record SimulationConfiguration(String pathToFile, Coordinate coordinate, List<String> resourcesToScanFor, int steps) {

}
