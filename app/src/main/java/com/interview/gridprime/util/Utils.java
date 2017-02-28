package com.interview.gridprime.util;

import android.app.Activity;
import android.support.design.widget.Snackbar;

/**
 * Utility class to provide basic shared values and methods.
 *
 * Created by michelcalacina on 26/02/17.
 */

public final class Utils {
    // Each time that background task run, it will bring this amount of prime values.
    public static final int MAX_PRIMES_TO_LOAD = 200;

    // Max size of values allowed. 32767*4(number of bytes by integer) equals 131068 bytes.
    public static final int MAX_ALLOWED_SIZE = 32767;

    // After this value is reached after scroll down, init load data.
    public static final int BOUNDARY_TO_LOAD_MORE_ELEMENTS = MAX_PRIMES_TO_LOAD / 2;

    public static void showSnackBarMessage(String msg, int length, Activity activity) {
        Snackbar.make(activity.findViewById(android.R.id.content)
                , msg
                , length)
                .show();
    }
}
