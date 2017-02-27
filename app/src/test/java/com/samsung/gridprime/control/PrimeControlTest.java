package com.samsung.gridprime.control;

import com.interview.gridprime.control.PrimeControl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by michelcalacina on 27/02/17.
 */

public class PrimeControlTest {
    private PrimeControl primeControl;
    private int[] primesDemo;

    @Before
    public void setUp() {
        primeControl = PrimeControl.getInstance();
        primesDemo = primeControl.generateRandomPrimes();
    }

    @Test
    public void validateThatGenerateSomePrimes() {
        int[] primes = primeControl.generateRandomPrimes();
        Assert.assertTrue(primes.length > 0);
    }

    @Test
    public void validateThatGenerateRandomPrimes() {
        int[] secondPrimesDemo = primeControl.generateRandomPrimes();

        // The Arrays must be different each other.
        Assert.assertTrue( !Arrays.equals(primesDemo, secondPrimesDemo) );
    }

    @Test
    public void validateRangeBetween2AndMaxInteger() {
        boolean allInRange = true;
        for (int p : primesDemo) {
            if ( !(p > 1) ) {
                allInRange = false;
                break;
            }
        }
        Assert.assertTrue(allInRange);
    }

    /**
     *  This test may take a long time to execute, depending of value in
     *  Utils.MAX_PRIMES_TO_LOAD, because of that this test is performed by sampling
     *  the first 10 elements in demos are taken to realize the test.
      */

    @Test
    public void validateIfValuesArePrimeNumbers_BruteForce() {
        int[] primesSample;
        if (primesDemo.length <= 10) {
            primesSample = primesDemo;
        } else {
            primesSample = Arrays.copyOfRange(primesDemo, 0, 9);
        }

        boolean areAllPrimes = true;
        for (int p : primesSample) {
            if (!isPrime_BruteForce(p)) {
                areAllPrimes = false;
                break;
            }
        }
        Assert.assertTrue(areAllPrimes);
    }

    // This method does not optimize the validation, it compares all possible divisible values.
    private boolean isPrime_BruteForce(int value) {
        boolean isPrime = true;
        if (value == 0 || value == 1)
            return !isPrime;
        if (value == 2)
            return isPrime;

        for (int i=2; i < value; i++) {
            if (value % i == 0) {
                isPrime = false;
                break;
            }
        }

        return isPrime;
    }
}
