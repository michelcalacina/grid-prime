package com.interview.gridprime.async;

/**
 * This Interface is used to provide a callback
 * to the activity implements it.
 * Generally useful after finish data load.
 *
 * Created by michelcalacina on 26/02/17.
 */

public interface LoadPrimeCallBack {
    /**
     * Returns the array of integers, generated in background task.
     * @param values returned.
     */
    void onPostExecute(int[] values);
}
