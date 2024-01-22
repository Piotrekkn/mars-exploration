package com.codecool.marsexploration.calculators.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class DimensionCalculatorImplTest {

    DimensionCalculator myCalculator = new DimensionCalculatorImpl();

    @Test
    void isDimensionCorrect(){
        //Arrange
        int test1 = 3; // 2x2
        int test2 = 8; // 3x3
        int test3 = 24; // 5x5
        int test4 = 25; // 5x5
        int test5 = 26; // 6x6
        //Act

        int score1 = myCalculator.calculateDimension(test1, 0); // 2
        int score2 = myCalculator.calculateDimension(test2, 1); // 4
        int score3 = myCalculator.calculateDimension(test3, 2); // 7

        int score4 = myCalculator.calculateDimension(test4, 0); // 5
        int score5 = myCalculator.calculateDimension(test5, 1); // 7
        //Assert
        assertEquals(2, score1);
        assertEquals(4, score2);
        assertEquals(7, score3);
        assertEquals(5, score4);
        assertEquals(7, score5);
    }
}
