package com.samsung.gridprime.control;

import java.util.HashSet;

/**
 * Created by michelcalacina on 26/02/17.
 * This class help to speed up search for prime numbers,
 * this do not cache prime numbers, it caches not prime even numbers only.
 * After many tests was seem that prime numbers in range of Integer.MAX_VALUE,
 * rarely repeat it self, but not prime occour few times.
 */

public final class CacheControl {

    private static final int MAX_CHACHE_SIZE = 10000;
    private static HashSet notPrimeContents = null;

    public static void build() {
        notPrimeContents = new HashSet();
    }

    public static boolean containNotPrime(int value) {
        return notPrimeContents.contains(value);
    }

    public static void setNotPrimeContent(int value) {
        if (notPrimeContents.size() >= MAX_CHACHE_SIZE)
            notPrimeContents.clear();
        notPrimeContents.add(value);
    }

    public static void clearCache() {
        notPrimeContents.clear();
    }
}
