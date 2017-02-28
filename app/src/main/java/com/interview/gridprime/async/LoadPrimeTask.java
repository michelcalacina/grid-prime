package com.interview.gridprime.async;

import com.interview.gridprime.control.PrimeControl;

/**
 * Implements the Runnable interface
 * that runs on background during prime numbers generation.
 *
 * Created by michelcalacina on 26/02/17.
 */

public class LoadPrimeTask implements Runnable {

    private final LoadPrimeCallBack callBack;
    private final PrimeControl primeControl;

    public LoadPrimeTask(LoadPrimeCallBack callBack) {
        this.callBack = callBack;
        primeControl = PrimeControl.getInstance();
    }

    @Override
    public void run() {
        // Moves the current Thread into the background
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        callBack.onPostExecute(primeControl.generateRandomPrimes());
    }
}
