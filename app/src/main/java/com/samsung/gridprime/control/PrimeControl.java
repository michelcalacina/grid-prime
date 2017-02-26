package com.samsung.gridprime.control;

import java.util.Random;

/**
 * Created by michelcalacina on 25/02/17.
 */

public class PrimeControl {

    private Random random = null;
    private final int TOTAL_PRIMES_TO_COLLECT;
    private int[] primes;
    private CacheControl cache;

    public PrimeControl(int totalToReturn) {
        if (totalToReturn <= 0) {
            TOTAL_PRIMES_TO_COLLECT = 100;
        } else {
            TOTAL_PRIMES_TO_COLLECT = totalToReturn;
        }
        cache = new CacheControl();

        // Following the rule that max prime allowed must be closer than max Integer value.
        random = new Random(Integer.MAX_VALUE+1);
    }

    public int[] generateRandomPrimes() {
        primes = new int[TOTAL_PRIMES_TO_COLLECT];

        // Populate every index of array with primes.
        for(int i=0; i < primes.length; i++) {
            boolean foundPrime = false;
            while (!foundPrime) {
                int value = random.nextInt();

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

    public boolean isPrime(int value) {
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
