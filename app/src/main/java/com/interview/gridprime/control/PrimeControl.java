package com.interview.gridprime.control;

import com.interview.gridprime.util.Utils;

/**
 * Implements the logic business that handles
 * prime numbers generation.
 *
 * Created by michelcalacina on 25/02/17.
 */

public class PrimeControl {

    private static PrimeControl primeControl = null;

    private PrimeControl() { }

    public static PrimeControl getInstance() {
        if (primeControl == null) {
            primeControl = new PrimeControl();
            CacheControl.build();
        }
        return primeControl;
    }

    public synchronized int[] generateRandomPrimes() {
        int[] primes = new int[Utils.MAX_PRIMES_TO_LOAD];

        // Populate array with primes.
        for(int i=0; i < primes.length; i++) {
            boolean foundPrime = false;
            // Loop until find a prime number.
            while (!foundPrime) {
                int value = generateRandomValue();

                // For optimize, any even != 2 must be ignored immediately.
                if (value > 2 && value % 2 == 0)
                    continue;

                if (CacheControl.containPrime(value)) {
                    primes[i] = value;
                    foundPrime = true;
                    continue;
                }

                if (CacheControl.containNotPrime(value)) {
                    continue;
                }

                if (isPrime(value)) {
                    primes[i] = value;
                    CacheControl.setPrimeNumber(value);
                    foundPrime = true;
                } else {
                    CacheControl.setNotPrimeContent(value);
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

    /**
     * This algorithm optimizes the random aproach
     * because the Math.random() method is not efficiente enought
     * when working with big number, if digit deterministic have
     * nine or ten digits, it bring values that has this digits approximately
     *
     * @return random value;
     */
    private int generateRandomValue() {
        int keyRandom = (int) (Math.random() * 7);
        int maxDecimalSize = 0;
        switch (keyRandom) {
            case 0:
                maxDecimalSize = 10000;
                break;
            case 1:
                maxDecimalSize = 100000;
                break;
            case 2:
                maxDecimalSize = 1000000;
                break;
            case 3:
                maxDecimalSize = 10000000;
                break;
            case 4:
                maxDecimalSize = 100000000;
                break;
            case 5:
                maxDecimalSize = 1000000000;
                break;
            case 6:
                maxDecimalSize = Integer.MAX_VALUE;
                break;

        }

        // Include the upper max decimal size generated value, and start from 2.
        return (int) (Math.random() * ((maxDecimalSize - 2) + 1)) + 2;
    }
}
