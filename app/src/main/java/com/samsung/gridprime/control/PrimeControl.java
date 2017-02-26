package com.samsung.gridprime.control;

import com.samsung.gridprime.util.Utils;

import java.util.Random;

/**
 * Created by michelcalacina on 25/02/17.
 */

public class PrimeControl {

    private Random random = null;
    private int[] primes;
    private CacheControl cache;

    public PrimeControl() {
        cache = new CacheControl();
        random = new Random();
    }

    public int[] generateRandomPrimes() {
        primes = new int[Utils.MAX_PRIMES_TO_LOAD];

        // Populate array with primes.
        for(int i=0; i < primes.length; i++) {
            boolean foundPrime = false;
            // Loop until find a prime number.
            while (!foundPrime) {
                // Include the upper inter max value and start from 2.
                int value = random.nextInt(Integer.MAX_VALUE) + 1;

                // For optimize, any even != 2 must be ignored immediately.
                if (value != 2 && value % 2 == 0)
                    continue;

                if (cache.containPrime(value)) {
                    primes[i] = value;
                    foundPrime = true;
                    continue;
                }

                if (cache.containNotPrime(value)) {
                    continue;
                }

                if (isPrime(value)) {
                    primes[i] = value;
                    cache.setPrimeContent(value);
                    foundPrime = true;
                } else {
                    cache.setNotPrimeContent(value);
                }
            }
        }

        return primes;
    }

    private boolean isPrime(int value) {
        boolean result = true;

        if (value == 2)
            return result;

        int stopPoint = (int)Math.sqrt(value);
        for (int i=2; i <= stopPoint; i++) {
            if (value % i == 0) {
                result = false;
                break;
            }
        }

        return result;
    }

}
