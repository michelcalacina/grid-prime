package com.samsung.gridprime.async;

import com.samsung.gridprime.control.PrimeControl;
import com.samsung.gridprime.util.Utils;

/**
 * Created by michelcalacina on 26/02/17.
 */

public class LoadPrimeTask implements Runnable {

    private LoadPrimeCallBack callBack;
    private PrimeControl primeControl;

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
