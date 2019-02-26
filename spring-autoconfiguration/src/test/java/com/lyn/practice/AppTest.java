package com.lyn.practice;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {


        double a = 1;
        double b = 1.8;
        double c = 1.5;

        float a1 = 1.0f;
        float b1 = 1.8f;
        float c1 = 1.5f;

        int a3 = 1;

        System.out.println(a == a1);
        System.out.println(b == b1);
        System.out.println(c == c1);

        System.out.println(a == a3);

        double d = 1-0.8;
        double e = 1-0.5;
        System.out.println(d);
        System.out.println(e);
    }
}
