package com.interview.gridprime.control;

import java.util.HashSet;

/**
 * Created by michelcalacina on 26/02/17.
 * This class help to speed up search for prime numbers,
 * this cache both prime and non prime numbers.
 */

  public final class CacheControl {

    private static final int MAX_CACHE_SIZE = 10000;
    private static HashSet<Integer> notPrimeContents = null;
    private static HashSet<Integer> primeNumberFounds = null;

    public static void build() {
        notPrimeContents = new HashSet<>();
        primeNumberFounds = new HashSet<>();
    }

    public static boolean containNotPrime(int value) {
        return notPrimeContents.contains(value);
    }

    public static boolean containPrime(int value) {
        return primeNumberFounds.contains(value);
    }

    public static void setPrimeNumber(int value) {
        if (primeNumberFounds.size() >= MAX_CACHE_SIZE)
            primeNumberFounds.clear();
        notPrimeContents.add(value);
    }

    public static void setNotPrimeContent(int value) {
        if (notPrimeContents.size() >= MAX_CACHE_SIZE)
            notPrimeContents.clear();
        notPrimeContents.add(value);
    }

    public static void clearCache() {
        primeNumberFounds.clear();
        notPrimeContents.clear();
    }
}
