package com.lyn.practice;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    private AtomicInteger nextServerCyclicCounter = new AtomicInteger(0);
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testRoundRobinRule() {
        for(int i=0;i<10;i++){
            System.out.println(i+ " next server index is "+this.incrementAndGetModulo(5));
        }

    }

    private int incrementAndGetModulo(int modulo) {
        for (;;) {
            int current = nextServerCyclicCounter.get();
            int next = (current + 1) % modulo;
            if (nextServerCyclicCounter.compareAndSet(current, next))
                return next;
        }
    }
}
