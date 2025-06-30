package org.code;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    private Calculator calculator;

    @BeforeEach
    void setUp(){
        System.out.println("Setting up before each test");
        calculator = new Calculator();
    }

    @AfterEach
    void tearDown(){
        System.out.println("Cleaning up after each test case");
    }

    @Test
    void testAddition(){
        int a = 5;
        int b = 9;
        int result = calculator.add(a,b);
        assertEquals(14, result, "Addition should return the sum of two numbers");
    }

    @Test
    void testSubtraction(){
        int a = 15;
        int b = 4;
        int result = calculator.subtract(a,b);
        assertEquals(11,result,"Subtraction should return the difference between two numbers");
    }

    @Test
    void testMultiplication(){
        int a = 25;
        int b = 4;
        int result = calculator.multiply(a,b);
        assertEquals(100,result,"Multiplication should return the product of two numbers");
    }

    @Test
    void testDivsion(){
        int a = 60;
        int b = 12;
        int result = calculator.divide(a,b);
        assertEquals(5,result,"Division should return the quotient of two numbers");
    }
}
