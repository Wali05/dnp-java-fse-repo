package org.code;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class test {
    Calculator calculator = new Calculator();

    @Test
    public void testAddition() {
        assertEquals(5, calculator.add(2, 3));
        assertEquals(0, calculator.add(-2, 2));
    }

    @Test
    public void testSubtraction() {
        assertEquals(1, calculator.subtract(5, 4));
        assertEquals(-4, calculator.subtract(2, 6));
    }

    @Test
    public void testMultiplication() {
        assertEquals(12, calculator.multiply(3, 4));
        assertEquals(0, calculator.multiply(0, 100));
    }

    @Test
    public void testDivision() {
        assertEquals(2, calculator.divide(10, 5));
    }

    @Test
    public void testDivisionByZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(10, 0);
        });
        assertEquals("Division by zero", exception.getMessage());
    }
}
