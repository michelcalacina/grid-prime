package com.interview.gridprime.async;

/**
 * Created by michelcalacina on 26/02/17.
 */

public interface LoadPrimeCallBack {
    /**
     * Returns the array of integers, generated in backgroud task.
     * @param values returned.
     */
    void onPostExecute(int[] values);
}
