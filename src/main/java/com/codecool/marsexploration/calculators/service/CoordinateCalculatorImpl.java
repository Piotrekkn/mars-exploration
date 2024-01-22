package com.codecool.marsexploration.calculators.service;

import com.codecool.marsexploration.calculators.model.Coordinate;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CoordinateCalculatorImpl implements CoordinateCalculator{
    private static Random RANDOM = new Random();

    public Coordinate getRandomCoordinate(int dimension){
        int x = RANDOM.nextInt(0, dimension);
        int y = RANDOM.nextInt(0, dimension);
        return new Coordinate(x, y);
    }

    public Iterable<Coordinate> getAdjacentCoordinates(Coordinate coordinate, int dimension){
        ArrayList<Coordinate> arr = new ArrayList<>();

        //left
        arr.add(new Coordinate(coordinate.x() - 1, coordinate.y()));
        //right
        arr.add(new Coordinate(coordinate.x() + 1, coordinate.y()));
        //top
        arr.add(new Coordinate(coordinate.x(), coordinate.y() - 1));
        //bot
        arr.add(new Coordinate(coordinate.x(), coordinate.y() + 1));

        // left top
        arr.add(new Coordinate(coordinate.x() - 1, coordinate.y() -1));
        //left bottom
        arr.add(new Coordinate(coordinate.x() - 1, coordinate.y() + 1));
        //right top
        arr.add(new Coordinate(coordinate.x() + 1, coordinate.y() -1));
        // right bottom
        arr.add(new Coordinate(coordinate.x() + 1, coordinate.y() + 1));
        arr.removeIf(cord -> cord.x() < 0 || cord.x() >= dimension || cord.y() < 0 || cord.y() >= dimension);
        return arr;
    }

    public Iterable<Coordinate> getAdjacentCoordinates(Iterable<Coordinate> coordinates, int dimension){
        ArrayList<Coordinate> arr = new ArrayList<>();
        for (Coordinate coordinate : coordinates){
            Iterable<Coordinate> adjacentCoords = getAdjacentCoordinates(coordinate, dimension);
            Stream<Coordinate> resumeStream = StreamSupport.stream(adjacentCoords.spliterator(), false);
            resumeStream.forEach(element -> arr.add(element));
        }
        return arr;
    }
}