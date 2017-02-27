package com.samsung.gridprime.util;

/**
 * Created by michelcalacina on 26/02/17.
 */

public final class Utils {
    // Each time that background task run, it will bring this amount of prime values.
    public static final int MAX_PRIMES_TO_LOAD = 60;

    // Max size of values allowed. 32767*4(number of bytes by integer) equals 131068 bytes.
    public static final int MAX_ALLOWED_VALUE = 32767;

    // After this value is reached during scroll down, init load data.
    public static final int BOUNDARY_TO_LOAD_MORE_ELEMENTS = MAX_PRIMES_TO_LOAD - 8;
}
