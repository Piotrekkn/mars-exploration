package com.codecool.marsexploration.mapelements.model;

import java.sql.Array;
import java.util.Arrays;
import java.util.stream.Stream;

public class Map {
    private String[][] representation;

    private boolean successfullyGenerated;

    public Map(String[][] representation) {
        this.representation = representation;
    }

    public boolean isSuccessfullyGenerated() {
        return successfullyGenerated;
    }

    public void setSuccessfullyGenerated(boolean successfullyGenerated) {
        this.successfullyGenerated = successfullyGenerated;
    }

    public static String createStringRepresentation(String[][] arr) {
//        String[] result = Arrays.stream(arr).flatMap(element -> Stream.of(element)).toArray(size -> new String[size]);
        String temp = "";
        for (int i = 0; i < arr.length; i ++){
            for (int j = 0; j < arr[i].length; j ++){
                temp += arr[i][j];
            }
            temp += "\n";
        }
        return temp;
//        String stringResult = Arrays.toString(result);
//        return stringResult;
    }

    @Override
    public String toString() {
        return createStringRepresentation(representation);
    }


    public String[][] GetCopy()
    {
       // representation;
       // Arrays.copyOf(
        String[][] copy = new String[representation.length][representation[0].length];
        for (int i = 0; i < representation.length; i++) {
            for (int j = 0; j < representation[0].length; j++) {
                copy[i][j]=representation[i][j];
            }
        }

        return copy;
    }

    public String[][] getRepresentation() {
        return representation;
    }
}

